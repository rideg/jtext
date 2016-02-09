package org.jtext.ui.event;

public class UIEvent {

    private boolean bubbling = true;


    public void stopBubbling() {
        bubbling = false;
    }

    public boolean isBubbling() {
        return bubbling;
    }
}
