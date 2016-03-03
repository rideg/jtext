package org.jtext.ui.attribute;

import org.jtext.ui.graphics.Dimension;
import org.jtext.ui.graphics.Rectangle;

@SuppressWarnings("checkstyle:visibilitymodifier")
public final class Padding {

    private static final Padding NO_PADDING = Padding.of(0, 0, 0, 0);

    public final int top;
    public final int right;
    public final int bottom;
    public final int left;


    private Padding(final int top, final int right, final int bottom, final int left) {
        if (top < 0 || right < 0 || left < 0 || bottom < 0) {
            throw new IllegalArgumentException("Padding values must be greater than zero!");
        }

        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
    }


    public static Padding no() {
        return NO_PADDING;
    }

    public static Padding of(final int top, final int right, final int bottom, final int left) {
        return new Padding(top, right, bottom, left);
    }

    public static Padding full(final int value) {
        return new Padding(value, value, value, value);
    }

    public static Padding horizontal(final int value) {
        return new Padding(0, value, 0, value);
    }

    public static Padding vertical(final int value) {
        return new Padding(value, 0, value, 0);
    }

    public static Padding top(final int value) {
        return new Padding(value, 0, 0, 0);
    }

    public static Padding bottom(final int value) {
        return new Padding(0, 0, value, 0);
    }

    public static Padding left(final int value) {
        return new Padding(0, 0, 0, value);
    }

    public static Padding right(final int value) {
        return new Padding(0, value, 0, 0);
    }

    public static Padding parse(final String string) {
        if ("no".equals(string)) {
            return Padding.no();
        }
        final String[] pads = string.trim().split(" ");
        return Padding.of(Integer.parseInt(pads[0]),
                Integer.parseInt(pads[1]),
                Integer.parseInt(pads[2]),
                Integer.parseInt(pads[3]));
    }

    public Padding inc() {
        return Padding.of(top + 1, right + 1, bottom + 1, left + 1);
    }

    public Rectangle apply(final Dimension dimension) {
        return Rectangle.of(left, top, Math.max(dimension.width - left - right, 0),
                Math.max(dimension.height - top - bottom, 0));
    }

    public Rectangle apply(final Rectangle area) {
        return Rectangle.of(area.topLeft().shift(left, top), area.shrink(left + right, top + bottom));
    }

    public Padding consider(final Border border) {
        return Padding.of(top + border.getTopThickness(), right + border.getRightThickness(),
                bottom + border.getBottomThickness(), left + border.getLeftThickness());
    }

    @Override
    public String toString() {
        return "Padding{" +
                "top=" + top +
                ", right=" + right +
                ", bottom=" + bottom +
                ", left=" + left +
                '}';
    }
}
