package org.jtext.ui.layout;

import org.jtext.ui.attribute.Direction;
import org.jtext.ui.attribute.HorizontalAlign;
import org.jtext.ui.attribute.Margin;
import org.jtext.ui.attribute.VerticalAlign;
import org.jtext.ui.graphics.Rectangle;
import org.jtext.ui.graphics.Widget;
import org.jtext.ui.graphics.Widget.WidgetDescriptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Collections.sort;
import static org.jtext.ui.graphics.Occupation.isFilling;

public class LinearLayout extends Layout {

    private final Direction direction;
    private final HorizontalAlign horizontalAlign;
    private final VerticalAlign verticalAlign;

    private final List<Widget> visibleWidgets = new ArrayList<>();
    private final Map<Widget, WidgetDescriptor> processedWidgets = new HashMap<>();
    private final List<Widget> widgetsWithOptionalSpace = new ArrayList<>();
    private final List<Widget> fillingWidgets = new ArrayList<>();


    public LinearLayout(final Direction direction, final HorizontalAlign horizontalAlign,
                        final VerticalAlign verticalAlign) {
        this.direction = direction;
        this.horizontalAlign = horizontalAlign;
        this.verticalAlign = verticalAlign;
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

    private PreProcessingResults preProcessWidgets() {
        final PreProcessingResults results = new PreProcessingResults();
        for (final Widget widget : widgetsInOrder) {
            if (widget.isVisible()) {
                final WidgetDescriptor descriptor = widget.getDescriptor(dimension.width, direction);

                if (isFilling(widget.getPreferredWidth())) {
                    results.numberOfFillingWidgets++;
                    fillingWidgets.add(widget);
                }

                results.requiredSpace += descriptor.preferred + widget.getMargin().horizontalSpacing();
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
        return dimension.width > preProcessingResults.requiredSpace && preProcessingResults.numberOfFillingWidgets > 0;
    }

    private void fillRemainingSpace(final PreProcessingResults preProcessingResults) {
        int remaining = dimension.width - preProcessingResults.requiredSpace;
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
        preProcessingResults.requiredSpace = dimension.width;
    }

    private boolean shouldShrinkWidgets(final PreProcessingResults preProcessingResults) {
        return dimension.width < preProcessingResults.requiredSpace && optionalSpaceRemains(preProcessingResults);
    }

    private boolean optionalSpaceRemains(final PreProcessingResults preProcessingResults) {
        return preProcessingResults.requiredSpace <= dimension.width + preProcessingResults.optionalSpace;
    }

    private void shrinkWidgets(final PreProcessingResults preProcessingResults) {
        sort(widgetsWithOptionalSpace, (a, b) -> processedWidgets.get(a).compareTo(processedWidgets.get(b)));
        for (final Widget w : widgetsWithOptionalSpace) {
            final int target = preProcessingResults.requiredSpace - dimension.width;
            if (target != 0) {
                final WidgetDescriptor d = processedWidgets.get(w);
                int reduction = min(max((target * d.optional + (preProcessingResults.optionalSpace >> 1)) /
                                preProcessingResults.optionalSpace, 1),
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
        int shift = calculateInitialShift(results);
        int x = 0;
        for (final Widget w : visibleWidgets) {
            final WidgetDescriptor d = processedWidgets.get(w);
            final Margin margin = w.getMargin();
            final int realHeight = w.getRealHeight(dimension.height);
            final int realWidth = d.toUse == -1 ? sizeFun.apply(d) : d.toUse;
            widgets.put(w, Rectangle.of(x + max(margin.left, shift), getStartY(realHeight, margin),
                    realWidth, realHeight));
            x += max(margin.left, shift) + realWidth + margin.right;
            shift = 0;
        }
    }

    private int calculateInitialShift(final PreProcessingResults results) {
        int shift = 0;
        if (results.requiredSpace < dimension.width) {
            if (horizontalAlign == HorizontalAlign.RIGHT) {
                shift = dimension.width - results.requiredSpace;
            }
            if (horizontalAlign == HorizontalAlign.CENTER) {
                final Widget firstWidget = visibleWidgets.get(0);
                final Widget lastWidget = visibleWidgets.get(visibleWidgets.size() - 1);
                int left = firstWidget.getMargin().left;
                int right = lastWidget.getMargin().right;
                int withoutMargin = dimension.width - results.requiredSpace + left + right;
                shift = withoutMargin / 2;
                int reminder = withoutMargin % 2;

                if (left > shift) {
                    shift = left;
                } else if (right > shift + reminder) {
                    shift -= right - shift - reminder;
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

    private int getStartY(final int height, final Margin margin) {
        int ret = margin.top;
        if (verticalAlign != VerticalAlign.TOP) {
            int remaining = dimension.height - height;
            ret = verticalAlign == VerticalAlign.CENTER ? remaining / 2 : remaining;

            if (ret < margin.top && verticalAlign == VerticalAlign.CENTER) {
                ret = margin.top;
            }
            if (verticalAlign == VerticalAlign.BOTTOM) {
                ret -= margin.bottom;
            }
        }
        return ret;
    }

    private static class PreProcessingResults {
        int requiredSpace;
        int optionalSpace;
        int numberOfFillingWidgets;
    }

}
