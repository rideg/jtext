package org.jtext.keyboard;

import org.jtext.Component;
import org.jtext.curses.ControlKey;
import org.jtext.curses.Curses;
import org.jtext.curses.ReadKey;
import org.jtext.event.EventBus;
import org.jtext.event.Topic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class KeyboardHandler implements Component {

    public static final Topic<KeyEvent> TOPIC = new Topic<>();

    private final Curses curses;
    private final ExecutorService executorService;
    private final AtomicBoolean shouldRun = new AtomicBoolean(true);
    private final EventBus bus;

    public KeyboardHandler(final Curses curses, final ExecutorService executorService, final EventBus bus) {
        this.curses = curses;
        this.executorService = executorService;
        this.bus = bus;
    }


    @Override
    public void start() {

        bus.registerTopic(TOPIC);

        executorService.execute(() -> {
            while (shouldRun.get()) {
                ReadKey readKey = curses.getCh();
                if (readKey.key() != ControlKey.ERR) {
                    bus.publish(TOPIC, new KeyEvent(readKey.key(), readKey.getValue()));
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
