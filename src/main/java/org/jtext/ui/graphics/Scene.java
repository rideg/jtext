package org.jtext.ui.graphics;

import org.jtext.Component;
import org.jtext.curses.Driver;
import org.jtext.event.EventBus;
import org.jtext.event.Topic;
import org.jtext.keyboard.KeyEvent;
import org.jtext.keyboard.KeyboardHandler;
import org.jtext.ui.event.FocusMovedEvent;
import org.jtext.ui.event.GainFocusEvent;
import org.jtext.ui.event.KeyPressedEvent;
import org.jtext.ui.event.LostFocusEvent;
import org.jtext.ui.event.RepaintEvent;
import org.jtext.ui.layout.Layouts;
import org.slf4j.Logger;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class Scene extends Container implements Component {

    public static final Topic<RepaintEvent> REPAINT_EVENT_TOPIC = new Topic<>();
    public static final Topic<FocusMovedEvent> FOCUS_MOVED_EVENT_TOPIC = new Topic<>();
    private static final Logger LOGGER = getLogger(Scene.class);
    private final Driver driver;
    private final EventBus eventBus;
    private final ExecutorService executorService;
    private final BlockingDeque<Runnable> taskQueue = new LinkedBlockingDeque<>();
    private Widget activeWidget;
    private volatile boolean isRunning = true;

    public Scene(final Driver driver, EventBus eventBus, final ExecutorService executorService) {
        super(Layouts.vertical());
        this.driver = driver;
        this.eventBus = eventBus;
        this.executorService = executorService;
        eventBus.registerTopic(REPAINT_EVENT_TOPIC);
        eventBus.registerTopic(FOCUS_MOVED_EVENT_TOPIC);
    }

    public void onRepaint(final RepaintEvent repaintEvent) {
        enqueueTask(() -> {
            driver.clearScreen();
            driver.clearStyle();
            final Rectangle area = calculateArea();
            setArea(area);
            draw(new Graphics(area, driver));
            driver.refresh();
        });
    }

    public void enqueueTask(final Runnable task) {
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            LOGGER.warn("Interrupted while enqueuing task!", e);
        }
    }

    private Rectangle calculateArea() {
        return Rectangle.of(0, 0, driver.getScreenWidth(), driver.getScreenHeight());
    }

    public void onKeyBoardEvent(final KeyEvent event) {
        enqueueTask(() -> {
            if (activeWidget != null) {
                activeWidget.onEvent(new KeyPressedEvent(event.key));
            }
        });
    }

    public void onFocusMoved(final FocusMovedEvent event) {
        enqueueTask(() -> {
            if (activeWidget != event.current) {
                if (activeWidget != null) {
                    activeWidget.onEvent(new LostFocusEvent());
                }
                activeWidget = event.current;
                activeWidget.onEvent(new GainFocusEvent());
            }
        });

    }

    @SuppressWarnings("checkstyle:illegalcatch")
    private void mainLoop() {
        while (isRunning) {
            try {
                taskQueue.take().run();
            } catch (final InterruptedException e) {
                isRunning = false;
            } catch (final Exception e) {
                LOGGER.warn("Interrupted while enqueuing task!", e);
            }
        }
    }

    @Override
    public void start() {
        executorService.submit(this::mainLoop);


        eventBus.subscribe(KeyboardHandler.KEY_EVENT_TOPIC, this::onKeyBoardEvent);
        eventBus.subscribe(FOCUS_MOVED_EVENT_TOPIC, this::onFocusMoved);
        eventBus.subscribe(REPAINT_EVENT_TOPIC, this::onRepaint);

        onRepaint(RepaintEvent.REPAINT_EVENT);
    }

    @Override
    public void stop() {
        // todo unsubscribe from topics

        executorService.shutdown();
        try {
            executorService.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOGGER.warn("Interrupted while waiting for executor service to stop", e);
        }
    }
}
