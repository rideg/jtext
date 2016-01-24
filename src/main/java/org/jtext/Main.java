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

        curses.clearScreen();
        curses.setForegroundColor(CharacterColor.GREEN);
        curses.printStringAt(0, 0, "Arrived code: " + read.key().name() + " : " + new String(Character.toChars(read.getValue())));


        Graphics graphics = new Graphics(Rectangle.of(rectStart, screenWidth, screenHeight), curses);
        graphics.startDraw();
        graphics.printString(Point.at(2, 1), text.toString());
        graphics.setForegroundColor(CharacterColor.CYAN);
        graphics.drawRectangle(Rectangle.of(0, 0, text.length() + 4, 3), Border.SINGLE);
        graphics.setForegroundColor(CharacterColor.RED);
        curses.refresh();
    }

}
