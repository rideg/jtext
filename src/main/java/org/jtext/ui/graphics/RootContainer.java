package org.jtext.ui.graphics;

import org.jtext.event.Event;
import org.jtext.event.EventBus;
import org.jtext.event.Topic;
import org.jtext.ui.layout.Layout;

public class RootContainer extends Container<Widget> {

    private final EventBus eventBus;

    public RootContainer(final Layout<Widget> layout, final EventBus eventBus) {
        super(layout);
        this.eventBus = eventBus;
    }

    @Override
    protected <T extends Event> void emit(final Topic<T> topic, final T event) {
        eventBus.publish(topic, event);
    }
}
