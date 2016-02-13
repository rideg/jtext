package org.jtext.ui.layout;

import org.jtext.ui.attribute.Align;
import org.jtext.ui.attribute.Direction;

public class Layouts {

    public static LinearLayout vertical(final Align align) {
        return new LinearLayout(Direction.VERTICAL, align);
    }

    public static LinearLayout horizontal(final Align align) {
        return new LinearLayout(Direction.HORIZONTAL, align);
    }
}
