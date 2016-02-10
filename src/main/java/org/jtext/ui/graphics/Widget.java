package org.jtext.ui.graphics;

import org.jtext.ui.event.UIEvent;

import java.util.Optional;

public abstract class Widget {

    private final int id;
    private Optional<Widget> parent;
    private Rectangle area;

    public Widget(final int id) {
        this.id = id;
        this.parent = Optional.empty();
    }

    public Widget(final int id, final Widget widget) {
        this.id = id;
        this.parent = Optional.of(widget);
    }

    public abstract void draw(Graphics graphics);

    public abstract OccupationType getPreferredWidth();

    public abstract OccupationType getPreferredHeight();

    public abstract Position getPosition();

    public void clearParent() {
        parent = Optional.empty();
    }

    public void setParent(final Widget widget) {
        parent = Optional.of(widget);
    }

    public void setArea(Rectangle area) {
        this.area = area;
        reValidate();
    }

    public final void onEvent(final UIEvent event) {
        handleEvent(event);
        if (event.isBubbling()) {
            parent.ifPresent(p -> p.handleEvent(event));
        }
    }

    protected abstract void reValidate(); //what is the aim of this?

    protected abstract void handleEvent(final UIEvent event);

}
