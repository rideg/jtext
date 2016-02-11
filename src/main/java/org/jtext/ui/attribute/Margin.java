package org.jtext.ui.attribute;

public class Margin {


    public static final Margin NO_MARGIN = Margin.of(0, 0, 0, 0);

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

    public static Margin of(final int top, final int right, final int bottom, final int left) {
        return new Margin(top, right, bottom, left);
    }
}
