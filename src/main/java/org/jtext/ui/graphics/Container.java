package org.jtext.ui.graphics;

import org.jtext.ui.layout.Layout;
import org.jtext.ui.theme.Theme;

public class Container<T extends Widget> extends Widget {

    private Layout<T> layout;

    public Container(final Layout<T> layout) {
        this.layout = layout;
    }

    @Override
    public void draw(Graphics graphics) {
        layout.getWidgets().stream().filter(Widget::isVisible)
                .forEach(widget -> widget.draw(graphics.restrict(layout.getAreaFor(widget))));
    }

    @Override
    public void setTheme(final Theme theme) {
        super.setTheme(theme);
        for (final Widget widget : layout.getWidgets()) {
            widget.setTheme(theme.useFor(widget.getClass()));
        }
    }

    @Override
    public void setRepaintRequester(final Runnable repaintRequester) {
        super.setRepaintRequester(repaintRequester);
        for (final Widget widget : layout.getWidgets()) {
            widget.setRepaintRequester(repaintRequester);
        }
    }

    public Layout<T> getLayout() {
        return layout;
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
        layout.getWidgets().stream().filter(w -> w instanceof Container && w.isVisible())
                .forEach(w -> ((Container) w).setArea(layout.getAreaFor(w)));

    }

    public Container add(final T widget) {
        layout.addWidget(widget);
        widget.setParent(this);
        widget.setRepaintRequester(getRepaintRequester());
        return this;
    }

    public void remove(final T widget) {
        layout.removeWidget(widget);
        widget.clearParent();
        widget.clearRepaintRequester();
    }
}
