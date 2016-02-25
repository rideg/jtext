package org.jtext.ui.theme;

import org.jtext.curses.Color;
import org.jtext.curses.Driver;
import org.jtext.curses.RgbValue;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public class ColorManager {

    public static final int MAXIMUM_NUMBER_OF_COLORS = 240;
    // in order to not to clash with terminal emulators' default colors
    public static final int COLOR_START_POINT = 16;
    private static final Logger LOGGER = getLogger(ColorManager.class);
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

    public void registerColorPairs() {
        defineColors();
        for (int i = 0; i < colorPairs.length; i++) {
            for (int j = 0; j < colorPairs.length; j++) {
                if (colorPairs[i][j] != -1) {
                    driver.initColorPair(colorPairs[i][j], i, j);
                }
            }
        }

    }

    private void defineColors() {
        for (final Color c : idToColor.values()) {
            RgbValue rgbValue = c.getRgbValue();
            driver.initColor(c.getColorId(), rgbValue.getRed(), rgbValue.getGreen(), rgbValue.getBlue());
        }
    }

    private void initialise(final Map<String, RgbValue> colors) {
        if (colors.size() > MAXIMUM_NUMBER_OF_COLORS) {
            throw new IllegalStateException("Cannot support more than 240 colors");
        }
        registerColorPairs(colors);
        initColorPairs();
    }

    private void registerColorPairs(final Map<String, RgbValue> colors) {
        nameToColor.clear();
        idToColor.clear();
        int idIndex = COLOR_START_POINT;
        for (Map.Entry<String, RgbValue> entry : colors.entrySet()) {
            registerAndStoreColor(idIndex, entry.getValue(), entry.getKey());
            idIndex++;
        }
    }

    private void registerAndStoreColor(final int idIndex, final RgbValue value, final String name) {
        Color color = new Color(idIndex, name, value);
        nameToColor.put(name, color);
        idToColor.put(idIndex, color);
    }

    private void initColorPairs() {
        for (final int[] colorPair : colorPairs) {
            Arrays.fill(colorPair, -1);
        }

        int i = 0;
        for (final Color foreground : nameToColor.values()) {
            for (final Color background : nameToColor.values()) {
                colorPairs[foreground.getColorId()][background.getColorId()] = i++;
            }
        }
    }

    public Color getColor(final int colorId) {
        if (colorId == -1) {
            return nameToColor.get("black");
        }
        return idToColor.get(colorId);
    }

    public Color getColor(final String name) {
        if (nameToColor.containsKey(name)) {
            return nameToColor.get(name);
        } else {
            String colorName = themeProvider.getColorValue(name);
            while (colorName != null && !nameToColor.containsKey(colorName)) {
                colorName = themeProvider.getColorValue(colorName);
            }
            if (!nameToColor.containsKey(name) && colorName != null) {
                nameToColor.put(name, nameToColor.get(colorName));
            }
            return colorName == null ? null : nameToColor.get(name);
        }
    }

    public int getPairId(final Color foreground, final Color background) {
        return colorPairs[foreground.getColorId()][background.getColorId()];
    }
}
