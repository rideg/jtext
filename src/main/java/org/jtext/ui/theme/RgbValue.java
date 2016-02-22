package org.jtext.ui.theme;

public final class RgbValue {

    private final int red;
    private final int green;
    private final int blue;

    RgbValue(final int red, final int green, final int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public static RgbValue of(final int red, final short green, final short blue) {
        if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
            throw new IllegalArgumentException("Each intensity value must be in range of [0-255]");
        }
        return new RgbValue(red, green, blue);
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    @Override
    public String toString() {
        return "RgbValue{" +
               "red=" + red +
               ", green=" + green +
               ", blue=" + blue +
               '}';
    }
}
