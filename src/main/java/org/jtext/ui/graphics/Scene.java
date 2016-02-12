package org.jtext.ui.graphics;

import org.jtext.curses.Driver;
import org.jtext.keyboard.KeyEvent;
import org.jtext.ui.event.*;
import org.jtext.ui.layout.LayoutManager;

public class Scene extends Container {

    private static final Rectangle UNITY = Rectangle.of(0, 0, 1, 1);

    private Widget activeWidget;
    private final Driver driver;

    public Scene(final Driver driver) {
        super(new LayoutManager());
        this.driver = driver;
    }

    public void onRepaint(final RepaintEvent repaintEvent) {
        draw(new Graphics(UNITY.resize(driver.getScreenWidth(), driver.getScreenHeight()), driver));
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


}
