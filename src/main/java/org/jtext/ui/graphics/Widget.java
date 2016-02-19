package org.jtext.ui.graphics;

import org.jtext.ui.attribute.Margin;
import org.jtext.ui.event.UIEvent;

import java.util.Optional;

public abstract class Widget {

    private Optional<Widget> parent;
    private Margin margin = Margin.no();
    private boolean visible = true;

    public Widget() {
        this.parent = Optional.empty();
    }

    public Widget(final Widget widget) {
        this.parent = Optional.of(widget);
    }

    public abstract void draw(Graphics graphics);

    public abstract Occupation getPreferredWidth();

    public abstract Occupation getPreferredHeight();

    public Occupation getMinWidth() {
        return getPreferredWidth();
    }

    public Occupation getMinHeight() {
        return getPreferredHeight();
    }

    public Occupation getMaxWidth() {
        return getPreferredWidth();
    }

    public Occupation getMaxHeight() {
        return getPreferredHeight();
    }

    public abstract Position getPosition();

    public Margin getMargin() {
        return margin;
    }

    public void setMargin(final Margin margin) {
        this.margin = margin == null ? Margin.no() : margin;
    }

    public void clearParent() {
        parent = Optional.empty();
    }

    public final void onEvent(final UIEvent event) {
        handleEvent(event);
        if (event.isBubbling()) {
            parent.ifPresent(p -> p.onEvent(event));
        }
    }

    public Optional<Widget> getParent() {
        return parent;
    }

    public void setParent(final Widget widget) {
        parent = Optional.of(widget);
    }

    public void hide() {
        visible = false;
    }

    public void show() {
        visible = true;
    }

    public boolean isVisible() {
        return visible;
    }

    protected void handleEvent(final UIEvent event) {

    }
}
