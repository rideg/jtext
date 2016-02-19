package org.jtext.ui.event;

import org.jtext.curses.ReadKey;

public final class KeyPressedEvent extends UIEvent {

    private final ReadKey key;

    public KeyPressedEvent(final ReadKey key) {
        this.key = key;
    }

    public ReadKey getKey() {
        return key;
    }
}
