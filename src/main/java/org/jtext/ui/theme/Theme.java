package org.jtext.ui.theme;

import org.jtext.curses.ColorName;
import org.jtext.ui.attribute.Border;
import org.jtext.ui.attribute.Padding;
import org.jtext.ui.graphics.Widget;

public interface Theme {

    Border getBorder(final String description);

    Padding getPadding(final String description);

    ColorName getColor(String name);

    String getString(String name);

    long getLong(String name);

    int getInt(String name);

    boolean getBoolean(String name);

    ThemeImpl useFor(Class<? extends Widget> widgetType);
}
