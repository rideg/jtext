package org.jtext.ui.layout;

import org.jtext.ui.graphics.Dimension;
import org.jtext.ui.graphics.Rectangle;
import org.jtext.ui.graphics.Widget;

import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Layout {

    protected final List<Widget> widgetOrder = new LinkedList<>();
    protected final Map<Widget, Rectangle> widgets = new IdentityHashMap<>();
    protected Dimension dimension;

    public void addWidget(final Widget widget) {
        validate(widget);
        widgetOrder.add(widget);
        widgets.put(widget, Rectangle.empty());
    }

    private void validate(final Widget widget) {
        if (widget.getPreferredWidth().toReal(Integer.MAX_VALUE) > widget.getMaxWidth().toReal(Integer.MAX_VALUE)) {
            throw new IllegalArgumentException("Preferred width must be smaller or equal to maximum width");
        }
        if (widget.getPreferredWidth().toReal(Integer.MAX_VALUE) < widget.getMinWidth().toReal(Integer.MAX_VALUE)) {
            throw new IllegalArgumentException("Preferred width must be greater or equal to minimum width");
        }

        if (widget.getPreferredHeight().toReal(Integer.MAX_VALUE) > widget.getMaxHeight().toReal(Integer.MAX_VALUE)) {
            throw new IllegalArgumentException("Preferred height must be smaller or equal to maximum width");
        }
        if (widget.getPreferredHeight().toReal(Integer.MAX_VALUE) < widget.getMinHeight().toReal(Integer.MAX_VALUE)) {
            throw new IllegalArgumentException("Preferred height must be greater or equal to minimum width");
        }
    }

    public void removeWidget(final Widget widget) {
        widgets.remove(widget);
        widgetOrder.remove(widget);
    }

    public void setDimension(final Dimension dimension) {
        this.dimension = dimension;
        updateWidgetAreas();
    }

    protected abstract void updateWidgetAreas();

    public Rectangle getAreaFor(final Widget widget) {
        return widgets.get(widget);
    }
}
