package org.jtext.ui.event;

public class UIEvent {

    private boolean bubbling = true;
    private boolean propagating = true;

    public void stopBubbling() {
        bubbling = false;
    }

    public boolean isBubbling() {
        return bubbling;
    }

    public void stopPropagating() {
        propagating = false;
    }

    public boolean isPropagating() {
        return propagating;
    }
}
