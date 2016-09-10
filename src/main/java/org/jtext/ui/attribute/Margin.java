package org.jtext.ui.attribute;

public final class Margin extends BoxSpacing {

    private Margin(BoxSpacing spacing) {
        super(spacing);
    }

    private static Margin from(BoxSpacing reference) {
        return new Margin(reference);
    }

    public static Margin no() {
        return from(BoxSpacing.no());
    }

    public static Margin of(final int top, final int right, final int bottom, final int left) {
        return from(BoxSpacing.of(top, right, bottom, left));
    }

    @Override
    public String toString() {
        return "Margin" + super.toString();
    }

    public Slice getSlice(final Direction direction) {
        return direction == Direction.HORIZONTAL ? new Slice(getLeft(), getRight()) : new Slice(getTop(), getBottom());
    }

    public static final class Slice {
        private final int begin;
        private final int end;

        private Slice(final int begin, final int end) {
            this.begin = begin;
            this.end = end;
        }

        public int getSpacing() {
            return begin + end;
        }

        public int getBegin() {
            return begin;
        }

        public int getEnd() {
            return end;
        }
    }
}
