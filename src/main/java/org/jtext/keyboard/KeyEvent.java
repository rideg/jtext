package org.jtext.keyboard;

import org.jtext.curses.ReadKey;
import org.jtext.event.Event;

@SuppressWarnings("checkstyle:visibilitymodifier")
public class KeyEvent implements Event {

    public final ReadKey key;

    public KeyEvent(final ReadKey key) {
        this.key = key;
    }

}
