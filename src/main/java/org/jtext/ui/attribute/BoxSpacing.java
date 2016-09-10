package org.jtext.ui.attribute;

import static java.lang.Integer.parseInt;

class BoxSpacing {

    private static final BoxSpacing NO_SPACING = new BoxSpacing(0, 0, 0, 0);

    private final int top;
    private final int right;
    private final int bottom;
    private final int left;

    private BoxSpacing(final int top, final int right, final int bottom, final int left) {
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
    }

    BoxSpacing(BoxSpacing spacing) {
        this.top = spacing.top;
        this.right = spacing.right;
        this.bottom = spacing.bottom;
        this.left = spacing.left;
    }

    static BoxSpacing no() {
        return NO_SPACING;
    }

    static BoxSpacing of(final int top, final int right, final int bottom, final int left) {
        return new BoxSpacing(top, right, bottom, left);
    }

    static BoxSpacing full(final int value) {
        return new BoxSpacing(value, value, value, value);
    }

    static BoxSpacing horizontal(final int value) {
        return new BoxSpacing(0, value, 0, value);
    }

    public static BoxSpacing vertical(final int value) {
        return new BoxSpacing(value, 0, value, 0);
    }

    public static BoxSpacing top(final int value) {
        return new BoxSpacing(value, 0, 0, 0);
    }

    static BoxSpacing bottom(final int value) {
        return new BoxSpacing(0, 0, value, 0);
    }

    static BoxSpacing left(final int value) {
        return new BoxSpacing(0, 0, 0, value);
    }

    static BoxSpacing right(final int value) {
        return new BoxSpacing(0, value, 0, 0);
    }

    static BoxSpacing parse(final String string) {
        if ("no".equals(string)) {
            return BoxSpacing.no();
        }
        final String[] pads = string.trim().split(" ");
        return BoxSpacing.of(parseInt(pads[0]), parseInt(pads[1]), parseInt(pads[2]), parseInt(pads[3]));
    }

    public int horizontalSpacing() {
        return right + left;
    }

    public int verticalSpacing() {
        return top + bottom;
    }

    public int getTop() {
        return top;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
    }

    public int getLeft() {
        return left;
    }

    @Override
    public String toString() {
        return "{" +
                "top=" + top +
                ", right=" + right +
                ", bottom=" + bottom +
                ", left=" + left +
                '}';
    }
}
