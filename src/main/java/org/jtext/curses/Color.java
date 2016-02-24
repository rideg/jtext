package org.jtext.curses;

public class Color {

    private final int colorId;
    private final RgbValue rgbValue;
    private final String name;

    public Color(final int colorId, final String name, final RgbValue rgbValue) {
        if (colorId < 0 || colorId > 255) {
            throw new IllegalArgumentException("Color id must be in range [0, 255]");
        }
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

    @Override
    public String toString() {
        return "Color{" +
               "colorId=" + colorId +
               ", name='" + name + '\'' +
               ", rgbValue=" + rgbValue +
               '}';
    }
}
