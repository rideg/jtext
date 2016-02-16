package org.jtext.ui.layout;

import org.jtext.ui.attribute.Direction;
import org.jtext.ui.attribute.HorizontalAlign;
import org.jtext.ui.attribute.Margin;
import org.jtext.ui.attribute.VerticalAlign;
import org.jtext.ui.graphics.Rectangle;
import org.jtext.ui.graphics.Widget;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

import static java.math.RoundingMode.HALF_UP;
import static org.jtext.ui.graphics.Occupation.isFilling;

public class LinearLayout extends Layout {

    private final Direction direction;
    private final HorizontalAlign horizontalAlign;
    private final VerticalAlign verticalAlign;

    public LinearLayout(final Direction direction,
                        final HorizontalAlign horizontalAlign,
                        final VerticalAlign verticalAlign) {
        this.direction = direction;
        this.horizontalAlign = horizontalAlign;
        this.verticalAlign = verticalAlign;
    }

    @Override
    protected void updateWidgetAreas() {

        final List<Widget> visibleWidgets = new ArrayList<>();
        final Map<Widget, Descriptor> processedWidgets = new HashMap<>();
        final List<Widget> widgetsWithBuffer = new ArrayList<>();
        final List<Widget> fillingWidgets = new ArrayList<>();

        int requiredSpace = 0;
        int buffer = 0;
        int nrOfFills = 0;
        for (final Widget w : widgetOrder) {
            if (w.isVisible()) {
                final Descriptor d = Descriptor.from(w, dimension.width);
                if (isFilling(w.getPreferredWidth())) {
                    nrOfFills++;
                    fillingWidgets.add(w);
                }
                requiredSpace += d.pref + w.getMargin().horizontalSpacing();
                buffer += d.opt;
                if (d.opt > 0) {
                    widgetsWithBuffer.add(w);
                }
                processedWidgets.put(w, d);
                visibleWidgets.add(w);
            } else {
                widgets.put(w, Rectangle.empty());
            }
        }

        if (processedWidgets.isEmpty()) {
            return;
        }

        if (nrOfFills > 0) {
            if (dimension.width > requiredSpace) {
                int remaining = dimension.width - requiredSpace;
                while (true) {
                    int additional = remaining / nrOfFills;
                    int reminder = remaining % nrOfFills;
                    final List<Widget> smallerWidgets = new ArrayList<>();
                    for (final Widget w : fillingWidgets) {
                        final Descriptor d = processedWidgets.get(w);
                        int toUse = d.pref + additional + (reminder-- > 0 ? 1 : 0);
                        if (toUse > d.max) {
                            d.toUse = d.max;
                            remaining -= d.max - d.pref;
                            smallerWidgets.add(w);
                        }
                    }
                    fillingWidgets.removeAll(smallerWidgets);
                    nrOfFills -= smallerWidgets.size();
                    if (smallerWidgets.isEmpty() || nrOfFills == 0) {
                        for (final Widget w : fillingWidgets) {
                            final Descriptor d = processedWidgets.get(w);
                            d.toUse = d.pref + additional + (reminder-- > 0 ? 1 : 0);
                        }
                        break;
                    }
                }
                requiredSpace = dimension.width;
            }
        }

        final Function<Descriptor, Integer> sizeFunction =
                requiredSpace - buffer > dimension.width ? d -> d.min : d -> d.pref;

        if (dimension.width < requiredSpace && requiredSpace <= dimension.width + buffer) {
            Collections.sort(widgetsWithBuffer, (a, b) -> processedWidgets.get(a).compareTo(processedWidgets.get(b)));


            for (final Widget w : widgetsWithBuffer) {
                final BigDecimal target = new BigDecimal(requiredSpace - dimension.width);
                if (target.equals(BigDecimal.ZERO)) {
                    break;
                }
                final Descriptor d = processedWidgets.get(w);
                int reduction = Math.max(
                        target.multiply(new BigDecimal(d.opt).divide(new BigDecimal(buffer), 2, HALF_UP))
                                .setScale(0, HALF_UP)
                                .intValue(), 1);

                reduction = Math.min(reduction, target.intValue());
                reduction = Math.min(reduction, d.opt);

                d.toUse = d.pref - reduction;
                d.opt = d.toUse - d.min;
                requiredSpace -= reduction;
                buffer -= reduction;
            }
        }

        int x = 0;
        int shift = 0;
        if (requiredSpace < dimension.width) {
            if (horizontalAlign == HorizontalAlign.RIGHT) {
                shift = dimension.width - requiredSpace;
            }
            if (horizontalAlign == HorizontalAlign.CENTER) {
                int left = visibleWidgets.get(0).getMargin().left;
                int right = visibleWidgets.get(visibleWidgets.size() - 1).getMargin().right;
                int withoutMargin = dimension.width - requiredSpace + left + right;

                shift = withoutMargin / 2;
                int reminder = withoutMargin % 2;

                if (left > shift) {
                    shift = left;
                } else if (right > shift + reminder) {
                    shift -= right - shift - reminder;
                }
            }
        }

        for (final Widget w : visibleWidgets) {
            final Descriptor d = processedWidgets.get(w);
            final Margin margin = w.getMargin();
            final int realHeight = w.getRealHeight(dimension.height);
            final int realWidth = d.toUse == -1 ? sizeFunction.apply(d) : d.toUse;
            widgets.put(w, Rectangle.of(x + Math.max(margin.left, shift),
                                        getStartY(realHeight, margin),
                                        realWidth, realHeight));
            x += Math.max(margin.left, shift) + realWidth + margin.right;
            shift = 0;
        }
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

    private static class Descriptor implements Comparable<Descriptor> {

        public int toUse = -1;
        public int pref;
        public int min;
        public int opt;
        public int max;

        public Descriptor(final int pref, final int min, final int max) {
            this.pref = pref;
            this.min = min;
            this.opt = pref - min;
            this.max = max;
        }

        public static Descriptor from(final Widget widget, int available) {
            int pref = (isFilling(widget.getPreferredWidth()) ?
                        widget.getMinWidth() :
                        widget.getPreferredWidth()).toReal(available);
            return new Descriptor(pref, widget.getMinWidth().toReal(available), widget.getMaxWidth().toReal(available));
        }


        @Override
        public int compareTo(final Descriptor o) {
            return Integer.compare(o.opt, opt);
        }
    }
}
