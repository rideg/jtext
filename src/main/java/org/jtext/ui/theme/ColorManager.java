package org.jtext.ui.theme;

import com.eclipsesource.json.JsonObject;
import org.jtext.curses.ColorName;
import org.jtext.curses.Driver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ColorManager {

    public static final int MAXIMUM_NUMBER_OF_COLORS = 257;
    private final Map<String, ColorName> nameToColor;
    private final int[][] colorPairs;
    private final Driver driver;
    private int colorPairId;

    public ColorManager(final Driver driver, final JsonObject colorDefinitions) {
        this.driver = driver;
        nameToColor = new HashMap<>();
        colorPairs = new int[MAXIMUM_NUMBER_OF_COLORS][MAXIMUM_NUMBER_OF_COLORS];
        initialiseColorPairMatrix();
        registerDefaultColorPair();
        initialise(colorDefinitions);
    }

    private void initialiseColorPairMatrix() {
        for (int[] row : colorPairs) {
            Arrays.fill(row, -1);
        }
    }

    private void registerDefaultColorPair() {
        colorPairs[0][0] = 0;
        colorPairId = 1;
    }

    private void initialise(final JsonObject colorDefinitions) {
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

    public ColorName getColor(final String colorName) {
        if (nameToColor.containsKey(colorName)) {
            return nameToColor.get(colorName);
        }
        return nameToColor.get(colorName.toUpperCase().replace('-', '_'));
    }

    public int getPairId(final ColorName foreground, final ColorName background) {
        int fgId = foreground.getCode() + 1;
        int bgId = background.getCode() + 1;
        int pairId = colorPairs[fgId][bgId];
        if (pairId == -1) {
            registerPair(fgId, bgId);
        }
        return colorPairs[fgId][bgId];
    }

    private void registerPair(int foreground, int background) {
        driver.initColorPair(colorPairId, foreground - 1, background - 1);
        colorPairs[foreground][background] = colorPairId++;
    }
}
