package org.jtext.ui.layout;

import org.jtext.ui.attribute.Direction;
import org.jtext.ui.attribute.HorizontalAlign;
import org.jtext.ui.attribute.VerticalAlign;

public final class Layouts {

    private Layouts() {

    }

    public static LinearLayout vertical() {
        return new LinearLayout(Direction.VERTICAL, HorizontalAlign.LEFT, VerticalAlign.TOP);
    }

    public static LinearLayout horizontal() {
        return new LinearLayout(Direction.HORIZONTAL, HorizontalAlign.LEFT, VerticalAlign.TOP);
    }

    public static LinearLayout vertical(final VerticalAlign verticalAlign) {
        return new LinearLayout(Direction.VERTICAL, HorizontalAlign.LEFT, verticalAlign);
    }

    public static LinearLayout horizontal(final VerticalAlign verticalAlign) {
        return new LinearLayout(Direction.HORIZONTAL, HorizontalAlign.LEFT, verticalAlign);
    }

    public static LinearLayout vertical(final HorizontalAlign horizontalAlign) {
        return new LinearLayout(Direction.VERTICAL, horizontalAlign, VerticalAlign.TOP);
    }

    public static LinearLayout horizontal(final HorizontalAlign horizontalAlign) {
        return new LinearLayout(Direction.HORIZONTAL, horizontalAlign, VerticalAlign.TOP);
    }

    public static LinearLayout vertical(final HorizontalAlign horizontalAlign, final VerticalAlign verticalAlign) {
        return new LinearLayout(Direction.VERTICAL, horizontalAlign, verticalAlign);
    }

    public static LinearLayout horizontal(final HorizontalAlign horizontalAlign, final VerticalAlign verticalAlign) {
        return new LinearLayout(Direction.HORIZONTAL, horizontalAlign, verticalAlign);
    }

}
