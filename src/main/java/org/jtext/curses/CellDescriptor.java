package org.jtext.curses;

import java.util.Optional;
import java.util.Set;


@SuppressWarnings("checkstyle:visibilitymodifier")
public final class CellDescriptor {

    public final Optional<Character> character;
    public final Optional<CharacterColor> foreground;
    public final Optional<CharacterColor> background;
    public final CharacterAttribute[] attributes;

    private CellDescriptor(final char character, final CharacterColor background, final CharacterColor foreground,
                           final Set<CharacterAttribute> attributes) {
        this(character, foreground, background, attributes.toArray(new CharacterAttribute[attributes.size()]));
    }

    private CellDescriptor(final Character character, final CharacterColor foreground, final CharacterColor background,
                           final CharacterAttribute[] attributes) {
        this.foreground = Optional.ofNullable(foreground);
        this.background = Optional.ofNullable(background);
        this.character = Optional.ofNullable(character);
        this.attributes = attributes;
    }

    public static CellDescriptor empty() {
        return new CellDescriptor(null, null, null, new CharacterAttribute[0]);
    }

    public static CellDescriptor of(final char character, final CharacterColor background,
                                    final CharacterColor foreground) {
        return new CellDescriptor(character, foreground, background, new CharacterAttribute[0]);
    }


    public static CellDescriptor of(final CharacterColor background, final CharacterColor foreground) {
        return new CellDescriptor(null, foreground, background, new CharacterAttribute[0]);
    }

    public static CellDescriptor of(final CharacterColor background, final CharacterColor foreground,
                                    final CharacterAttribute first, final CharacterAttribute... rest) {
        final CharacterAttribute[] attributes = new CharacterAttribute[rest.length + 1];
        attributes[0] = first;
        System.arraycopy(rest, 0, attributes, 1, rest.length);
        return new CellDescriptor(null, foreground, background, attributes);
    }

    public static CellDescriptor of(final char character, final CharacterColor background,
                                    final CharacterColor foreground, CharacterAttribute first,
                                    CharacterAttribute... rest) {
        final CharacterAttribute[] attributes = new CharacterAttribute[rest.length + 1];
        attributes[0] = first;
        System.arraycopy(rest, 0, attributes, 1, rest.length);
        return new CellDescriptor(character, foreground, background, attributes);
    }

    public CellDescriptor withCh(final char character) {
        return new CellDescriptor(character, foreground.orElse(null), background.orElse(null), attributes);
    }

    public CellDescriptor withBackground(final CharacterColor color) {
        return new CellDescriptor(character.orElse(null), color, foreground.orElse(null), attributes);
    }

    public CellDescriptor withForeground(final CharacterColor color) {
        return new CellDescriptor(character.orElse(null), background.orElse(null), color, attributes);
    }

}
