package org.jtext.curses;

public interface Driver {

    void init();

    void initColor(int number, int red, int green, int blue);

    void initColorPair(int pairId, int foreground, int background);

    int getScreenWidth();

    int getScreenHeight();

    void setColor(int colorPairId);

    int getForegroundColor();

    int getBackgroundColor();

    void onAttributes(CharacterAttribute[] attributes);

    void onAttribute(CharacterAttribute attribute);

    void offAttribute(CharacterAttribute attribute);

    void drawHorizontalLineAt(int x, int y, char character, int length);

    void drawVerticalLineAt(int x, int y, char character, int length);

    void printStringAt(int x, int y, String string);

    void putCharAt(int x, int y, char character);

    void changeAttributeAt(int x, int y, int length, int colorPairId, CharacterAttribute[] attributes);

    void clearScreen();

    void bell();

    void shutdown();

    ReadKey getCh();

    void refresh();

    void clearStyle();

    int getCursorX();

    int getCursorY();
}
