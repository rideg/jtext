package org.jtext.ui.graphics;

import org.jtext.ui.event.UIEvent;
import org.jtext.ui.layout.Layout;

import java.util.HashSet;
import java.util.Set;

public class Container extends Widget {

    private Set<Widget> widgets = new HashSet<>();
    private Layout layout;
    private Rectangle area;

    public Container(final Layout layout) {
        this.layout = layout;
    }

    @Override
    public void draw(Graphics graphics) {
        widgets.forEach(widget -> widget.draw(graphics.restrict(layout.getAreaFor(widget))));
    }

    @Override
    public Occupation getPreferredWidth() {
        return Occupation.fill();
    }

    @Override
    public Occupation getPreferredHeight() {
        return Occupation.fill();
    }

    @Override
    public Occupation getMinWidth() {
        return Occupation.fixed(1);
    }

    @Override
    public Occupation getMinHeight() {
        return Occupation.fixed(1);
    }

    @Override
    public Position getPosition() {
        return null;
    }

    public void setArea(final Rectangle area) {
        this.area = area;
        layout.setDimension(area.dimension());

        for (Widget w : widgets) {
            if (w instanceof Container) {
                ((Container) w).setArea(layout.getAreaFor(w));
            }
        }

    }

    @Override
    protected void handleEvent(UIEvent event) {

    }

    public Container add(final Widget widget) {
        layout.addWidget(widget);
        widgets.add(widget);
        return this;
    }

    public void remove(final Widget widget) {
        layout.addWidget(widget);
        widgets.remove(widget);
    }
}
