package org.jtext.ui.theme;

import org.jtext.curses.BaseColor;
import org.jtext.curses.Color;
import org.jtext.curses.Driver;
import org.jtext.curses.RgbValue;

import java.util.HashMap;
import java.util.Map;

public class ColorManager {

    public static final int MAXIMUM_NUMBER_OF_COLORS = 256;
    private final Map<String, Color> nameToColor;
    private final Map<Integer, Color> idToColor;
    private final int[][] colorPairs;
    private final Driver driver;
    private ThemeProvider themeProvider;

    public ColorManager(final Driver driver, final ThemeProvider themeProvider) {
        this.driver = driver;
        this.themeProvider = themeProvider;
        nameToColor = new HashMap<>();
        idToColor = new HashMap<>();
        colorPairs = new int[MAXIMUM_NUMBER_OF_COLORS][MAXIMUM_NUMBER_OF_COLORS];
        initialise(themeProvider.getDefinedColors());
    }

    private void initialise(final Map<String, RgbValue> colors) {
        if (colors.size() > MAXIMUM_NUMBER_OF_COLORS) {
            throw new IllegalStateException("Cannot support more than 256 colors");
        }
        registerColors(colors);
        initColorPairs();
    }

    private void registerColors(final Map<String, RgbValue> colors) {
        nameToColor.clear();
        idToColor.clear();
        registerBasicColors(colors);
        int idIndex = BaseColor.values().length;
        for (Map.Entry<String, RgbValue> entry : colors.entrySet()) {
            registerAndStoreColor(idIndex, entry.getValue(), entry.getKey());
            idIndex++;
        }
    }

    private void registerAndStoreColor(final int idIndex, final RgbValue value, final String name) {
        driver.initColor(idIndex, value.cursesRed(), value.cursesGreen(), value.cursesBlue());
        Color color = new Color(idIndex, name, value);
        nameToColor.put(name, color);
        idToColor.put(idIndex, color);
    }

    private void registerBasicColors(final Map<String, RgbValue> colors) {
        for (final BaseColor c : BaseColor.values()) {
            final String name = c.name().toLowerCase();
            final RgbValue rgbValue = colors.remove(name);
            if (rgbValue != null) {
                registerAndStoreColor(c.ordinal(), rgbValue, name);
            }
        }
    }

    private void initColorPairs() {
        int i = 0;
        for (final Color foreground : nameToColor.values()) {
            for (final Color background : nameToColor.values()) {
                colorPairs[foreground.getColorId()][background.getColorId()] = i;
                driver.initColorPair(i, foreground.getColorId(), background.getColorId());
                i++;
            }
        }
    }

    public Color getColor(final int colorId) {
        return idToColor.get(colorId);
    }

    public Color getColor(final String name) {
        String colorName = themeProvider.getColorValue(name);
        while (colorName != null && !nameToColor.containsKey(colorName)) {
            colorName = themeProvider.getColorValue(colorName);
        }
        if (!nameToColor.containsKey(name) && colorName != null) {
            nameToColor.put(name, nameToColor.get(colorName));
        }
        return colorName == null ? null : nameToColor.get(name);
    }

    public int getPairId(final Color foreground, final Color background) {
        return colorPairs[foreground.getColorId()][background.getColorId()];
    }
}
