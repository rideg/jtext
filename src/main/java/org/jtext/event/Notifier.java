package org.jtext.event;

import org.slf4j.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.slf4j.LoggerFactory.getLogger;

public class Notifier<T> implements Observable<T> {

    private final Logger logger = getLogger(getClass());
    private final Set<Consumer<T>> observers = new HashSet<>();
    private final Supplier<T> eventSupplier;

    public Notifier() {
        eventSupplier = () -> null;
    }

    public Notifier(final Supplier<T> eventSupplier) {
        this.eventSupplier = eventSupplier;
    }

    @Override
    public void subscribe(final Consumer<T> observer) {
        observers.add(observer);
    }

    @SuppressWarnings("checkstyle:illegalcatch")
    public void notifyObservers(T data) {
        for (Consumer<T> o : observers) {
            try {
                o.accept(data);
            } catch (Exception e) {
                logger.warn("Exception occurred while notifying listeners!", e);
            }
        }
    }

    public void notifyObservers() {
        notifyObservers(eventSupplier.get());
    }
}
