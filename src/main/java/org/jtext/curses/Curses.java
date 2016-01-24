package org.jtext.curses;

public interface Curses {

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

    void changeAttributeAt(int x, int y, CharacterAttribute[] attributes);

    void moveCursor(int x, int y);

    void drawVerticalLine(char character, int length);

    void drawHorizontalLine(char character, int length);

    void printString(String string);

    void putChar(char character);

    void changeAttribute(CharacterAttribute[] attributes);

    void clearScreen();

    void bell();

    void shutdown();

    ReadKey getCh();

    void refresh();

    void clearStyle();
}
