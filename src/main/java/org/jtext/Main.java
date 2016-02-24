package org.jtext;

import org.jtext.curses.ControlKey;
import org.jtext.curses.CursesDriver;
import org.jtext.curses.Driver;
import org.jtext.curses.LibraryLoader;
import org.jtext.curses.ReadKey;
import org.jtext.event.EventBus;
import org.jtext.keyboard.KeyboardHandler;
import org.jtext.ui.attribute.HorizontalAlign;
import org.jtext.ui.attribute.VerticalAlign;
import org.jtext.ui.event.FocusMovedEvent;
import org.jtext.ui.event.KeyPressedEvent;
import org.jtext.ui.graphics.Container;
import org.jtext.ui.graphics.Scene;
import org.jtext.ui.layout.Layouts;
import org.jtext.ui.theme.ThemeImpl;
import org.jtext.ui.util.KeyEventProcessor;
import org.jtext.ui.widget.TextField;

import java.lang.reflect.Proxy;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static org.jtext.keyboard.KeyboardHandler.KEY_EVENT_TOPIC;

public final class Main {


    private Main() {
    }

    public static void main(String[] args) throws InterruptedException {
        LibraryLoader.load();
        final Driver driver = getDriver();
        driver.init();
        final EventBus eventBus = new EventBus();
        final KeyboardHandler keyboardHandler = new KeyboardHandler(driver, newSingleThreadExecutor(), eventBus);
        final Scene scene = new Scene(driver, eventBus, newSingleThreadExecutor(), new ThemeImpl(driver));
        try {
            final Container mainContainer =
                    new Container(Layouts.vertical(HorizontalAlign.CENTER, VerticalAlign.CENTER));

            final TextField textField = new TextField(15);
            mainContainer.add(textField);


            scene.add(mainContainer);

            scene.start();
            keyboardHandler.start();

            final CountDownLatch latch = new CountDownLatch(1);


            final KeyEventProcessor processor = new KeyEventProcessor(true);

            processor.register(ControlKey.ESCAPE, latch::countDown);

            processor.register(ControlKey.DATA_LINK_ESCAPE, e -> {
                if (textField.isVisible()) {
                    textField.hide();
                    scene.onFocusMoved(new FocusMovedEvent(new Container(Layouts.horizontal())));
                } else {
                    textField.show();
                    scene.onFocusMoved(new FocusMovedEvent(textField));
                }
            });

            processor.register(ControlKey.NULL, e -> {
                if (textField.isVisible()) {
                    if (textField.isFocused()) {
                        scene.onFocusMoved(new FocusMovedEvent(new Container(Layouts.horizontal())));
                    } else {
                        scene.onFocusMoved(new FocusMovedEvent(textField));
                    }
                }
            });

            eventBus.subscribe(KEY_EVENT_TOPIC, event -> processor.handle(new KeyPressedEvent(event.key)));
            latch.await();

        } finally {
            keyboardHandler.stop();
            scene.stop();
            driver.shutdown();
        }
    }

    private static Driver getDriver() {
        if (Boolean.parseBoolean(System.getProperty("test.mode", "false"))) {
            final AtomicInteger closed = new AtomicInteger();
            return (Driver) Proxy.newProxyInstance(Main.class.getClassLoader(), new Class[]{Driver.class},
                                                   (proxy, method, args) -> {
                                                       if (method.getName().equals("getScreenHeight")) {
                                                           return 25;
                                                       }
                                                       if (method.getName().equals("getScreenWidth")) {
                                                           return 80;
                                                       }
                                                       if (method.getName().equals("getCh")) {
                                                           if (closed.get() > 2) {
                                                               return new ReadKey(ControlKey.ERR, 0);
                                                           }
                                                           closed.incrementAndGet();
                                                           return new ReadKey(ControlKey.NORMAL, 'a');
                                                       }
                                                       if (method.getName().equals("getBackgroundColor")) {
                                                           return 0;
                                                       }
                                                       if (method.getName().equals("getForegroundColor")) {
                                                           return 0;
                                                       }
                                                       return null;
                                                   });
        }
        return new CursesDriver();
    }

}
