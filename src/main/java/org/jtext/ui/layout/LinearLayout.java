package org.jtext.ui.layout;

import org.jtext.ui.attribute.Align;
import org.jtext.ui.attribute.Direction;
import org.jtext.ui.graphics.Widget;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class LinearLayout extends Layout {

    private final Direction direction;
    private final Align align;

    public LinearLayout(Direction direction, Align align) {
        this.direction = direction;
        this.align = align;
    }

    @Override
    protected void updateWidgetAreas() {
        Set<Widget> visibleWidgets = widgets.keySet()
                .stream()
                .filter(Widget::isVisible)
                .collect(toSet());


    }
}
