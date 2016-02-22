package org.jtext.curses;

public class CursesDriver implements Driver {

    @Override
    public native void init();

    @Override
    public native void initColor(int number, short red, short green, short blue);

    @Override
    public native void initColorPair(int colorId, int foreground, int background);

    @Override
    public native int getScreenWidth();

    @Override
    public native int getScreenHeight();

    @Override
    public native void setColor(final CharacterColor foreground, final CharacterColor background);

    @Override
    public native void setBackgroundColor(final CharacterColor color);

    @Override
    public native void setForegroundColor(final CharacterColor color);

    @Override
    public native void onAttributes(final CharacterAttribute[] attributes);

    @Override
    public native void onAttribute(final CharacterAttribute attribute);

    @Override
    public native void offAttribute(final CharacterAttribute attribute);

    @Override
    public native void drawHorizontalLineAt(final int x, final int y, final char character, final int length);

    @Override
    public native void drawVerticalLineAt(final int x, final int y, final char character, final int length);

    @Override
    public native void printStringAt(final int x, final int y, final String string);

    @Override
    public native void putCharAt(final int x, final int y, final char character);

    @Override
    public native void changeAttributeAt(final int x, final int y, final int length,
                                         final CharacterColor foregroundColor, final CharacterColor backgroundColor,
                                         final CharacterAttribute[] attributes);

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
