package org.jtext;

import org.jtext.curses.*;
import org.jtext.ui.attribute.Border;
import org.jtext.ui.graphics.Graphics;
import org.jtext.ui.graphics.Point;
import org.jtext.ui.graphics.Rectangle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

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




        int windowId = curses.createWindow((screenWidth - width) /2,
                                           (screenHeight - height) /2,
                                           width, height);

//        curses.clearScreen();
        curses.setForegroundColor(windowId, CharacterColor.BLUE);
        curses.setBackgroundColor(windowId, CharacterColor.RED);
        curses.drawBox(windowId,
                       Border.SINGLE.topLeft,
                       Border.SINGLE.horizontal,
                       Border.SINGLE.topRight,
                       Border.SINGLE.vertical,
                       Border.SINGLE.bottomRight,
                       Border.SINGLE.horizontal,
                       Border.SINGLE.bottomLeft,
                       Border.SINGLE.vertical);

//        curses.changeBackground(windowId, new CellDescriptor(
//                '.', CharacterColor.BLUE, CharacterColor.MAGENTA,
//                new HashSet<>(Arrays.asList(CharacterAttribute.BOLD))
//        ));

        curses.refresh(windowId);

        while(true) {
            ReadKey ch = curses.getCh();
            if(ch.getValue() == 'q') {
                break;
            }
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
