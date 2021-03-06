package org.jtext;

import org.jtext.curses.ColorName;
import org.jtext.curses.ControlKey;
import org.jtext.curses.CursesDriver;
import org.jtext.curses.Driver;
import org.jtext.curses.LibraryLoader;
import org.jtext.curses.ReadKey;
import org.jtext.event.EventBus;
import org.jtext.keyboard.KeyboardHandler;
import org.jtext.ui.attribute.Border;
import org.jtext.ui.attribute.Padding;
import org.jtext.ui.event.FocusMovedEvent;
import org.jtext.ui.event.KeyPressedEvent;
import org.jtext.ui.graphics.Container;
import org.jtext.ui.graphics.Occupation;
import org.jtext.ui.graphics.Scene;
import org.jtext.ui.graphics.Widget;
import org.jtext.ui.layout.Layouts;
import org.jtext.ui.model.TextModel;
import org.jtext.ui.theme.ThemeImpl;
import org.jtext.ui.util.KeyEventProcessor;
import org.jtext.ui.widget.Panel;
import org.jtext.ui.widget.TextField;

import java.lang.reflect.Proxy;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static org.jtext.keyboard.KeyboardHandler.KEY_EVENT_TOPIC;
import static org.jtext.ui.graphics.Scene.FOCUS_MOVED_EVENT_TOPIC;

public final class Main {


    private Main() {
    }

    public static void main(final String[] args) throws InterruptedException {
        LibraryLoader.load();
        final Driver driver = getDriver();
        driver.init();
        final EventBus eventBus = new EventBus();
        final KeyboardHandler keyboardHandler = new KeyboardHandler(driver, newSingleThreadExecutor(), eventBus);
        final ThemeImpl theme = new ThemeImpl(driver);
        final Scene scene = new Scene(driver, eventBus, newSingleThreadExecutor(), theme);
        try {

            final Container<Widget> mainContainer = new Panel(Layouts.vertical(),
                    Occupation.fill(),
                    Occupation.fill(),
                    Border.no(),
                    Padding.full(1),
                    ColorName.BLUE);


            final TextModel model = new TextModel();
            final TextField textField = new TextField(model, 25);
            final TextField textField1 = new TextField(model, 25);

            mainContainer.add(textField);
            mainContainer.add(textField1);
//            mainContainer.add(new Label(CellDescriptor.foreground(ColorName.WHITE), model));

//            final GridSelector gridSelector = new GridSelector(100, 100, Arrays.asList(
//                    new MenuElement("elso"),
//                    new MenuElement("masodik"),
//                    new MenuElement("harmadik"),
//                    new MenuElement("negyedik_elem"),
//                    new MenuElement("otodik"),
//                    new MenuElement("hatodik"),
//                    new MenuElement("hetedik"),
//                    new MenuElement("nyolcadik"),
//                    new MenuElement("kilencedik"),
//                    new MenuElement("tizedik"),
//                    new MenuElement("huszadik"),
//                    new MenuElement("szazezredik")
//            ));
//
//            gridSelector.subscribe(m -> {
//                String text = m.getModel().getChars();
//                model.setText(text);
//            });
//
//
//            mainContainer.add(gridSelector);

            scene.add(mainContainer);

            scene.start();
            keyboardHandler.start();

            final CountDownLatch latch = new CountDownLatch(1);


            final KeyEventProcessor processor = new KeyEventProcessor(true);

            processor.register(ControlKey.ESCAPE, latch::countDown);

//            gridSelector.select(0);


            processor.register(ControlKey.NULL, e -> {
                if (textField.isVisible()) {
                    if (textField.isFocused()) {
                        eventBus.publish(FOCUS_MOVED_EVENT_TOPIC, new FocusMovedEvent(textField1));
                    } else {
                        eventBus.publish(FOCUS_MOVED_EVENT_TOPIC, new FocusMovedEvent(textField));
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
