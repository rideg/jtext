package org.jtext.ui.graphics;

import org.jtext.ui.event.UIEvent;
import org.jtext.ui.layout.Layout;

import java.util.Set;

public class Container extends Widget {

    private Set<Widget> widgets;
    private Layout layout;

    public Container(final Layout layout) {
        this.layout = layout;
    }

    @Override
    public void draw(Graphics graphics) {
        widgets.forEach(widget -> widget.draw(graphics.restrict(layout.getAreaFor(widget))));
    }

    @Override
    public OccupationType getPreferredWidth() {
        return OccupationType.fill();
    }

    @Override
    public OccupationType getPreferredHeight() {
        return OccupationType.fill();
    }

    @Override
    public Position getPosition() {
        return null;
    }

    public void setArea(Rectangle area) {
        layout.setArea(area);
    }

    @Override
    protected void handleEvent(UIEvent event) {

    }
}
