package org.jtext;

import org.jtext.curses.*;

public class Main {

    private static final String NAME = "jText 0.0.1";
    private static final String MAIN_MENU = "(M) - Ctrl-p";

    public static void main(String[] args) throws InterruptedException {
        LibraryLoader.load();
        CursesImpl curses = new CursesImpl();
        curses.init();

        while (true) {
            ReadKey read = curses.getCh();
            if (read.key() == ControlKey.ERR) {
                Thread.sleep(1);
                continue;
            }
            printHeader(curses, read);
            if (read.getValue() == 'q') {
                break;
            }
        }
        curses.shutdown();
    }

    private static void printHeader(final Curses curses, final ReadKey read) {
        curses.clearScreen();
        curses.moveCursor(new Point(0, 0));
        curses.clearStyle();
        curses.setColor(CharacterColor.CYAN, CharacterColor.BLACK);
        curses.onAttributes(new CharacterAttribute[] { CharacterAttribute.DIM });
        curses.drawVerticalLineAt(new Point(0, 2), '|', curses.getScreenHeight() - 2);

        curses.moveCursor(new Point(0, 0));
        curses.clearStyle();
        curses.setColor(CharacterColor.GREEN, CharacterColor.BLACK);
        curses.onAttributes(new CharacterAttribute[] { CharacterAttribute.BOLD });
        curses.printString("(✺) Got code: " + read.getValue() +
                           " 0x" + Integer.toHexString(read.getValue()) +
                           " 0" + Integer.toOctalString(read.getValue()) +
                           " (" + read.key().name() + ")");

        curses.moveCursor(new Point(0, 1));
        curses.clearStyle();
        curses.setColor(CharacterColor.MAGENTA, CharacterColor.BLACK);
        curses.setForegroundColor(CharacterColor.RED);
        curses.printStringAt(new Point(5, 5), "W: " + curses.getScreenWidth() + " H: " + curses.getScreenHeight());

        curses.setBackgroundColor(CharacterColor.BLUE);
        curses.putCharAt(new Point(10, 15), 'J');
        curses.refresh();
    }

}
