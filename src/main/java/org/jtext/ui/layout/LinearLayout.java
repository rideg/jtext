package org.jtext.ui.layout;

import org.jtext.ui.attribute.Direction;
import org.jtext.ui.attribute.HorizontalAlign;
import org.jtext.ui.attribute.Margin;
import org.jtext.ui.attribute.VerticalAlign;
import org.jtext.ui.graphics.Rectangle;
import org.jtext.ui.graphics.Widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Collections.sort;
import static org.jtext.ui.graphics.Occupation.isFilling;
import static org.jtext.ui.util.Util.divHalfUp;

public class LinearLayout extends Layout<Widget> {

    private final Direction direction;
    private final Alignment primaryAlignment;
    private final Alignment secondaryAlignment;

    private final List<Widget> visibleWidgets = new ArrayList<>();
    private final Map<Widget, WidgetDescriptor> processedWidgets = new HashMap<>();
    private final List<Widget> widgetsWithOptionalSpace = new ArrayList<>();
    private final List<Widget> fillingWidgets = new ArrayList<>();

    public LinearLayout(final Direction direction, final HorizontalAlign horizontalAlign,
                        final VerticalAlign verticalAlign) {
        this.direction = direction;
        if (isHorizontal()) {
            this.primaryAlignment = Alignment.map(horizontalAlign);
            this.secondaryAlignment = Alignment.map(verticalAlign);
        } else {
            this.primaryAlignment = Alignment.map(verticalAlign);
            this.secondaryAlignment = Alignment.map(horizontalAlign);
        }
    }

    private boolean isHorizontal() {
        return direction == Direction.HORIZONTAL;
    }

    @Override
    protected void updateWidgetAreas() {
        clearAuxiliaries();
        final PreProcessingResults results = preProcessWidgets();
        if (!processedWidgets.isEmpty()) {
            if (shouldFillRemainingSpace(results)) {
                fillRemainingSpace(results);
            }
            if (shouldShrinkWidgets(results)) {
                shrinkWidgets(results);
            }
            placeWidgets(results);
        }
    }

    private int getAvailableSpace() {
        return isHorizontal() ? dimension.width : dimension.height;
    }

    private int getAvailableSecondarySpace() {
        return isHorizontal() ? dimension.height : dimension.width;
    }

    private PreProcessingResults preProcessWidgets() {
        final PreProcessingResults results = new PreProcessingResults();
        for (final Widget widget : widgetsInOrder) {
            if (widget.isVisible()) {
                final WidgetDescriptor descriptor = getDescriptor(widget);

                if (isFilling(isHorizontal() ? widget.getPreferredWidth() : widget.getPreferredHeight())) {
                    results.numberOfFillingWidgets++;
                    fillingWidgets.add(widget);
                }

                results.requiredSpace += descriptor.preferred + widget.getMargin().getSlice(direction).getSpacing();
                results.optionalSpace += descriptor.optional;
                if (descriptor.optional > 0) {
                    widgetsWithOptionalSpace.add(widget);
                }
                processedWidgets.put(widget, descriptor);
                visibleWidgets.add(widget);
            } else {
                widgets.put(widget, Rectangle.empty());
            }
        }
        return results;
    }

    private boolean shouldFillRemainingSpace(final PreProcessingResults preProcessingResults) {
        return getAvailableSpace() > preProcessingResults.requiredSpace &&
               preProcessingResults.numberOfFillingWidgets > 0;
    }

