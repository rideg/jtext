package org.jtext.curses;

import java.util.EnumSet;

public interface Curses {

    void init();

    int getScreenWidth();

    int getScreenHeight();

    void setColor(CharacterColor foreground, CharacterColor background);

    void setBackgroundColor(CharacterColor color);

    void setForegroundColor(CharacterColor color);

    void setAttributes(CharacterAttribute[] attributes);

    void setAttribute(CharacterAttribute attribute);

    void toggleAttribute(CharacterAttribute attribute);

    void drawHorizontalLineAt(Point point, char character, int length);

    void drawVerticalLineAt(Point point, char character, int length);

    void printStringAt(Point point, String string);

    void putCharAt(Point point, char character);

    void changeAttributeAt(Point point, CharacterAttribute[] attributes);

    void moveCursor(Point point);

    void drawVerticalLine(char character, int length);

    void drawHorizontalLine(char character, int length);

    void printString(String string);

    void printChar(char character);

    void changeAttribute(CharacterAttribute[] attributes);

    void clearScreen();

    void bell();

    void shutdown();

    ReadKey getCh();

    void refresh();

    void setCursorAttributes(CursorAttribute attribute);


    void clearStyle();
}
