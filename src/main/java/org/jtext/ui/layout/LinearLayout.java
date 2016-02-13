package org.jtext.ui.layout;

import org.jtext.ui.attribute.Align;
import org.jtext.ui.attribute.Direction;

public class LinearLayout extends Layout {

    private final Direction direction;
    private final Align align;

    public LinearLayout(Direction direction, Align align) {
        this.direction = direction;
        this.align = align;
    }

    @Override
    protected void updateWidgetAreas() {

    }
}
