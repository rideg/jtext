package org.jtext;

import org.jtext.curses.CursesDriver;
import org.jtext.curses.LibraryLoader;
import org.jtext.event.EventBus;
import org.jtext.keyboard.KeyboardHandler;
import org.jtext.ui.graphics.Scene;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        LibraryLoader.load();
        CursesDriver driver = new CursesDriver();
        driver.init();
        final EventBus eventBus = new EventBus();
        final KeyboardHandler keyboardHandler = new KeyboardHandler(driver, newSingleThreadExecutor(), eventBus);
        final Scene scene = new Scene(driver, eventBus);

    }

}
