package org.jtext.event;


@FunctionalInterface
public interface EventHandler<Evt extends Event> {

    void handle(Evt event);

}
