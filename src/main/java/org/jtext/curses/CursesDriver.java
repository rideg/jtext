package org.jtext.curses;

public class CursesDriver implements Driver {

    @Override
    public native void init();

    @Override
    public native void initColor(int number, int red, int green, int blue);

    @Override
    public native void initColorPair(int colorId, int foreground, int background);

    @Override
    public native int getScreenWidth();

    @Override
    public native int getScreenHeight();

    @Override
    public native void setColor(int colorPairId);

    @Override
    public native int getForegroundColor();

    @Override
    public native int getBackgroundColor();

    @Override
    public native void onAttributes(CharacterAttribute[] attributes);

    @Override
    public native void onAttribute(CharacterAttribute attribute);

    @Override
    public native void offAttribute(CharacterAttribute attribute);

    @Override
    public native void drawHorizontalLineAt(int x, int y, char character, int length);

    @Override
    public native void drawVerticalLineAt(int x, int y, char character, int length);

    @Override
    public native void printStringAt(int x, int y, String string);

    @Override
    public native void putCharAt(int x, int y, char character);

    @Override
    public native void changeAttributeAt(int x, int y, int length, int colorPairId, CharacterAttribute[] attributes);

    @Override
    public native void bell();

    @Override
    public native void shutdown();

    @Override
    public native ReadKey getCh();

    @Override
    public native void refresh();

    @Override
    public native void clearScreen();


    @Override
    public native void clearStyle();

    @Override
    public native int getCursorX();

    @Override
    public native int getCursorY();

}
