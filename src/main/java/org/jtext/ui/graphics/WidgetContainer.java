package org.jtext.ui.graphics;

import org.jtext.ui.layout.Layout;

import java.util.LinkedList;
import java.util.List;

public abstract class WidgetContainer extends Widget {

    private final List<Widget> elements = new LinkedList<>();
    private final Layout layout;

    public WidgetContainer(final Widget parent, final Layout layout) {
        super(parent);
        this.layout = layout;
    }


    public void addWidget(final Widget widget) {
        widget.setParent(this);
        elements.add(widget);
    }

    public void removeWidget(final Widget widget) {
        elements.remove(widget);
        widget.clearParent();
    }


}
