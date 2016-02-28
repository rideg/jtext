package org.jtext.curses;

import org.jtext.ui.util.Util;

public final class RgbValue {

    private final int red;
    private final int green;
    private final int blue;

    RgbValue(final int red, final int green, final int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public static RgbValue of(final int red, final int green, final int blue) {
        return new RgbValue(red, green, blue);
    }

    public static RgbValue fromClassic(final int red, final int green, final int blue) {
        return new RgbValue(Util.divHalfUp(red * 1000, 255),
                Util.divHalfUp(green * 1000, 255),
                Util.divHalfUp(blue * 1000, 255));
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
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final RgbValue rgbValue = (RgbValue) o;

        return red == rgbValue.red && green == rgbValue.green && blue == rgbValue.blue;

    }

    public static RgbValue parse(String value) {
        return RgbValue.of(Integer.valueOf(value.substring(1, 3), 16),
                Integer.valueOf(value.substring(3, 5), 16),
                Integer.valueOf(value.substring(5, 7), 16));
    }

    @Override
    public int hashCode() {
        int result = red;
        result = 31 * result + green;
        result = 31 * result + blue;
        return result;
    }

    @Override
    public String toString() {
        return "RgbValue{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                '}';
    }

    public String toHexString() {
        return String.format("0x%02x%02x%02x", red, green, blue);
    }

    public int intValue() {
        return (red << 16) | (green << 8) | blue;
    }

    public double length() {
        return Math.sqrt(red * red + green * green + blue * blue);
    }


}
