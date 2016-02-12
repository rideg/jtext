package org.jtext.ui.layout;

import org.jtext.ui.graphics.Rectangle;
import org.jtext.ui.graphics.Widget;

import java.util.IdentityHashMap;
import java.util.Map;

public abstract class Layout {

    private static final Object PLACEHOLDER = new Object();
    private final Map<Widget, Object> widgets = new IdentityHashMap<>();
    private Rectangle area;

    public void addWidget(final Widget widget) {
        widgets.put(widget, PLACEHOLDER);
    }

    public void removeWidget(final Widget widget) {
        widgets.remove(widget);
    }

    public void setArea(final Rectangle area) {
        this.area = area;
    }

    public abstract Rectangle getAreaFor(final Widget widget);
}
