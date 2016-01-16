package org.jtext.system;

public class Descriptor {

    private final CharacterColor foregroundColor;
    private final CharacterColor backgroundColor;
    private final CharacterAttribute characterAttribute;

    public Descriptor(final CharacterColor characterColor,
                      final CharacterColor backgroundColor,
                      final CharacterAttribute characterAttribute) {
        this.foregroundColor = characterColor;
        this.backgroundColor = backgroundColor;
        this.characterAttribute = characterAttribute;
    }

    public CharacterColor getForegroundColor() {
        return foregroundColor;
    }

    public CharacterAttribute getCharacterAttribute() {
        return characterAttribute;
    }

    public CharacterColor getBackgroundColor() {
        return backgroundColor;
    }
}
