package org.jtext.ui.theme;

public class Color {

    private final int colorId;
    private final RgbValue rgbValue;
    private final String name;

    public Color(final int colorId, final String name, final RgbValue rgbValue) {
        this.colorId = colorId;
        this.name = name;
        this.rgbValue = rgbValue;
    }

    public int getColorId() {
        return colorId;
    }

    public RgbValue getRgbValue() {
        return rgbValue;
    }

    public String getName() {
        return name;
    }
}
