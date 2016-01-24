package org.jtext.curses;

public class CursesImpl implements Curses {

    @Override
    public native void init();

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
    public native void changeAttributeAt(final int x, final int y, final CharacterAttribute[] attributes);

    @Override
    public native void moveCursor(final int x, final int y);

    @Override
    public native void drawVerticalLine(final char character, final int length);

    @Override
    public native void drawHorizontalLine(final char character, final int length);

    @Override
    public native void printString(final String string);

    @Override
    public native void putChar(final char character);

    @Override
    public native void changeAttribute(final CharacterAttribute[] attributes);

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

}
