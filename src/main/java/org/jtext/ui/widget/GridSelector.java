package org.jtext.ui.widget;

import org.jtext.ui.graphics.Container;
import org.jtext.ui.graphics.Occupation;
import org.jtext.ui.graphics.Position;
import org.jtext.ui.layout.GridSelectorLayout;

import java.util.List;

public class GridSelector extends Container<MenuElement> {

    private final int maxWidth;
    private final int maxHeight;

    private final GridSelectorLayout layout;

    public GridSelector(final int maxWidth, final int maxHeight, final List<MenuElement> items) {
        this(new GridSelectorLayout(), maxWidth, maxHeight, items);
    }

    private GridSelector(final GridSelectorLayout layout, final int maxWidth, final int maxHeight,
                         final List<MenuElement> items) {
        super(layout);
        this.layout = layout;
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        items.stream().forEach(this::add);
    }

    @Override
    public Occupation getPreferredWidth() {
        return Occupation.fixed(maxWidth);
    }

    @Override
    public Occupation getPreferredHeight() {
        if (layout.getPreferredHeight().toReal(layout.getDimension().getHeight()) > maxHeight) {
            return Occupation.fixed(maxHeight);
        }
        return layout.getPreferredHeight();
    }

    @Override
    public Position getPosition() {
        return Position.RELATIVE;
    }

}
