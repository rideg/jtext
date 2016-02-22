package org.jtext;

import org.jtext.curses.CellDescriptor;
import org.jtext.curses.CharacterColor;
import org.jtext.curses.ControlKey;
import org.jtext.curses.CursesDriver;
import org.jtext.curses.Driver;
import org.jtext.curses.LibraryLoader;
import org.jtext.curses.ReadKey;
import org.jtext.event.EventBus;
import org.jtext.keyboard.KeyboardHandler;
import org.jtext.ui.attribute.Border;
import org.jtext.ui.attribute.HorizontalAlign;
import org.jtext.ui.attribute.Padding;
import org.jtext.ui.attribute.VerticalAlign;
import org.jtext.ui.event.FocusMovedEvent;
import org.jtext.ui.event.KeyPressedEvent;
import org.jtext.ui.event.RepaintEvent;
import org.jtext.ui.graphics.Container;
import org.jtext.ui.graphics.Scene;
import org.jtext.ui.layout.Layouts;
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
        final EventBus eventBus = new EventBus();
        final KeyboardHandler keyboardHandler = new KeyboardHandler(driver, newSingleThreadExecutor(), eventBus);
        final Scene scene = new Scene(driver, eventBus, newSingleThreadExecutor());
        try {
            driver.init();

            final Container mainContainer =
                    new Container(Layouts.vertical(HorizontalAlign.CENTER, VerticalAlign.CENTER));


            TextField textField =
                    new TextField(15, Border.single(), Padding.horizontal(1), CharacterColor.BLUE, CharacterColor.WHITE,
                                  CharacterColor.CYAN, CharacterColor.BLACK,
                                  CellDescriptor.of(CharacterColor.BLACK, CharacterColor.WHITE),
                                  CellDescriptor.of(CharacterColor.BLACK, CharacterColor.RED));

            textField.hide();
            mainContainer.add(textField);


            //            final Panel top =
            //                    new Panel(Layouts.horizontal(HorizontalAlign.RIGHT), Occupation.fill(), Occupation
            // .fixed(1),
            //                              Border.no(), Padding.horizontal(1));
            //
            //            CellDescriptor descriptor = CellDescriptor.empty();
            //            top.add(new Label(descriptor, "JTextIDE - 0.0.3"));
            //
            //            final Container center = new Container(Layouts.horizontal(VerticalAlign.TOP));
            //
            //            final Panel left =
            //                    new Panel(Layouts.vertical(HorizontalAlign.LEFT), Occupation.percent(30),
            // Occupation.fill(),
            //                              new Border(Optional.empty(), Optional.empty(), Border.single().right,
            //                                         Border.single().right, Border.single().right, Optional.empty(),
            //                                         Optional.empty(), Optional.empty())
            //                                      .changeCell(CellDescriptor.of(CharacterColor.BLACK,
            // CharacterColor.WHITE)),
            //                              Padding.full(1), CharacterColor.BLUE);
            //
            //            final Label labelLeft = new Label(CellDescriptor.of(' ', CharacterColor.BLUE,
            // CharacterColor.WHITE,
            //                                                                CharacterAttribute.BOLD), "Left Panel!");
            //
            //            left.add(labelLeft);
            //
            //            final Panel right =
            //                    new Panel(Layouts.vertical(), Occupation.fill(), Occupation.fill(), Border.no(),
            // Padding.full(1),
            //                              CharacterColor.CYAN);
            //
            //            final Label labelRight = new Label(CellDescriptor.of(' ', CharacterColor.CYAN,
            // CharacterColor.WHITE,
            //                                                                 CharacterAttribute.BOLD), "Right
            // Panel!");
            //
            //            right.add(labelRight);
            //
            //            center.add(left);
            //            center.add(right);
            //
            //            final Panel bottom =
            //                    new Panel(Layouts.horizontal(HorizontalAlign.CENTER), Occupation.fill(), Occupation
            // .fixed(1));
            //
            //
            //            bottom.add(new Label(descriptor, "Hahocska"));
            //
            //            mainContainer.add(top);
            //            mainContainer.add(center);
            //            mainContainer.add(bottom);


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

            eventBus.subscribe(KEY_EVENT_TOPIC, event -> {
                processor.handle(new KeyPressedEvent(event.key));
                if (event.key.controlKey != ControlKey.ESCAPE) {
                    eventBus.publish(Scene.REPAINT_EVENT_TOPIC, RepaintEvent.REPAINT_EVENT);
                }
            });

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
                                                       return null;
                                                   });
        }
        return new CursesDriver();
    }

}
