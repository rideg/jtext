package org.jtext.ui.layout;

import org.jtext.ui.graphics.Dimension;
import org.jtext.ui.graphics.Rectangle;
import org.jtext.ui.graphics.Widget;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("checkstyle:visibilitymodifier")
public abstract class Layout<T extends Widget> {

    protected final List<T> widgetsInOrder = new LinkedList<>();
    protected final Map<T, Rectangle> widgets = new IdentityHashMap<>();
    protected Dimension dimension;

    public void addWidget(final T widget) {
        widgetsInOrder.add(widget);
        widgets.put(widget, Rectangle.empty());
    }

    public void removeWidget(final T widget) {
        widgets.remove(widget);
        widgetsInOrder.remove(widget);
    }

    protected abstract void updateWidgetAreas();

    public Rectangle getAreaFor(final T widget) {
        return widgets.get(widget);
    }

    public Collection<T> getWidgets() {
        return widgetsInOrder;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(final Dimension dimension) {
        this.dimension = dimension;
        updateWidgetAreas();
    }
}
