package org.jtext.ui.graphics;

import org.jtext.event.Event;
import org.jtext.event.Topic;
import org.jtext.ui.attribute.Margin;
import org.jtext.ui.event.UIEvent;
import org.jtext.ui.theme.Theme;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public abstract class Widget {

    private final Map<Class<? extends UIEvent>, Set<Consumer<? extends UIEvent>>> eventHandlers = new HashMap<>();
    private Widget parent;
    private Margin margin = Margin.no();
    private boolean visible = true;
    private Theme theme;
    private Runnable repaintRequester = () -> {
    };

    public Widget() {
    }

    public Widget(final Widget widget) {
        this.parent = widget;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(final Theme theme) {
        this.theme = theme;
    }

    public void requestRepaint() {
        repaintRequester.run();
    }

    public Runnable getRepaintRequester() {
        return repaintRequester;
    }

    public void setRepaintRequester(final Runnable repaintRequester) {
        this.repaintRequester = repaintRequester;
    }

    public void clearRepaintRequester() {
        repaintRequester = () -> {
        };
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
        parent = null;
    }

    public <T extends UIEvent> void addHandler(final Class<T> type, final Consumer<T> handler) {
        eventHandlers.computeIfAbsent(type, t -> new HashSet<>()).add(handler);
    }

    @SuppressWarnings("unchecked")
    public final void onEvent(final UIEvent event) {
        if (eventHandlers.containsKey(event.getClass())) {
            for (Consumer c : eventHandlers.get(event.getClass())) {
                if (event.isPropagating()) {
                    c.accept(event);
                }
            }
        }
        if (event.isPropagating()) {
            handleEvent(event);
            if (event.isBubbling()) {
               getParent().ifPresent(p -> p.onEvent(event));
            }
        }
    }

    public Optional<Widget> getParent() {
        return Optional.ofNullable(parent);
    }

    public void setParent(final Widget widget) {
        parent = widget;
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

    protected <T extends Event> void emit(final Topic<T> topic, final T event) {
        getParent().ifPresent(w -> w.emit(topic, event));
    }
}
