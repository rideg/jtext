package org.jtext.ui.layout;

import org.jtext.ui.attribute.Direction;
import org.jtext.ui.attribute.HorizontalAlign;
import org.jtext.ui.attribute.Margin;
import org.jtext.ui.attribute.VerticalAlign;
import org.jtext.ui.graphics.Rectangle;
import org.jtext.ui.graphics.Widget;
import org.jtext.ui.graphics.Widget.WidgetDescriptor;

import java.util.*;
import java.util.function.Function;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.jtext.ui.graphics.Occupation.isFilling;

public class LinearLayout extends Layout {

    private final Direction direction;
    private final HorizontalAlign horizontalAlign;
    private final VerticalAlign verticalAlign;

    private final List<Widget> visibleWidgets = new ArrayList<>();
    private final Map<Widget, WidgetDescriptor> processedWidgets = new HashMap<>();
    private final List<Widget> widgetsWithBuffer = new ArrayList<>();
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


    private void placeWidgets(final PreProcessingResults preProcessingResults) {
        final Function<WidgetDescriptor, Integer> sizeFunction =
                preProcessingResults.requiredSpace - preProcessingResults.buffer > dimension.width ? d -> d.min
                                                                                                   : d -> d.pref;
        int shift = calculateInitialShift(preProcessingResults);
        int x = 0;
        for (final Widget w : visibleWidgets) {
            final WidgetDescriptor d = processedWidgets.get(w);
            final Margin margin = w.getMargin();
            final int realHeight = w.getRealHeight(dimension.height);
            final int realWidth = d.toUse == -1 ? sizeFunction.apply(d) : d.toUse;
            widgets.put(w, Rectangle.of(x + max(margin.left, shift), getStartY(realHeight, margin),
                                        realWidth, realHeight));
            x += max(margin.left, shift) + realWidth + margin.right;
            shift = 0;
        }
    }

    private PreProcessingResults preProcessWidgets() {
        final PreProcessingResults results = new PreProcessingResults();
        for (final Widget widget : widgetOrder) {
            if (widget.isVisible()) {
                final WidgetDescriptor descriptor = widget.getDescriptor(dimension.width, direction);

                if (isFilling(widget.getPreferredWidth())) {
                    results.nrOfFills++;
                    fillingWidgets.add(widget);
                }

                results.requiredSpace += descriptor.pref + widget.getMargin().horizontalSpacing();
                results.buffer += descriptor.opt;
                if (descriptor.opt > 0) {
                    widgetsWithBuffer.add(widget);
                }
                processedWidgets.put(widget, descriptor);
                visibleWidgets.add(widget);
            } else {
                widgets.put(widget, Rectangle.empty());
            }
        }
        return results;
    }

    private void fillRemainingSpace(final PreProcessingResults preProcessingResults) {
        int remaining = dimension.width - preProcessingResults.requiredSpace;
        while (true) {
            int additional = remaining / preProcessingResults.nrOfFills;
            int reminder = remaining % preProcessingResults.nrOfFills;
            final List<Widget> smallerWidgets = new ArrayList<>();
            for (final Widget w : fillingWidgets) {
                final WidgetDescriptor d = processedWidgets.get(w);
                int toUse = d.pref + additional + (reminder-- > 0 ? 1 : 0);
                if (toUse > d.max) {
                    d.toUse = d.max;
                    remaining -= d.max - d.pref;
                    smallerWidgets.add(w);
                }
            }
            fillingWidgets.removeAll(smallerWidgets);
            preProcessingResults.nrOfFills -= smallerWidgets.size();
            if (smallerWidgets.isEmpty() || preProcessingResults.nrOfFills == 0) {
                for (final Widget w : fillingWidgets) {
                    final WidgetDescriptor d = processedWidgets.get(w);
                    d.toUse = d.pref + additional + (reminder-- > 0 ? 1 : 0);
                }
                break;
            }
        }
        preProcessingResults.requiredSpace = dimension.width;
    }

    private boolean shouldFillRemainingSpace(final PreProcessingResults preProcessingResults) {
        return dimension.width > preProcessingResults.requiredSpace && preProcessingResults.nrOfFills > 0;
    }

    private void shrinkWidgets(final PreProcessingResults preProcessingResults) {
        Collections.sort(widgetsWithBuffer, (a, b) -> processedWidgets.get(a).compareTo(processedWidgets.get(b)));
        for (final Widget w : widgetsWithBuffer) {
            final int target = preProcessingResults.requiredSpace - dimension.width;
            if (target != 0) {
                final WidgetDescriptor d = processedWidgets.get(w);
                int reduction =
                        min(max((target * d.opt + (preProcessingResults.buffer >> 1)) / preProcessingResults.buffer, 1),
                            min(target, d.opt));
                d.toUse = d.pref - reduction;
                d.opt = d.toUse - d.min;
                preProcessingResults.requiredSpace -= reduction;
                preProcessingResults.buffer -= reduction;
            } else {
                break;
            }
        }
    }

    private boolean shouldShrinkWidgets(final PreProcessingResults preProcessingResults) {
        return dimension.width < preProcessingResults.requiredSpace &&
               preProcessingResults.requiredSpace <= dimension.width + preProcessingResults.buffer;
    }

    private int calculateInitialShift(final PreProcessingResults results) {
        int shift = 0;
        if (results.requiredSpace < dimension.width) {
            if (horizontalAlign == HorizontalAlign.RIGHT) {
                shift = dimension.width - results.requiredSpace;
            }
            if (horizontalAlign == HorizontalAlign.CENTER) {
                int left = visibleWidgets.get(0).getMargin().left;
                int right = visibleWidgets.get(visibleWidgets.size() - 1).getMargin().right;
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
        widgetsWithBuffer.clear();
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
        int buffer;
        int nrOfFills;
    }

}
