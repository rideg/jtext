package org.jtext.ui.graphics;

public final class Selection {

    public static final Selection NONE = Selection.of(0, 0);

    private final int start;
    private final int end;

    private Selection(final int start, final int end) {
        this.start = start;
        this.end = end;
    }

    public static Selection of(final int start, final int end) {
        return new Selection(start, end);
    }

    public Selection increment() {
        return Selection.of(start, end + 1);
    }

    public Selection increment(final int increment) {
        return Selection.of(start, end + increment);
    }

    public Selection decrement() {
        return Selection.of(start, end - 1);
    }

    public Selection decrement(final int increment) {
        return Selection.of(start, end - increment);
    }

    public int getStart() {
        return Math.min(start, end);
    }

    public int getEnd() {
        return Math.max(start, end);
    }

    public int length() {
        return getEnd() - getStart();
    }

    public boolean isRightToLeft() {
        return start > end;
    }

    public boolean isLeftToRight() {
        return start <= end;
    }
}
