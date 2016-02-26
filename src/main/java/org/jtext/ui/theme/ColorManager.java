package org.jtext.ui.theme;

import com.eclipsesource.json.JsonObject;
import org.jtext.curses.ColorName;
import org.jtext.curses.Driver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ColorManager {

    public static final int MAXIMUM_NUMBER_OF_COLORS = 256;
    private final Map<String, ColorName> nameToColor;
    private final int[][] colorPairs;
    private final Driver driver;

    public ColorManager(final Driver driver, final JsonObject colorDefinitions) {
        this.driver = driver;
        nameToColor = new HashMap<>();
        colorPairs = new int[MAXIMUM_NUMBER_OF_COLORS][MAXIMUM_NUMBER_OF_COLORS];
        initialise(colorDefinitions);
    }

    public void initColorPairs() {
        for (int i = 0; i < colorPairs.length; i++) {
            for (int j = 0; j < colorPairs.length; j++) {
                if (colorPairs[i][j] != -1) {
                    driver.initColorPair(colorPairs[i][j], i, j);
                }
            }
        }

    }

    private void initialise(final JsonObject colorDefinitions) {
        defineColorPairs();
        Arrays.stream(ColorName.values()).forEach(v -> nameToColor.put(v.name(), v));
        for (final JsonObject.Member member : colorDefinitions) {
            if (member.getValue().isNumber()) {
                int index = member.getValue().asInt();
                if (index >= 0 && index < 256) {
                    nameToColor.putIfAbsent(member.getName(), ColorName.values()[index]);
                }
            }
            if (member.getValue().isString()) {
                String keyName = member.getValue().asString().toUpperCase().replace('-', '_');
                if (nameToColor.containsKey(keyName)) {
                    nameToColor.putIfAbsent(member.getName(), ColorName.valueOf(keyName));
                }
            }
        }
    }

    private void defineColorPairs() {
        int i = 0;
        for (final ColorName foreground : ColorName.values()) {
            for (final ColorName background : ColorName.values()) {
                colorPairs[foreground.ordinal()][background.ordinal()] = i++;
            }
        }
    }

    public ColorName getColor(final String colorName) {
        if (nameToColor.containsKey(colorName)) {
            return nameToColor.get(colorName);
        }
        return nameToColor.get(colorName.toUpperCase().replace('-', '_'));
    }

    public int getPairId(final ColorName foreground, final ColorName background) {
        return colorPairs[foreground.ordinal()][background.ordinal()];
    }
}
