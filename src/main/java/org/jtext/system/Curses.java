package org.jtext.system;

public interface Curses {

    void init();

    int getScreenWidth();

    int getScreenHeight();

    CursesWindow createWindow(Rectangle area);

    void deleteWindow(CursesWindow window);

    void clearWindow(CursesWindow window);

    void drawHorizontalLine(CursesWindow window, Point point,  int length, Descriptor descriptor);

    void drawVerticalLine(CursesWindow window, Point point, int length, Descriptor descriptor);

    void bell();

    void shutdown();

    ReadKey getCh();

    void printString(String string, Point start, Descriptor descriptor);

    void refresh();

    void setCursorAttributes(CursorAttribute attribute);

}
