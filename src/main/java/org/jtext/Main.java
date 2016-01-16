package org.jtext;

import org.jtext.system.*;
import sun.misc.Signal;

import java.util.concurrent.atomic.AtomicReference;

public class Main {

    public static void main(String[] args) {
        LibraryLoader.load();
        CursesImpl curses = new CursesImpl();
        curses.init();

        final AtomicReference<Point> point = new AtomicReference<>(new Point(0, 0));
        final Descriptor green = new Descriptor(CharacterColor.GREEN, CharacterColor.BLACK,
                                                CharacterAttribute.UNDERLINE,
                                                CharacterAttribute.BOLD);

        Signal.handle(new Signal("WINCH"), s -> {
            int screenHeight = curses.getScreenHeight();
            int screenWidth = curses.getScreenWidth();
            curses.printString("H:" + screenHeight + " W:" + screenWidth, point.get(), green);
            point.set(point.get().nextRow());
        });

        while (true) {
            ReadKey read = curses.getCh();
            if (read.key() == ControlKey.ESC) {
                break;
            }
        }

        curses.shutdown();
    }

}
