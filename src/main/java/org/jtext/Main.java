package org.jtext;

import org.jtext.curses.*;
import org.jtext.ui.attribute.Border;
import org.jtext.ui.graphics.Graphics;
import org.jtext.ui.graphics.Point;
import org.jtext.ui.graphics.Rectangle;

public class Main {

    private static final String NAME = "jText 0.0.1";
    private static final String MAIN_MENU = "(M) - Ctrl-p";
    private static Point rectStart = Point.of(5, 5);
    private static int height = 10;
    private static int width = 10;
    private static boolean dirty = true;

    public static void main(String[] args) throws InterruptedException {
        LibraryLoader.load();
        CursesImpl curses = new CursesImpl();
        curses.init();
        curses.setColor(CharacterColor.CYAN, CharacterColor.BLACK);

        drawRectangle(curses, new ReadKey(ControlKey.NULL, 0));
        while (true) {
            ReadKey read = curses.getCh();
            if (read.key() == ControlKey.ERR) {
                Thread.sleep(1);
                continue;
            }
            drawRectangle(curses, read);
            if (read.getValue() == 'q') {
                break;
            }
        }
        curses.shutdown();
    }

    private static void drawRectangle(final Curses curses, final ReadKey read) {
        int screenWidth = curses.getScreenWidth();
        int screenHeight = curses.getScreenHeight();

        if (read.key() == ControlKey.UP) {
            rectStart =  rectStart.y > 0? rectStart.decY() : rectStart;
        }

        if(read.key() == ControlKey.DOWN) {
            rectStart = rectStart.y < screenHeight - height ? rectStart.incY() : rectStart;
        }

        if (read.key() == ControlKey.RIGHT) {
            rectStart = rectStart.x < screenWidth - width ? rectStart.incX() : rectStart;
        }

        if (read.key() == ControlKey.LEFT) {
            rectStart = rectStart.x > 0 ? rectStart.decX() : rectStart;
        }

        if(read.key() == ControlKey.SHIFT_UP) {
            height++;
        }

        if(read.key() == ControlKey.SHIFT_DOWN) {
            height--;
        }

        if(read.key() == ControlKey.SHIFT_RIGHT) {
            width++;
        }

        if (read.key() == ControlKey.SHIFT_LEFT) {
            width--;
        }

        curses.clearScreen();
        Graphics graphics = new Graphics(Rectangle.of(rectStart, screenWidth, screenHeight), curses);
        graphics.startDraw();
        graphics.drawRectangle(width, height, Border.SINGLE);
        curses.refresh();
    }

}
