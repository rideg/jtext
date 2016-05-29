package org.jtext.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public class Notifier<T> implements Observable<T> {

    private final Collection<Consumer<T>> observers = new ArrayList<>();

    @Override
    public void subscribe(final Consumer<T> observer) {
        observers.add(observer);
    }

    public void notifyObservers(T data) {
        observers.forEach(o -> o.accept(data));
    }
}
