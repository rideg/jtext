package org.jtext.system;

public class ReadKey {

    final ControlKey controlKey;

    public ReadKey(final ControlKey controlKey) {
        this.controlKey = controlKey;
    }


    public ControlKey key() {
        return controlKey;
    }
}
