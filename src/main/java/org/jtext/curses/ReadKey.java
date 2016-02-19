package org.jtext.curses;

@SuppressWarnings("checkstyle:visibilitymodifier")
public class ReadKey {

    public final ControlKey controlKey;
    public final int value;

    public ReadKey(final ControlKey controlKey, final int value) {
        this.controlKey = controlKey;
        this.value = value;
    }

}
