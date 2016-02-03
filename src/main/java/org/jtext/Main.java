package org.jtext;

import org.jtext.curses.*;
import org.jtext.ui.attribute.Border;
import org.jtext.ui.graphics.Graphics;
import org.jtext.ui.graphics.Point;
import org.jtext.ui.graphics.Rectangle;

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

//        curses.setBackground(windowId,
//                CellDescriptor.builder()
//                        .fg(CharacterColor.MAGENTA)
//                        .bg(CharacterColor.BLUE)
//                        .ch(' ')
//                        .attr(CharacterAttribute.BOLD)
//                        .create());

//        curses.clearScreen();

//        curses.printStringAt(windowId, 1,1, "Hello world!");


        final CellDescriptor prototype = CellDescriptor.builder()
                .bg(CharacterColor.RED)
                .fg(CharacterColor.WHITE)
                .attr(CharacterAttribute.BOLD)
                .create();
//
        curses.drawBox(windowId,
                prototype.ch(Border.SINGLE.topLeft),
                prototype.ch(Border.SINGLE.horizontal),
                prototype.ch(Border.SINGLE.topRight),
                prototype.ch(Border.SINGLE.vertical),
                prototype.ch(Border.SINGLE.bottomRight),
                prototype.ch(Border.SINGLE.horizontal),
                prototype.ch(Border.SINGLE.bottomLeft),
                prototype.ch(Border.SINGLE.vertical));

//        curses.changeBackground(windowId, new CellDescriptor(
//                '.', CharacterColor.BLUE, CharacterColor.MAGENTA,
//                new HashSet<>(Arrays.asList(CharacterAttribute.BOLD))
//        ));

//        curses.refresh(windowId);

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
            curses.refresh(windowId);
        }


//        TimeUnit.SECONDS.sleep(5);
        curses.deleteWindow(windowId);
        curses.shutdown();
//        curses.setColor(CharacterColor.CYAN, CharacterColor.BLACK);

//        drawRectangle(curses, new ReadKey(ControlKey.NULL, 0));
//        while (true) {
//            ReadKey read = curses.getCh();
//            if (read.key() == ControlKey.ERR) {
//                Thread.sleep(1);
//                continue;
//            }
//            drawRectangle(curses, read);
//            if (read.getValue() == 'q') {
//                break;
//            }
//        }
//        curses.shutdown();
    }

    private static void drawRectangle(final Driver driver, final ReadKey read) {
        int screenWidth = driver.getScreenWidth();
        int screenHeight = driver.getScreenHeight();

        if (read.key() == ControlKey.UP) {
            rectStart = rectStart.y > 0 ? rectStart.decY() : rectStart;
        }

        if (read.key() == ControlKey.DOWN) {
            rectStart = rectStart.y < screenHeight - 3 ? rectStart.incY() : rectStart;
        }

        if (read.key() == ControlKey.RIGHT) {
            rectStart = rectStart.x < screenWidth - text.length() + 4 ? rectStart.incX() : rectStart;
        }

        if (read.key() == ControlKey.LEFT) {
            rectStart = rectStart.x > 0 ? rectStart.decX() : rectStart;
        }

        if (read.key() == ControlKey.NORMAL || read.key() == ControlKey.SPACE) {
            text.append(new String(Character.toChars(read.getValue())));
        }

        if (read.key() == ControlKey.BACKSPACE && text.length() > 0) {
            text.setLength(text.length() - 1);
        }

        driver.clearScreen();
//        driver.setForegroundColor(CharacterColor.GREEN);
//        driver.printStringAt(0, 0, "Arrived code: " + read.key().name() + " : " + new String(Character.toChars(read.getValue())));


        Graphics graphics = new Graphics(Rectangle.of(rectStart, screenWidth, screenHeight), driver);
        graphics.startDraw();
        graphics.printString(Point.at(2, 1), text.toString());
        graphics.setForegroundColor(CharacterColor.CYAN);
        graphics.drawRectangle(Rectangle.of(0, 0, text.length() + 4, 3), Border.SINGLE);
        graphics.setForegroundColor(CharacterColor.RED);
//        driver.refresh();
    }

}
