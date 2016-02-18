package org.jtext.curses;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CellDescriptor {

    private final char character;
    private final CharacterColor foregroundColor;
    private final CharacterColor backgroundColor;
    private final CharacterAttribute[] attributes;

    public CellDescriptor(final char character, final CharacterColor foregroundColor,
                          final CharacterColor backgroundColor, final Set<CharacterAttribute> attributes) {
        this.foregroundColor = foregroundColor;
        this.backgroundColor = backgroundColor;
        this.attributes = attributes.toArray(new CharacterAttribute[attributes.size()]);
        this.character = character;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder copy(final CellDescriptor descriptor) {
        return new Builder().ch(descriptor.getCharacter()).bg(descriptor.getBackgroundColor())
                       .fg(descriptor.getForegroundColor()).attr(descriptor.getAttributes());
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

    public CellDescriptor ch(final char ch) {
        return new CellDescriptor(ch, foregroundColor, backgroundColor, new HashSet<>(Arrays.asList(attributes)));
    }

    public static class Builder {

        private char character = ' ';
        private CharacterColor foregroundColor = CharacterColor.WHITE;
        private CharacterColor backgroundColor = CharacterColor.BLACK;
        private Set<CharacterAttribute> attributes = new HashSet<>();


        public Builder ch(final char character) {
            this.character = character;
            return this;
        }

        public Builder fg(final CharacterColor color) {
            this.foregroundColor = color;
            return this;
        }

        public Builder bg(final CharacterColor color) {
            this.backgroundColor = color;
            return this;
        }

        public Builder attr(final CharacterAttribute attribute, final CharacterAttribute... rest) {
            attributes = new HashSet<>();
            attributes.add(attribute);
            attributes.addAll(Arrays.asList(rest));
            return this;
        }

        public Builder attr(final CharacterAttribute[] attributes) {
            this.attributes = new HashSet<>();
            this.attributes.addAll(Arrays.asList(attributes));
            return this;
        }

        public CellDescriptor create() {
            return new CellDescriptor(character, foregroundColor, backgroundColor, attributes);
        }
    }
}
