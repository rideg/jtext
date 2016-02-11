package org.jtext.ui.attribute;

import org.jtext.ui.graphics.Rectangle;

public class Padding {

    public static final Padding NO_PADDING = Padding.of(0, 0, 0, 0);

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


    public static Padding of(final int top, final int right, final int bottom, final int left) {
        return new Padding(top, right, bottom, left);
    }

    public Padding inc() {
        return Padding.of(top + 1, right + 1, bottom + 1, left + 1);
    }

    public Rectangle shrink(final Rectangle area) {
        return Rectangle.of(area.x + left, area.y + top, area.width - left - right, area.height - top - bottom);
    }

    public Padding apply(final Border border) {
        return Padding.of(top + border.getTopThickness(),
                          right + border.getRightThickness(),
                          bottom + border.getBottomThickness(),
                          left + border.getLeftThickness()
                         );
    }
}
