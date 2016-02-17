package org.jtext.keyboard;

import org.jtext.Component;
import org.jtext.curses.ControlKey;
import org.jtext.curses.Driver;
import org.jtext.curses.ReadKey;
import org.jtext.event.EventBus;
import org.jtext.event.Topic;
import org.jtext.ui.event.RepaintEvent;
import org.jtext.ui.graphics.Scene;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class KeyboardHandler implements Component {

    public static final Topic<KeyEvent> KEY_EVENT_TOPIC = new Topic<>();

    private final Driver driver;
    private final ExecutorService executorService;
    private final AtomicBoolean shouldRun = new AtomicBoolean(true);
    private final EventBus bus;

    public KeyboardHandler(final Driver driver, final ExecutorService executorService, final EventBus bus) {
        this.driver = driver;
        this.executorService = executorService;
        this.bus = bus;
        bus.registerTopic(KEY_EVENT_TOPIC);
    }


    @Override
    public void start() {
        executorService.execute(() -> {
            while (shouldRun.get()) {
                ReadKey readKey = driver.getCh();
                if (readKey.key() == ControlKey.RESIZE) {
                    bus.publish(Scene.REPAINT_EVENT_TOPIC, RepaintEvent.REPAINT_EVENT);
                } else if (readKey.key() != ControlKey.ERR) {
                    bus.publish(KEY_EVENT_TOPIC, new KeyEvent(readKey));
                } else {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void stop() {
        shouldRun.set(false);
        executorService.shutdown();
    }
}
