package org.jtext.ui.theme;

import org.jtext.curses.Color;
import org.jtext.curses.Driver;
import org.jtext.ui.attribute.Border;
import org.jtext.ui.attribute.Padding;
import org.jtext.ui.graphics.Widget;

public class ThemeImpl implements Theme {

    private final Class<? extends Widget> widgetType;
    private final ThemeProvider themeProvider;
    private final ColorManager colorManager;
    private final BorderProvider borderProvider;

    public ThemeImpl(final Driver driver,
                     final ThemeProvider themeProvider) {
        this.widgetType = null;
        this.themeProvider = themeProvider;
        this.colorManager = new ColorManager(driver, themeProvider);
        this.borderProvider = new BorderProvider(colorManager, themeProvider.getBorders());
    }

    public ThemeImpl(final Driver driver) {
        this(driver, ThemeProvider.loadDefault());
    }

    private ThemeImpl(final Class<? extends Widget> widgetType, final ThemeProvider themeProvider,
                      final ColorManager colorManager, final BorderProvider borderProvider) {
        this.widgetType = widgetType;
        this.themeProvider = themeProvider;
        this.colorManager = colorManager;
        this.borderProvider = borderProvider;
    }

    @Override
    public Border getBorder(final String identifier) {
        return borderProvider.getBorder(themeProvider.getWidgetConfig(widgetType).getString(identifier, "no"));
    }

    @Override
    public Padding getPadding(final String name) {
        return Padding.parse(themeProvider.getWidgetConfig(widgetType).getString(name, "no"));
    }

    @Override
    public Color getColor(final String name) {
        return colorManager.getColor(themeProvider.getWidgetConfig(widgetType).getString(name, null));
    }

    @Override
    public String getString(final String name) {
        return themeProvider.getWidgetConfig(widgetType).getString(name, null);
    }

    @Override
    public long getLong(final String name) {
        return themeProvider.getWidgetConfig(widgetType).getLong(name, 0L);
    }

    @Override
    public int getInt(final String name) {
        return themeProvider.getWidgetConfig(widgetType).getInt(name, 0);
    }

    @Override
    public boolean getBoolean(final String name) {
        return themeProvider.getWidgetConfig(widgetType).getBoolean(name, false);
    }

    @Override
    public ThemeImpl useFor(final Class<? extends Widget> widgetType) {
        return new ThemeImpl(widgetType, themeProvider, colorManager, borderProvider);
    }

    public Color getColorByName(final String color) {
        return colorManager.getColor(color);
    }

    public ColorManager getColorManager() {
        return colorManager;
    }

    public void initialise() {
        colorManager.registerColors();
    }
}
