package org.jtext.curses;

public class CursesDriver implements Driver {

    @Override
    public native void init();

    @Override
    public native int getScreenWidth();

    @Override
    public native int getScreenHeight();

    @Override
    public native int createWindow(final int x, final int y, final int width, final int height);

    @Override
    public native void setColor(final int windowId, final CharacterColor foreground, final CharacterColor background);

    @Override
    public native void setBackgroundColor(final int windowId, final CharacterColor color);

    @Override
    public native void setForegroundColor(final int windowId, final CharacterColor color);

    @Override
    public native void onAttributes(final int windowId, final CharacterAttribute[] attributes);

    @Override
    public native void onAttribute(final int windowId, final CharacterAttribute attribute);

    @Override
    public native void offAttribute(final int windowId, final CharacterAttribute attribute);

    @Override
    public native void drawHorizontalLineAt(final int windowId, final int x, final int y, final char character, final int length);

    @Override
    public native void drawVerticalLineAt(final int windowId, final int x, final int y, final char character, final int length);

    @Override
    public native void printStringAt(final int windowId, final int x, final int y, final String string);

    @Override
    public native void putCharAt(final int windowId, final int x, final int y, final char character);

    @Override
    public native void changeAttributeAt(final int windowId,
                                         final int x,
                                         final int y,
                                         final int length,
                                         final CharacterColor foregroundColor,
                                         final CharacterColor backgroundColor,
                                         final CharacterAttribute[] attributes);

    @Override
    public native void moveCursor(final int windowId, final int x, final int y);

    @Override
    public native void drawVerticalLine(final int windowId, final char character, final int length);

    @Override
    public native void drawHorizontalLine(final int windowId, final char character, final int length);

    @Override
    public native void printString(final int windowId, final String string);

    @Override
    public native void putChar(final int windowId, final char character);

    @Override
    public native void changeAttribute(final int windowId, final CharacterAttribute[] attributes);

    @Override
    public native void bell();

    @Override
    public native void shutdown();

    @Override
    public native ReadKey getCh();

    @Override
    public native void refresh(final int windowId);

    @Override
    public native void clearScreen();

    @Override
    public native void clear(final int windowId);

    @Override
    public native void clearStyle(final int windowId);

    @Override
    public native int getCursorX(final int windowId);

    @Override
    public native int getCursorY(final int windowId);

    @Override
    public native void moveWindow(final int windowId, final int x, final int y);

    @Override
    public native void resizeWindow(final int windowId, final int width, final int height);

    @Override
    public native void deleteWindow(final int windowId);

    @Override
    public native void setBackground(final int windowId, final char character, final CharacterColor foregroundColor, final CharacterColor backgroundColor, final CharacterAttribute[] attributes);

    @Override
    public native void changeBackground(final int windowId, final char character, final CharacterColor foregroundColor,final CharacterColor backgroundColor, final CharacterAttribute[] attributes);

}
