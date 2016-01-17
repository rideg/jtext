package org.jtext.system;

public class ReadKey {

    final ControlKey controlKey;
    final int value;

    public ReadKey(final ControlKey controlKey, final int value) {
        this.controlKey = controlKey;
        this.value = value;
    }


    public ControlKey key() {
        return controlKey;
    }

    public int getValue() {
        return value;
    }
}
