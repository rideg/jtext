package org.jtext.curses;

import java.util.Set;

public class CellDescriptor {

    private final char character;
    private final CharacterColor foregroundColor;
    private final CharacterColor backgroundColor;
    private final CharacterAttribute[] attributes;

    public CellDescriptor(final char character,
                          final CharacterColor foregroundColor,
                          final CharacterColor backgroundColor,
                          final Set<CharacterAttribute> attributes) {
        this.foregroundColor = foregroundColor;
        this.backgroundColor = backgroundColor;
        this.attributes = attributes.toArray(new CharacterAttribute[attributes.size()]);
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }

    public CharacterColor getForegroundColor() {
        return foregroundColor;
    }

    public CharacterColor getBackgroundColor() {
        return backgroundColor;
    }

    public CharacterAttribute[] getAttributes() {
        return attributes;
    }
}
