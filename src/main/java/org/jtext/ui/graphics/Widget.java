package org.jtext.ui.graphics;

import org.jtext.ui.event.UIEvent;

import java.util.Optional;

public abstract class Widget {

    private Optional<Widget> parent;

    public Widget() {
        this.parent = Optional.empty();
    }

    public Widget(final Widget widget) {
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

    public final void onEvent(final UIEvent event) {
        handleEvent(event);
        if (event.isBubbling()) {
            parent.ifPresent(p -> p.handleEvent(event));
        }
    }

    protected void handleEvent(final UIEvent event) {

    }

}
