package org.jtext.curses;

public enum CharacterColor {

    BLACK(0, 0, 0),
    RED(255, 0, 0),
    GREEN(0, 255, 0),
    YELLOW(255, 255, 0),
    BLUE(0, 0, 255),
    MAGENTA(255, 0, 255),
    CYAN(0, 255, 255),
    WHITE(255, 255, 255);

    private final byte red;
    private final byte green;
    private final byte blue;

    CharacterColor(int red, int green, int blue) {
        this.red = (byte) red;
        this.green = (byte) green;
        this.blue = (byte) blue;
    }

    public byte getRed() {
        return red;
    }

    public byte getGreen() {
        return green;
    }

    public byte getBlue() {
        return blue;
    }
}
