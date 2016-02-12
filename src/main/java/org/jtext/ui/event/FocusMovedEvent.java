package org.jtext.ui.event;

import org.jtext.ui.graphics.Widget;

public class FocusMovedEvent {

    public final Widget current;

    public FocusMovedEvent(final Widget current) {
        this.current = current;
    }


}
