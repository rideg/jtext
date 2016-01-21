package org.jtext;

import org.jtext.system.*;

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
        Descriptor d1 = new Descriptor(
                CharacterColor.WHITE,
                CharacterColor.BLACK,
                CharacterAttribute.UNDERLINE);

        Descriptor d2 = new Descriptor(CharacterColor.MAGENTA, CharacterColor.BLACK, CharacterAttribute.NORMAL);

        int width = curses.getScreenWidth() - NAME.length() - MAIN_MENU.length();


        final StringBuilder builder = new StringBuilder(MAIN_MENU);
        while (width > 0) {
            builder.append(' ');
            width--;
        }
        builder.append(NAME);


        curses.printString(builder.toString(), new Point(0, 0), d1);
        curses.printString("Got keykode: O" +
                Integer.toOctalString(read.getValue()) + " D" +
                read.getValue() + " (" +
                read.key().name() + ")", new Point(0, 1), d2);

//        curses.printString("W: " + curses.getScreenWidth() + " H: " + curses.getScreenHeight(), new Point(0, 1), d2);
    }

}
