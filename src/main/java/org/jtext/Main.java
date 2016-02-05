package org.jtext;

import org.jtext.curses.*;
import org.jtext.ui.attribute.Border;
import org.jtext.ui.graphics.Point;

public class Main {

    private static Point rectStart = Point.at(5, 5);
    private static final StringBuilder text = new StringBuilder();

    public static void main(String[] args) throws InterruptedException {
        LibraryLoader.load();
        CursesDriver curses = new CursesDriver();
        curses.init();


        int screenWidth = curses.getScreenWidth();
        int screenHeight = curses.getScreenHeight();
        int width = 30;
        int height = 15;


        Point p = Point.at((screenWidth - width) / 2, (screenHeight - height) / 2);

        int windowId = curses.createWindow(p.x, p.y, width, height);


        while (true) {
            ReadKey ch = curses.getCh();
            if (ch.getValue() == 'q') {
                break;
            }
            if (ch.key() == ControlKey.ERR) {
                Thread.sleep(1);
                continue;
            }
            curses.printStringAt(windowId, 1, 1, ch.key().name());
            if (ch.key() == ControlKey.LEFT) {
                p = p.decX();
                curses.moveWindow(windowId, p.x, p.y);
            }
            if (ch.key() == ControlKey.RIGHT) {
                p = p.incX();
                curses.moveWindow(windowId, p.x, p.y);
            }

            if (ch.key() == ControlKey.SHIFT_RIGHT) {
                width++;
                curses.resizeWindow(windowId, width, height);
            }

            if (ch.key() == ControlKey.SHIFT_LEFT) {
                width--;
                curses.resizeWindow(windowId, width, height);
            }
            drawWindow(curses, windowId, ch.key().name());
        }

        curses.deleteWindow(windowId);
        curses.shutdown();

    }


    public static void drawWindow(final Driver driver, final int windowId, final String text) {
        driver.clearScreen();

        driver.clear(windowId);

        final CellDescriptor prototype = CellDescriptor.builder()
                .bg(CharacterColor.BLACK)
                .fg(CharacterColor.WHITE)
                .attr(CharacterAttribute.BOLD)
                .create();

        driver.setBackground(windowId, CellDescriptor.builder().bg(CharacterColor.BLUE).create());

        driver.drawBox(windowId,
                prototype.ch(Border.SINGLE.topLeft),
                prototype.ch(Border.SINGLE.horizontal),
                prototype.ch(Border.SINGLE.topRight),
                prototype.ch(Border.SINGLE.vertical),
                prototype.ch(Border.SINGLE.bottomRight),
                prototype.ch(Border.SINGLE.horizontal),
                prototype.ch(Border.SINGLE.bottomLeft),
                prototype.ch(Border.SINGLE.vertical));

        driver.setForegroundColor(windowId, CharacterColor.GREEN);
        driver.onAttribute(windowId, CharacterAttribute.UNDERLINE);
        driver.printStringAt(windowId, 2, 2, text);

        driver.refresh(Driver.SCREEN_WINDOW_ID);
        driver.refresh(windowId);
        driver.doUpdate();
    }

}
