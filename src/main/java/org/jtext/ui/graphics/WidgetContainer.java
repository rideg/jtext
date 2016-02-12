package org.jtext.ui.graphics;

import org.jtext.ui.layout.LayoutManager;

import java.util.LinkedList;
import java.util.List;

public abstract class WidgetContainer extends Widget {

    private final List<Widget> elements = new LinkedList<>();
    private final LayoutManager layoutManager;

    public WidgetContainer(final Widget parent, final LayoutManager layoutManager) {
        super(parent);
        this.layoutManager = layoutManager;
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
