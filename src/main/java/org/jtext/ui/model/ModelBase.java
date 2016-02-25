package org.jtext.ui.model;

import org.jtext.ui.event.ModelChangedEvent;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import static org.slf4j.LoggerFactory.getLogger;

public class ModelBase {

    private final Logger logger = getLogger(getClass());
    private final Set<Consumer<ModelChangedEvent>> changeListeners = new HashSet<>();

    public void addChangeListener(final Consumer<ModelChangedEvent> listener) {
        changeListeners.add(listener);
    }

    public void removeChangeListener(final Consumer<ModelChangedEvent> listener) {
        changeListeners.remove(listener);
    }

    @SuppressWarnings("checkstyle:illegalcatch")
    protected void notifyListeners() {
        for (Consumer<ModelChangedEvent> listener : changeListeners) {
            try {
                listener.accept(new ModelChangedEvent());
            } catch (Exception e) {
                logger.warn("Exception occurred while notifying listeners!", e);
            }
        }
    }


}
