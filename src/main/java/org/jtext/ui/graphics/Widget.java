package org.jtext.ui.graphics;

import org.jtext.ui.event.UIEvent;

import java.util.Optional;

public abstract class Widget {

    private final int id;
    private Optional<Widget> parent;

    public Widget(final int id) {
        this.id = id;
        this.parent = Optional.empty();
    }

    public Widget(final int id, final Widget widget) {
        this.id = id;
        this.parent = Optional.of(widget);
    }

    public int getId() {
        return id;
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

    public final void onEvent(final UIEvent event) {
        handleEvent(event);
        if (event.isBubbling()) {
            parent.ifPresent(p -> p.handleEvent(event));
        }
    }

    protected abstract void handleEvent(final UIEvent event);

}
