package org.jtext.ui.layout;

import org.jtext.ui.graphics.Occupation;
import org.jtext.ui.widget.MenuElement;

public class GridSelectorLayout extends Layout<MenuElement> {


    @Override
    protected void updateWidgetAreas() {

    }

    public Occupation getPreferredHeight() {
        return Occupation.fill();
    }
}
