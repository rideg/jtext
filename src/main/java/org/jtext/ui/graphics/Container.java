package org.jtext.ui.graphics;

import org.jtext.ui.layout.Layout;
import org.jtext.ui.theme.Theme;

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
        for (final Widget widget : widgets) {
            if (widget.isVisible()) {
                widget.draw(graphics.restrict(layout.getAreaFor(widget)));
            }
        }
    }

    @Override
    public void setTheme(final Theme theme) {
        super.setTheme(theme);
        for (final Widget widget : widgets) {
            widget.setTheme(theme.useFor(widget.getClass()));
        }
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
        layout.setDimension(area.dimension());
        for (Widget w : widgets) {
            if (w instanceof Container && w.isVisible()) {
                ((Container) w).setArea(layout.getAreaFor(w));
            }
        }

    }

    public Container add(final Widget widget) {
        layout.addWidget(widget);
        widgets.add(widget);
        widget.setParent(this);
        return this;
    }

    public void remove(final Widget widget) {
        layout.addWidget(widget);
        widgets.remove(widget);
        widget.clearParent();
    }
}
