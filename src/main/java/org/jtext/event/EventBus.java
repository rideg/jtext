package org.jtext.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {

    private final Map<Topic<? extends Event>, List<EventHandler>> topics = new HashMap<>();

    public synchronized <T extends Event> void registerTopic(final Topic<T> topic) {
        topics.computeIfAbsent(topic, t -> new ArrayList<>());
    }

    @SuppressWarnings("unchecked")
    public synchronized <T extends Event> void publish(final Topic<T> topic, final T event) {
        List<EventHandler> handlers = topics.get(topic);

        if (handlers == null) {
            throw new IllegalArgumentException("The topic is not registered!");
        }
        for (EventHandler handler : handlers) {
            handler.handle(event);
        }
    }

    public synchronized <T extends Event> void publish(final Topic<T> topic) {
        publish(topic, null);
    }

    public synchronized <T extends Event> void subscribe(final Topic<T> topic, EventHandler<T> handler) {
        List<EventHandler> handlers = topics.get(topic);
        if (handlers == null) {
            throw new IllegalArgumentException("The topic is not registered!");
        }
        handlers.add(handler);
    }

    public synchronized <T extends Event> void subscribe(final Topic<T> topic, Runnable handler) {
        List<EventHandler> handlers = topics.get(topic);
        if (handlers == null) {
            throw new IllegalArgumentException("The topic is not registered!");
        }
        handlers.add((e) -> handler.run());
    }
}
