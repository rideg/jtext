package org.jtext.ui.graphics;

import org.jtext.Component;
import org.jtext.curses.Driver;
import org.jtext.event.EventBus;
import org.jtext.event.Topic;
import org.jtext.keyboard.KeyEvent;
import org.jtext.keyboard.KeyboardHandler;
import org.jtext.ui.event.*;
import org.jtext.ui.layout.Layouts;

public class Scene extends Container implements Component {

    public static final Topic<RepaintEvent> REPAINT_EVENT_TOPIC = new Topic<>();
    public static final Topic<FocusMovedEvent> FOCUS_MOVED_EVENT_TOPIC = new Topic<>();

    private final Driver driver;
    private final EventBus eventBus;
    private Widget activeWidget;

    public Scene(final Driver driver, EventBus eventBus) {
        super(Layouts.vertical());
        this.driver = driver;
        this.eventBus = eventBus;
        eventBus.registerTopic(REPAINT_EVENT_TOPIC);
        eventBus.registerTopic(FOCUS_MOVED_EVENT_TOPIC);
    }

    public synchronized void onRepaint(final RepaintEvent repaintEvent) {
        driver.clearScreen();
        driver.clearStyle();
        final Rectangle area = calculateArea();
        setArea(area);
        draw(new Graphics(area, driver));
        driver.refresh();
    }

    private Rectangle calculateArea() {
        return Rectangle.of(0, 0, driver.getScreenWidth(), driver.getScreenHeight());
    }

    public void onKeyBoardEvent(final KeyEvent event) {
        if (activeWidget != null) {
            activeWidget.onEvent(new KeyPressedEvent(event.key));
        }
    }

    public void onFocusMoved(final FocusMovedEvent event) {
        if (activeWidget != null) {
            activeWidget.onEvent(LostFocusEvent.LOST_FOCUS_EVENT);
        }
        activeWidget = event.current;
        activeWidget.onEvent(GainFocusEvent.GAIN_FOCUS_EVENT);
    }


    @Override
    public void start() {
        eventBus.subscribe(KeyboardHandler.KEY_EVENT_TOPIC, this::onKeyBoardEvent);
        eventBus.subscribe(FOCUS_MOVED_EVENT_TOPIC, this::onFocusMoved);
        eventBus.subscribe(REPAINT_EVENT_TOPIC, this::onRepaint);

        onRepaint(RepaintEvent.REPAINT_EVENT);
    }

    @Override
    public void stop() {
        // todo unsubscribe
    }
}
