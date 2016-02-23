package org.jtext.curses;

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


    public int cursesRed() {
        return red * 4;
    }

    public int cursesGreen() {
        return green * 4;
    }

    public int cursesBlue() {
        return blue * 4;
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
}
