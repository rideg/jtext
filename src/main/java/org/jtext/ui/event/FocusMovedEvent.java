package org.jtext.ui.event;

import org.jtext.event.Event;
import org.jtext.ui.graphics.Widget;

@SuppressWarnings("checkstyle:visibilitymodifier")
public class FocusMovedEvent implements Event {

    public final Widget current;

    public FocusMovedEvent(final Widget current) {
        this.current = current;
    }


}