    private void fillRemainingSpace(final PreProcessingResults preProcessingResults) {
        int remaining = getAvailableSpace() - preProcessingResults.requiredSpace;
        while (preProcessingResults.numberOfFillingWidgets > 0) {
            int additional = remaining / preProcessingResults.numberOfFillingWidgets;
            int reminder = remaining % preProcessingResults.numberOfFillingWidgets;
            final List<Widget> smallerWidgets = new ArrayList<>();
            for (final Widget w : fillingWidgets) {
                final WidgetDescriptor d = processedWidgets.get(w);
                // maybe the reminder space could be
                // distributed more equally
                int toUse = d.preferred + additional + (reminder-- > 0 ? 1 : 0);
                if (toUse > d.maximum) {
                    d.toUse = d.maximum;
                    remaining -= d.maximum - d.preferred;
                    smallerWidgets.add(w);
                }
            }
            fillingWidgets.removeAll(smallerWidgets);
            preProcessingResults.numberOfFillingWidgets -= smallerWidgets.size();
            if (smallerWidgets.isEmpty()) {
                for (final Widget w : fillingWidgets) {
                    final WidgetDescriptor d = processedWidgets.get(w);
                    d.toUse = d.preferred + additional + (reminder-- > 0 ? 1 : 0);
                }
                preProcessingResults.numberOfFillingWidgets = 0;
            }
        }
        preProcessingResults.requiredSpace = getAvailableSpace();
    }

    private boolean shouldShrinkWidgets(final PreProcessingResults preProcessingResults) {
        return getAvailableSpace() < preProcessingResults.requiredSpace && optionalSpaceRemains(preProcessingResults);
    }

    private boolean optionalSpaceRemains(final PreProcessingResults preProcessingResults) {
        return preProcessingResults.requiredSpace <= getAvailableSpace() + preProcessingResults.optionalSpace;
    }

    private void shrinkWidgets(final PreProcessingResults preProcessingResults) {
        sort(widgetsWithOptionalSpace, (a, b) -> processedWidgets.get(a).compareTo(processedWidgets.get(b)));
        for (final Widget w : widgetsWithOptionalSpace) {
            final int target = preProcessingResults.requiredSpace - getAvailableSpace();
            if (target != 0) {
                final WidgetDescriptor d = processedWidgets.get(w);
                int reduction = min(max(divHalfUp(target * d.optional, preProcessingResults.optionalSpace), 1),
                                    min(target, d.optional));
                d.toUse = d.preferred - reduction;
                d.optional = d.toUse - d.minimum;
                preProcessingResults.requiredSpace -= reduction;
                preProcessingResults.optionalSpace -= reduction;
            } else {
                break;
            }
        }
    }

    private void placeWidgets(final PreProcessingResults results) {
        Function<WidgetDescriptor, Integer> sizeFun = optionalSpaceRemains(results) ? d -> d.preferred : d -> d.minimum;
        int shift = getPrimaryShift(results);
        int startPosition = 0;
        for (final Widget widget : visibleWidgets) {
            final WidgetDescriptor d = processedWidgets.get(widget);
            final Margin margin = widget.getMargin();
            final Margin.Slice slice = widget.getMargin().getSlice(direction);
            final int secondarySize = getRealSecondarySize(widget);
            final int primarySize = d.toUse == -1 ? sizeFun.apply(d) : d.toUse;
            final Rectangle area = Rectangle.of(startPosition + max(slice.begin, shift),
                                                getStartSecondary(secondarySize, margin.getSlice(direction.opposite())),
                                                primarySize, secondarySize);
            widgets.put(widget, isHorizontal() ? area : area.flip());
            startPosition += max(slice.begin, shift) + primarySize + slice.end;
            shift = 0;
        }
    }

    private int getPrimaryShift(final PreProcessingResults results) {
        int shift = 0;
        if (results.requiredSpace < getAvailableSpace()) {
            if (primaryAlignment == Alignment.END) {
                shift = getAvailableSpace() - results.requiredSpace;
            }
            if (primaryAlignment == Alignment.CENTER) {
                final Widget firstWidget = visibleWidgets.get(0);
                final Widget lastWidget = visibleWidgets.get(visibleWidgets.size() - 1);
                int begin = firstWidget.getMargin().getSlice(direction).begin;
                int end = lastWidget.getMargin().getSlice(direction).end;
                int withoutMargin = getAvailableSpace() - results.requiredSpace + begin + end;
                shift = withoutMargin / 2;
                int reminder = withoutMargin % 2;

                if (begin > shift) {
                    shift = begin;
                } else if (end > shift + reminder) {
                    shift -= end - shift - reminder;
                }
            }
        }
        return shift;
    }

