package org.jtext.curses;

import java.util.EnumSet;

public class Descriptor {

    private final CharacterColor foregroundColor;
    private final CharacterColor backgroundColor;
    private final EnumSet<CharacterAttribute> attributes;

    public Descriptor(final CharacterColor characterColor,
                      final CharacterColor backgroundColor,
                      final CharacterAttribute attribute,
                      final CharacterAttribute... restAttributes) {
        this.foregroundColor = characterColor;
        this.backgroundColor = backgroundColor;
        this.attributes = EnumSet.of(attribute, restAttributes);
    }

    public CharacterColor getForegroundColor() {
        return foregroundColor;
    }

    public CharacterAttribute[] getAttributes() {
        return attributes.toArray(new CharacterAttribute[attributes.size()]);
    }

    public CharacterColor getBackgroundColor() {
        return backgroundColor;
    }
}
