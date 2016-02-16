package org.jtext.ui.graphics;

import org.jtext.ui.attribute.Direction;
import org.jtext.ui.attribute.Margin;
import org.jtext.ui.event.UIEvent;

import java.util.Optional;

import static org.jtext.ui.graphics.Occupation.isFilling;

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

    public int getRealHeight(final int available) {
        final int realHeight = Math.min(getPreferredHeight().toReal(available), getMaxHeight().toReal(available));
        if (realHeight > available) {
            return Math.max(available, getMinHeight().toReal(available));
        }
        return realHeight;
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

    public void setParent(final Widget widget) {
        parent = Optional.of(widget);
    }

    public final void onEvent(final UIEvent event) {
        handleEvent(event);
        if (event.isBubbling()) {
            parent.ifPresent(p -> p.onEvent(event));
        }
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

    public WidgetDescriptor getDescriptor(final int availableSpace, final Direction direction) {
        if (direction == Direction.HORIZONTAL) {
            int pref = (isFilling(getPreferredWidth()) ? getMinWidth() : getPreferredWidth()).toReal(availableSpace);
            return new WidgetDescriptor(pref, getMinWidth().toReal(availableSpace),
                    getMaxWidth().toReal(availableSpace));
        } else {
            int pref = (isFilling(getPreferredHeight()) ? getMinHeight() : getPreferredHeight()).toReal(availableSpace);
            return new WidgetDescriptor(pref, getMinHeight().toReal(availableSpace),
                    getMaxHeight().toReal(availableSpace));
        }
    }

    public static class WidgetDescriptor implements Comparable<WidgetDescriptor> {

        public int toUse = -1;
        public int preferred;
        public int minimum;
        public int optional;
        public int maximum;

        public WidgetDescriptor(final int preferred, final int minimum, final int maximum) {
            this.preferred = preferred;
            this.minimum = minimum;
            this.optional = preferred - minimum;
            this.maximum = maximum;
        }

        @Override
        public int compareTo(final WidgetDescriptor o) {
            return Integer.compare(o.optional, optional);
        }
    }
}
