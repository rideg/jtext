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

        int remaining = dimension.width;
        int startX = 0;
        int numberOfFillingWidgets = 0;

        for (final Widget widget : visibleWidgets) {
            if (!isFilling(widget.getPreferredWidth())) {
                final Margin margin = widget.getMargin();
                int realWidth = widget.getPreferredWidth().toReal(dimension.width);
                int realHeight = widget.getRealHeight(dimension.height);
                widgets.put(widget, Rectangle.of(startX + margin.left, margin.top, realWidth, realHeight));
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
                    widgets.put(widget, Rectangle.of(startX + margin.left, margin.top, width, realHeight));
                    startX += width + margin.horizontalSpacing();
                    shift += width + margin.horizontalSpacing();
                } else {
                    Rectangle shifted = widgets.get(widget).shiftX(shift);
                    widgets.put(widget, shifted);
                    startX += shifted.x + shifted.width + widget.getMargin().right;
                }
            }
        } else if (horizontalAlign != HorizontalAlign.LEFT && remaining > 0) {
            final int shift = horizontalAlign == HorizontalAlign.CENTER ? remaining / 2 : remaining;
            visibleWidgets
                    .stream()
                    .filter(Widget::isVisible)
                    .forEach(w -> widgets.put(w, widgets.get(w).shiftX(shift)));
        }
    }

}
