package org.jtext.ui.event;

import org.jtext.event.Event;

public final class RepaintEvent implements Event {

    public static final RepaintEvent INSTANCE = new RepaintEvent();

    private RepaintEvent() {

    }

}
