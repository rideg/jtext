package org.jtext.ui.layout;

import org.jtext.ui.graphics.Dimension;
import org.jtext.ui.graphics.Rectangle;
import org.jtext.ui.graphics.Widget;

import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Layout {

    protected final List<Widget> widgetsInOrder = new LinkedList<>();
    protected final Map<Widget, Rectangle> widgets = new IdentityHashMap<>();
    protected Dimension dimension;

    public void addWidget(final Widget widget) {
        widgetsInOrder.add(widget);
        widgets.put(widget, Rectangle.empty());
    }

    public void removeWidget(final Widget widget) {
        widgets.remove(widget);
        widgetsInOrder.remove(widget);
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
