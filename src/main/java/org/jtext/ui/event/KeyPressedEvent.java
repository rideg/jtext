package org.jtext.ui.event;

import org.jtext.curses.ReadKey;

public class KeyPressedEvent extends UIEvent {

    public final ReadKey key;

    public KeyPressedEvent(final ReadKey key) {
        this.key = key;}

}
