package org.jtext.ui.util;

import org.jtext.curses.ControlKey;
import org.jtext.curses.ReadKey;
import org.jtext.ui.event.KeyPressedEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class KeyEventProcessor {

    private final Map<ControlKey, Consumer<ReadKey>> handlers = new HashMap<>();
    private final boolean stopPropagation;

    public KeyEventProcessor() {
        this(false);
    }

    public KeyEventProcessor(boolean stopPropagation) {
        this.stopPropagation = stopPropagation;
    }

    public void register(final ControlKey key, Consumer<ReadKey> handler) {
        handlers.put(key, handler);
    }

    public void register(final ControlKey key, Runnable runnable) {
        handlers.put(key, e -> runnable.run());
    }

    public boolean handle(KeyPressedEvent event) {
        boolean isHandled = false;
        Optional<Consumer<ReadKey>> handler = Optional.ofNullable(handlers.get(event.getKey().controlKey));
        if (handler.isPresent()) {
            handler.get().accept(event.getKey());
            isHandled = true;
        }
        if (isHandled && stopPropagation) {
            event.stopPropagating();
        }
        return isHandled;
    }

}
