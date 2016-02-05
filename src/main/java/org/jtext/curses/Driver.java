package org.jtext.curses;

public interface Driver {

    int SCREEN_WINDOW_ID = 0;

    void init();

    int getScreenWidth();

    int getScreenHeight();

    int createWindow(int x, int y, int width, int height);

    void setColor(int windowId, CharacterColor foreground, CharacterColor background);

    void setBackgroundColor(int windowId, CharacterColor color);

    void setForegroundColor(int windowId, CharacterColor color);

    void onAttributes(int windowId, CharacterAttribute[] attributes);

    void onAttribute(int windowId, CharacterAttribute attribute);

    void offAttribute(int windowId, CharacterAttribute attribute);

    void drawHorizontalLineAt(int windowId, int x, int y, char character, int length);

    void drawVerticalLineAt(int windowId, int x, int y, char character, int length);

    void printStringAt(int windowId, int x, int y, String string);

    void putCharAt(int windowId, int x, int y, char character);

    void changeAttributeAt(int windowId, int x, int y, int length, final CharacterColor foregroundColor,
                           final CharacterColor backgroundColor, CharacterAttribute[] attributes);

    void moveCursor(int windowId, int x, int y);

    void drawVerticalLine(int windowId, char character, int length);

    void drawHorizontalLine(int windowId, char character, int length);

    void printString(int windowId, String string);

    void putChar(int windowId, char character);

    void changeAttribute(int windowId, CharacterAttribute[] attributes);

    void clearScreen();

    void clear(int windowId);

    void bell();

    void shutdown();

    ReadKey getCh();

    void refresh(int windowId);

    void doUpdate();

    void clearStyle(int windowId);

    int getCursorX(int windowId);

    int getCursorY(int windowId);

    boolean moveWindow(int windowId, int x, int y);

    boolean resizeWindow(int windowId, int width, int height);

    void deleteWindow(int windowId);

    void setBackground(int windowId, CellDescriptor descriptor);

    void changeBackground(int windowId, CellDescriptor descriptor);

    void drawBox(int windowId,
                 CellDescriptor topLeft,
                 CellDescriptor top,
                 CellDescriptor topRight,
                 CellDescriptor right,
                 CellDescriptor bottomRight,
                 CellDescriptor bottom,
                 CellDescriptor bottomLeft,
                 CellDescriptor left);

    void drawBox(int windowId,
                 char topLeft,
                 char top,
                 char topRight,
                 char right,
                 char bottomRight,
                 char bottom,
                 char bottomLeft,
                 char left);
}
