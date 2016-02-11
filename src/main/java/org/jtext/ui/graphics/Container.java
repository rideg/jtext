package org.jtext.ui.graphics;

import org.jtext.ui.event.UIEvent;

import java.util.Set;

public class Container extends Widget {

    private Set<Widget> widgets;
    private LayoutManager layoutManager;

    public Container(final int id, final LayoutManager layoutManager) {
        super(id);
        this.layoutManager = layoutManager;
    }

    @Override
    public void draw(Graphics graphics) {
        widgets.forEach(widget -> widget.draw(new Graphics(layoutManager.getAreaFor(widget), graphics)));
    }

    @Override
    public OccupationType getPreferredWidth() {
        return layoutManager.getPreferredWidth();
    }

    @Override
    public OccupationType getPreferredHeight() {
        return layoutManager.getPreferredHeight();
    }

    @Override
    public Position getPosition() {
        return null;
    }

    public void setArea(Rectangle area) {
        layoutManager.setArea(area);
    }

    @Override
    protected void handleEvent(UIEvent event) {

    }
}
