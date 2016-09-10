package org.jtext.ui.attribute;

import org.jtext.curses.Point;
import org.jtext.ui.graphics.Dimension;
import org.jtext.ui.graphics.Rectangle;

public final class Padding extends BoxSpacing {

    private Padding(BoxSpacing spacing) {
        super(spacing);
    }

    private static Padding from(BoxSpacing reference) {
        return new Padding(reference);
    }

    public static Padding no() {
        return from(BoxSpacing.no());
    }

    public static Padding of(final int top, final int right, final int bottom, final int left) {
        return from(BoxSpacing.of(top, right, bottom, left));
    }

    public static Padding full(final int value) {
        return from(BoxSpacing.full(value));
    }

    public static Padding horizontal(final int value) {
        return from(BoxSpacing.horizontal(value));
    }

    public static Padding vertical(final int value) {
        return from(BoxSpacing.vertical(value));
    }

    public static Padding top(final int value) {
        return from(BoxSpacing.top(value));
    }

    public static Padding bottom(final int value) {
        return from(BoxSpacing.bottom(value));
    }

    public static Padding left(final int value) {
        return from(BoxSpacing.left(value));
    }

    public static Padding right(final int value) {
        return from(BoxSpacing.right(value));
    }

    public static Padding parse(final String string) {
        return from(BoxSpacing.parse(string));
    }

    public Padding inc() {
        return from(BoxSpacing.of(getTop() + 1, getRight() + 1, getBottom() + 1, getLeft() + 1));
    }

    public Rectangle apply(final Dimension dimension) {
        return Rectangle.of(Point.at(getLeft(), getTop()), dimension.shrink(horizontalSpacing(), verticalSpacing()));
    }

    public Rectangle apply(final Rectangle area) {
        return Rectangle.of(area.topLeft().shift(getLeft(), getTop()),
                area.shrink(horizontalSpacing(), verticalSpacing()));
    }

    public Padding include(final Border border) {
        return Padding.of(getTop() + border.getTopThickness(), getRight() + border.getRightThickness(),
                getBottom() + border.getBottomThickness(), getLeft() + border.getLeftThickness());
    }

    @Override
    public String toString() {
        return "Padding" + super.toString();
    }
}
