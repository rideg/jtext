package org.jtext.curses;

public interface Driver {

    void init();

    int getScreenWidth();

    int getScreenHeight();

    void setColor(CharacterColor foreground, CharacterColor background);

    void setBackgroundColor(CharacterColor color);

    void setForegroundColor(CharacterColor color);

    void onAttributes(CharacterAttribute[] attributes);

    void onAttribute(CharacterAttribute attribute);

    void offAttribute(CharacterAttribute attribute);

    void drawHorizontalLineAt(int x, int y, char character, int length);

    void drawVerticalLineAt(int x, int y, char character, int length);

    void printStringAt(int x, int y, String string);

    void putCharAt(int x, int y, char character);

    void changeAttributeAt(int x,
                           int y,
                           int length,
                           CharacterColor foregroundColor,
                           CharacterColor backgroundColor,
                           CharacterAttribute[] attributes);

    void clearScreen();

    void bell();

    void shutdown();

    ReadKey getCh();

    void refresh();

    void clearStyle();

    int getCursorX();

    int getCursorY();

}
