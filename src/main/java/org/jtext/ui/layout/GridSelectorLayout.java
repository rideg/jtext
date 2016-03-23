package org.jtext.ui.layout;

import org.jtext.ui.graphics.Occupation;
import org.jtext.ui.graphics.Rectangle;
import org.jtext.ui.util.Util;
import org.jtext.ui.widget.MenuElement;

public class GridSelectorLayout extends Layout<MenuElement> {

    private final int maxWidth;
    private int maximalWidgetWidth;

    public GridSelectorLayout(final int maxWidth) {
        super();
        this.maxWidth = maxWidth;
    }

    @Override
    protected void updateWidgetAreas() {
        final int rows = getRequiredNumberOfRows();
        int y = 0;
        int x = 0;
        for (MenuElement menuElement : widgetsInOrder) {
            widgets.put(menuElement, Rectangle.of(x, y,
                    menuElement.getPreferredWidth().toReal(maximalWidgetWidth + 1),
                    menuElement.getPreferredHeight().toReal(1)));
            y++;
            if (y == rows) {
                y = 0;
                x += maximalWidgetWidth + 1;
            }
        }
    }

    @Override
    public void addWidget(final MenuElement widget) {
        int widgetWidth = widget.getPreferredWidth().toReal(0);
        if (widgetWidth > maximalWidgetWidth) {
            maximalWidgetWidth = widgetWidth;
        }
        super.addWidget(widget);
    }

    public Occupation getPreferredHeight() {
        return Occupation.fixed(getRequiredNumberOfRows());
    }

    private int getRequiredNumberOfRows() {
        final int columns = maxWidth / (maximalWidgetWidth + 1);
        return Util.divUp(getWidgets().size(), columns);
    }
}
