package org.jtext;

import org.jtext.system.*;

public class Main {

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
            printScreenSize(curses, read);
            if (read.key() == ControlKey.ESC) {
                break;
            }
        }
        curses.shutdown();
    }

    private static void printScreenSize(final Curses curses, final ReadKey read) {
        curses.clearScreen();
        Descriptor d1 = new Descriptor(CharacterColor.GREEN,
                                       CharacterColor.BLACK,
                                       CharacterAttribute.UNDERLINE,
                                       CharacterAttribute.BOLD);

        Descriptor d2 = new Descriptor(CharacterColor.MAGENTA, CharacterColor.BLACK, CharacterAttribute.NORMAL);
        curses.printString("Got keykode: " + read.getValue(), new Point(0, 0), d1);

        curses.printString("W: " + curses.getScreenWidth() + " H: " + curses.getScreenHeight(), new Point(0, 1), d2);
    }

}
