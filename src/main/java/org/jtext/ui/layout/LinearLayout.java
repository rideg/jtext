package org.jtext.ui.layout;

import org.jtext.ui.attribute.Direction;
import org.jtext.ui.attribute.HorizontalAlign;
import org.jtext.ui.attribute.Margin;
import org.jtext.ui.attribute.VerticalAlign;
import org.jtext.ui.graphics.Rectangle;
import org.jtext.ui.graphics.Widget;

import java.util.List;
import java.util.stream.Collectors;

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
        final List<Widget> visibleWidgets = widgetOrder.stream().filter(Widget::isVisible).collect(Collectors.toList());
        widgetOrder.stream().filter(w -> !w.isVisible()).forEach(w -> widgets.put(w, Rectangle.empty()));

        if (visibleWidgets.isEmpty()) {
            return;
        }

        int remaining = dimension.width;
        int startX = 0;
        int numberOfFillingWidgets = 0;

        for (final Widget widget : visibleWidgets) {
            if (!isFilling(widget.getPreferredWidth())) {
                final Margin margin = widget.getMargin();
                int realWidth = widget.getPreferredWidth().toReal(dimension.width);
                int realHeight = widget.getRealHeight(dimension.height);

                int startY = getStartY(realHeight, margin);

                widgets.put(widget, Rectangle.of(startX + margin.left, startY, realWidth, realHeight));
                remaining -= realWidth + margin.horizontalSpacing();
                startX += realWidth + margin.horizontalSpacing();
            } else {
                numberOfFillingWidgets++;
            }
        }

        if (numberOfFillingWidgets > 0) {
            int shift = 0;
            startX = 0;
            int fillingWidth = remaining / numberOfFillingWidgets;
            int remainder = remaining % numberOfFillingWidgets;
            for (Widget widget : visibleWidgets) {
                if (isFilling(widget.getPreferredWidth())) {
                    final int widthCandidate = fillingWidth + (remainder > 0 ? 1 : 0);
                    final int width = Math.min(widthCandidate, widget.getMaxWidth().toReal(dimension.width));
                    if (width == widthCandidate) {
                        remainder--;
                    }
                    int realHeight = widget.getRealHeight(dimension.height);
                    final Margin margin = widget.getMargin();
                    int startY = getStartY(realHeight, margin);
                    widgets.put(widget, Rectangle.of(startX + margin.left, startY, width, realHeight));
                    startX += width + margin.horizontalSpacing();
                    shift += width + margin.horizontalSpacing();
                } else {
                    Rectangle shifted = widgets.get(widget).shiftX(shift);
                    widgets.put(widget, shifted);
                    startX += shifted.x + shifted.width + widget.getMargin().right;
                }
            }
        } else if (horizontalAlign != HorizontalAlign.LEFT && remaining > 0) {
            final Widget firstWidget = visibleWidgets.get(0);
            final Widget lastWidget = visibleWidgets.get(visibleWidgets.size() - 1);

            remaining += firstWidget.getMargin().left;
            remaining += lastWidget.getMargin().right;

            int shift = horizontalAlign == HorizontalAlign.CENTER ? remaining / 2 : remaining;

            if (shift < firstWidget.getMargin().left && horizontalAlign == HorizontalAlign.CENTER) {
                shift = firstWidget.getMargin().left;
            }

            if (horizontalAlign == HorizontalAlign.RIGHT) {
                shift -= lastWidget.getMargin().right;
            }

            for (final Widget widget : visibleWidgets) {
                final int tmpShift = widget == firstWidget ? shift - widget.getMargin().left : shift;
                widgets.put(widget, widgets.get(widget).shiftX(tmpShift));
            }
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
}
