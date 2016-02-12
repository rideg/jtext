package org.jtext.ui.graphics;

import org.jtext.curses.Driver;
import org.jtext.keyboard.KeyEvent;
import org.jtext.ui.event.*;
import org.jtext.ui.layout.Layout;

public class Scene extends Container {

    private Widget activeWidget;
    private final Driver driver;

    public Scene(final Driver driver) {
        super(null);
        this.driver = driver;
    }

    public void onRepaint(final RepaintEvent repaintEvent) {
        final Rectangle area = Rectangle.of(0, 0, driver.getScreenWidth(), driver.getScreenHeight());
        setArea(area);
        draw(new Graphics(area, driver));
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
