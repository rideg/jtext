package org.jtext;

import org.jtext.curses.*;
import org.jtext.event.EventBus;
import org.jtext.keyboard.KeyboardHandler;
import org.jtext.ui.attribute.Border;
import org.jtext.ui.attribute.HorizontalAlign;
import org.jtext.ui.attribute.Padding;
import org.jtext.ui.attribute.VerticalAlign;
import org.jtext.ui.graphics.Container;
import org.jtext.ui.graphics.Occupation;
import org.jtext.ui.graphics.Scene;
import org.jtext.ui.layout.Layouts;
import org.jtext.ui.widget.Label;
import org.jtext.ui.widget.Panel;

import java.lang.reflect.Proxy;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static org.jtext.keyboard.KeyboardHandler.KEY_EVENT_TOPIC;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        LibraryLoader.load();
        final Driver driver = getDriver();
        final EventBus eventBus = new EventBus();
        final KeyboardHandler keyboardHandler = new KeyboardHandler(driver, newSingleThreadExecutor(), eventBus);
        try {
            driver.init();
            final Scene scene = new Scene(driver, eventBus);

            final Container mainContainer = new Container(Layouts.vertical(VerticalAlign.TOP));

            final Panel top = new Panel(Layouts.horizontal(HorizontalAlign.LEFT),
                    Occupation.fill(),
                    Occupation.fixed(1),
                    Border.no(),
                    Padding.horizontal(1));

            CellDescriptor descriptor = CellDescriptor.builder().fg(CharacterColor.GREEN).create();
            top.add(new Label(descriptor, "Hello"));

            final Container center = new Container(Layouts.horizontal(VerticalAlign.TOP));

            final Panel left = new Panel(Layouts.vertical(HorizontalAlign.LEFT),
                    Occupation.percent(30),
                    Occupation.fill(),
                    new Border(Optional.empty(),
                            Optional.empty(),
                            Border.single().right,
                            Border.single().right,
                            Border.single().right,
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty()),
                    Padding.full(1),
                    CharacterColor.BLUE);

            final Label labelLeft = new Label(CellDescriptor.builder()
                    .fg(CharacterColor.WHITE)
                    .bg(CharacterColor.BLUE)
                    .attr(CharacterAttribute.BOLD)
                    .create(),
                    "Left Panel!");

            left.add(labelLeft);

            final Panel right = new Panel(Layouts.vertical(HorizontalAlign.RIGHT),
                    Occupation.fill(),
                    Occupation.fill(),
                    Border.no(),
                    Padding.full(1),
                    CharacterColor.CYAN);

            final Label labelRight = new Label(CellDescriptor.builder()
                    .fg(CharacterColor.WHITE)
                    .bg(CharacterColor.CYAN)
                    .attr(CharacterAttribute.BOLD)
                    .create(),
                    "Right Panel!");

            right.add(labelRight);

            center.add(left);
            center.add(right);

            final Panel bottom = new Panel(Layouts.horizontal(HorizontalAlign.RIGHT),
                    Occupation.fill(),
                    Occupation.fixed(1));


            bottom.add(new Label(descriptor, "Hahocska"));

            mainContainer.add(top);
            mainContainer.add(center);
            mainContainer.add(bottom);

            scene.add(mainContainer);

            scene.start();
            keyboardHandler.start();

            final CountDownLatch latch = new CountDownLatch(1);
            eventBus.subscribe(KEY_EVENT_TOPIC, event -> {
                if (event.key.getValue() == 'q') {
                    latch.countDown();
                }
            });
            latch.await();

        } finally {
            keyboardHandler.stop();
            driver.shutdown();
        }
    }

    private static Driver getDriver() {
        if (Boolean.parseBoolean(System.getProperty("test.mode", "false"))) {

            return (Driver) Proxy.newProxyInstance(Main.class.getClassLoader(), new Class[]{Driver.class},
                    (proxy, method, args) -> {
                        if (method.getName().equals("getScreenHeight")) {
                            return 25;
                        }
                        if (method.getName().equals("getScreenWidth")) {
                            return 80;
                        }
                        if (method.getName().equals("getCh")) {
                            return new ReadKey(ControlKey.ERR, 0);
                        }
                        return null;
                    });
        }
        return new CursesDriver();
    }

}
