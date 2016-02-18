package org.jtext.ui.attribute;

@SuppressWarnings("checkstyle:visibilitymodifier")
public final class Margin {

    private static final Margin NO_MARGIN = Margin.of(0, 0, 0, 0);

    public final int top;
    public final int right;
    public final int bottom;
    public final int left;

    private Margin(final int top, final int right, final int bottom, final int left) {
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
    }

    public static Margin no() {
        return NO_MARGIN;
    }

    public static Margin of(final int top, final int right, final int bottom, final int left) {
        return new Margin(top, right, bottom, left);
    }

    public int horizontalSpacing() {
        return right + left;
    }

    public int verticalSpacing() {
        return top + bottom;
    }

    @Override
    public String toString() {
        return "Margin{" +
               "top=" + top +
               ", right=" + right +
               ", bottom=" + bottom +
               ", left=" + left +
               '}';
    }

    public Slice getSlice(final Direction direction) {
        return direction == Direction.HORIZONTAL ? new Slice(left, right) : new Slice(top, bottom);
    }

    @SuppressWarnings("checkstyle:visibilitymodifier")
    public static final class Slice {
        public final int begin;
        public final int end;

        private Slice(final int begin, final int end) {
            this.begin = begin;
            this.end = end;
        }

        public int getSpacing() {
            return begin + end;
        }
    }
}
