package org.jtext.ui.graphics;

import org.jtext.ui.event.UIEvent;
import org.jtext.ui.layout.Layout;

import java.util.HashSet;
import java.util.Set;

public class Container extends Widget {

    private Set<Widget> widgets = new HashSet<>();
    private Layout layout;

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
        return null;
    }

    @Override
    public Occupation getMinHeight() {
        return null;
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

    public Container add(final Widget widget) {
        widgets.add(widget);
        return this;
    }

    public void remove(final Widget widget) {
        widgets.remove(widget);
    }
}
