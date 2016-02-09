package org.jtext.ui.graphics;

import java.util.LinkedList;
import java.util.List;

public abstract class WidgetContainer extends Widget {

    private final List<Widget> elements = new LinkedList<>();
    private final LayoutManager layoutManager;

    public WidgetContainer(int id, Widget parent, LayoutManager layoutManager) {
        super(id, parent);
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
