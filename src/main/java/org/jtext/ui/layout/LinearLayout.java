package org.jtext.ui.layout;

import org.jtext.ui.attribute.Direction;
import org.jtext.ui.attribute.HorizontalAlign;
import org.jtext.ui.attribute.Margin;
import org.jtext.ui.attribute.VerticalAlign;
import org.jtext.ui.graphics.Occupation;
import org.jtext.ui.graphics.Rectangle;
import org.jtext.ui.graphics.Widget;

import java.math.BigDecimal;

import static org.jtext.ui.graphics.Occupation.*;

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

        int remaining = dimension.width;
        int startX = 0;
        int numberOfFillingWidgets = 0;

        for (final Widget widget : widgetOrder) {
            if (widget.isVisible()) {
                if (!isFilling(widget.getPreferredWidth())) {
                    final Margin margin = widget.getMargin();
                    int realWidth = getNrOfCells(widget.getPreferredWidth(), dimension.width);
                    int realHeight = Math.min(getNrOfCells(widget.getPreferredHeight(), dimension.height),
                                              getNrOfCells(widget.getMaxHeight(), dimension.height));

                    if (realHeight > dimension.height) {
                        realHeight = Math.max(dimension.height, getNrOfCells(widget.getMinHeight(), dimension.height));
                    }
                    widgets.put(widget, Rectangle.of(startX + margin.left, margin.top, realWidth, realHeight));
                    remaining -= realWidth + margin.horizontalSpacing();
                    startX += realWidth + margin.horizontalSpacing();
                } else {
                    numberOfFillingWidgets++;
                }
            } else {
                widgets.put(widget, Rectangle.empty());
            }
        }

        if (numberOfFillingWidgets > 0) {
            int shift = 0;
            startX = 0;
            int fillingWidth = remaining / numberOfFillingWidgets;
            int remainder = remaining % numberOfFillingWidgets;
            for (Widget widget : widgetOrder) {
                if (isFilling(widget.getPreferredWidth())) {
                    final int widthCandidate = fillingWidth + (remainder > 0 ? 1 : 0);
                    final int width = Math.min(widthCandidate, getNrOfCells(widget.getMaxWidth(), dimension.width));
                    if (width == widthCandidate) {
                        remainder--;
                    }
                    int realHeight = Math.min(getNrOfCells(widget.getPreferredHeight(), dimension.height),
                                              getNrOfCells(widget.getMaxHeight(), dimension.height));
                    if (realHeight > dimension.height) {
                        realHeight = Math.max(dimension.height, getNrOfCells(widget.getMinHeight(), dimension.height));
                    }
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
            widgetOrder
                    .stream()
                    .filter(Widget::isVisible)
                    .forEach(w -> widgets.put(w, widgets.get(w).shiftX(shift)));
        }
    }

    private int getNrOfCells(final Occupation preferred, final int max) {
        if (isFixed(preferred)) {
            return ((Occupation.Fixed) preferred).getSize();
        }
        if (isProportional(preferred)) {
            return new BigDecimal(max)
                           .multiply(new BigDecimal(((Occupation.Proportional) preferred).getPercentage()))
                           .divide(new BigDecimal(100), BigDecimal.ROUND_CEILING).intValue();
        }
        return max;
    }
}
