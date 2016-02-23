package org.jtext.ui.theme;

import org.jtext.curses.Color;
import org.jtext.ui.attribute.Border;
import org.jtext.ui.attribute.Padding;
import org.jtext.ui.graphics.Widget;

public class Theme {

    private final Class<? extends Widget> widgetType;
    private final ThemeProvider themeProvider;
    private final ColorProvider colorProvider;
    private final BorderProvider borderProvider;

    public Theme(final Class<? extends Widget> widgetType,
                 final ThemeProvider themeProvider,
                 final ColorProvider colorProvider,
                 final BorderProvider borderProvider) {
        this.widgetType = widgetType;
        this.themeProvider = themeProvider;
        this.colorProvider = colorProvider;
        this.borderProvider = borderProvider;
    }

    public Border getBorder(final String name) {
        return borderProvider.getBorder(themeProvider.getWidgetConfig(widgetType).getString(name, "no"));
    }

    public Padding getPadding(final String name) {
        return Padding.parse(themeProvider.getWidgetConfig(widgetType).getString(name, "no"));
    }

    public Color getColor(final String name) {
        return colorProvider.getColor(name);
    }

    public String getString(final String name) {
        return themeProvider.getWidgetConfig(widgetType).getString(name, null);
    }

    public long getLong(final String name) {
        return themeProvider.getWidgetConfig(widgetType).getLong(name, 0L);
    }

    public int getInt(final String name) {
        return themeProvider.getWidgetConfig(widgetType).getInt(name, 0);
    }

    public boolean getBoolean(final String name) {
        return themeProvider.getWidgetConfig(widgetType).getBoolean(name, false);
    }

    public Theme useFor(final Class<? extends Widget> widgetType) {
        return new Theme(widgetType, themeProvider, colorProvider, borderProvider);
    }
}
