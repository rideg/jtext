package org.jtext.system;

public class CursesImpl implements Curses {

    @Override
    public native void init();

    @Override
    public native int getScreenWidth();

    @Override
    public native int getScreenHeight();

    @Override
    public CursesWindow createWindow(final Rectangle area) {
        return null;
    }

    @Override
    public void deleteWindow(final CursesWindow window) {

    }

    @Override
    public void clearWindow(final CursesWindow window) {

    }

    @Override
    public void drawHorizontalLine(final CursesWindow window, final Point point, final int length, final Descriptor descriptor) {

    }

    @Override
    public void drawVerticalLine(final CursesWindow window, final Point point, final int length, final Descriptor descriptor) {

    }

    @Override
    public void bell() {

    }

    @Override
    public native void shutdown();

    @Override
    public native ReadKey getCh();

    @Override
    public native void printString(final String string, final Point start, final Descriptor descriptor);

    @Override
    public void refresh() {

    }

    @Override
    public void setCursorAttributes(final CursorAttribute attribute) {

    }

    @Override
    public native void clearScreen();

}