    private void clearAuxiliaries() {
        visibleWidgets.clear();
        processedWidgets.clear();
        widgetsWithOptionalSpace.clear();
        fillingWidgets.clear();
    }

    private int getStartSecondary(final int size, final Margin.Slice slice) {
        int ret = slice.begin;
        if (secondaryAlignment != Alignment.BEGIN) {
            int remaining = getAvailableSecondarySpace() - size;
            ret = secondaryAlignment == Alignment.CENTER ? remaining / 2 : remaining;

            if (ret < slice.begin && secondaryAlignment == Alignment.CENTER) {
                ret = slice.begin;
            }
            if (secondaryAlignment == Alignment.END) {
                ret -= slice.end;
            }
        }
        return ret;
    }

    private WidgetDescriptor getDescriptor(final Widget widget) {
        final int availableSpace = getAvailableSpace();
        if (isHorizontal()) {
            int pref = (isFilling(widget.getPreferredWidth()) ? widget.getMinWidth() : widget.getPreferredWidth())
                               .toReal(availableSpace);
            return new WidgetDescriptor(pref, widget.getMinWidth().toReal(availableSpace),
                                        widget.getMaxWidth().toReal(availableSpace));
        } else {
            int pref = (isFilling(widget.getPreferredHeight()) ? widget.getMinHeight() : widget.getPreferredHeight())
                               .toReal(availableSpace);
            return new WidgetDescriptor(pref, widget.getMinHeight().toReal(availableSpace),
                                        widget.getMaxHeight().toReal(availableSpace));
        }
    }

    private int getRealSecondarySize(final Widget widget) {
        int available = getAvailableSecondarySpace();
        if (isHorizontal()) {
            final int height =
                    Math.min(widget.getPreferredHeight().toReal(available), widget.getMaxHeight().toReal(available));
            if (height > available) {
                return Math.max(available, widget.getMinHeight().toReal(available));
            }
            return height;
        } else {
            final int width =
                    Math.min(widget.getPreferredWidth().toReal(available), widget.getMaxWidth().toReal(available));
            if (width > available) {
                return Math.max(available, widget.getMinWidth().toReal(available));
            }
            return width;
        }
    }

    private enum Alignment {
        BEGIN,
        CENTER,
        END;

        public static Alignment map(final VerticalAlign align) {
            switch (align) {
                case TOP:
                    return BEGIN;
                case CENTER:
                    return CENTER;
                case BOTTOM:
                    return END;
                default:
                    throw new IllegalArgumentException("Cannot map alignment: " + align);
            }
        }

        public static Alignment map(final HorizontalAlign align) {
            switch (align) {
                case LEFT:
                    return BEGIN;
                case CENTER:
                    return CENTER;
                case RIGHT:
                    return END;
                default:
                    throw new IllegalArgumentException("Cannot map alignment: " + align);
            }
        }

    }

    @SuppressWarnings("checkstyle:visibilitymodifier")
    private static class PreProcessingResults {

        int requiredSpace;
        int optionalSpace;
        int numberOfFillingWidgets;
    }

    @SuppressWarnings("checkstyle:visibilitymodifier")
    public static class WidgetDescriptor implements Comparable<WidgetDescriptor> {

        public int toUse = -1;
        public int preferred;
        public int minimum;
        public int optional;
        public int maximum;

        public WidgetDescriptor(final int preferred, final int minimum, final int maximum) {
            this.preferred = preferred;
            this.minimum = minimum;
            this.optional = preferred - minimum;
            this.maximum = maximum;
        }

        @Override
        public int compareTo(final WidgetDescriptor o) {
            return Integer.compare(o.optional, optional);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            final WidgetDescriptor that = (WidgetDescriptor) o;

            return toUse == that.toUse && preferred == that.preferred && minimum == that.minimum &&
                   optional == that.optional && maximum == that.maximum;

        }

        @Override
        public int hashCode() {
            int result = toUse;
            result = 31 * result + preferred;
            result = 31 * result + minimum;
            result = 31 * result + optional;
            result = 31 * result + maximum;
            return result;
        }
    }

}
