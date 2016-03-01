package org.jtext.curses;

import java.util.Arrays;
import java.util.Objects;


@SuppressWarnings("checkstyle:visibilitymodifier")
public final class CellDescriptor {

    private final ColorName foreground;
    private final ColorName background;
    private final CharacterAttribute[] attributes;

    private CellDescriptor(final ColorName foreground, final ColorName background, final CharacterAttribute[] attributes) {
        Objects.requireNonNull(foreground);
        Objects.requireNonNull(background);
        Objects.requireNonNull(attributes);
        this.foreground = foreground;
        this.background = background;
        this.attributes = attributes;
    }

    public static CellDescriptor empty() {
        return new CellDescriptor(ColorName.NO_COLOR, ColorName.NO_COLOR, new CharacterAttribute[0]);
    }

    public static CellDescriptor foreground(final ColorName color) {
        return of(ColorName.NO_COLOR, color);
    }

    public static CellDescriptor background(final ColorName color) {
        return of(color, ColorName.NO_COLOR);
    }

    public static CellDescriptor of(final ColorName background, final ColorName foreground) {
        return new CellDescriptor(foreground, background, new CharacterAttribute[0]);
    }

    public static CellDescriptor of(final ColorName background, final ColorName foreground,
                                    final CharacterAttribute first,
                                    final CharacterAttribute... rest) {
        final CharacterAttribute[] attributes = new CharacterAttribute[rest.length + 1];
        attributes[0] = first;
        System.arraycopy(rest, 0, attributes, 1, rest.length);
        return new CellDescriptor(foreground, background, attributes);
    }

    public CellDescriptor withBackground(final ColorName colorName) {
        return new CellDescriptor(colorName, foreground, attributes);
    }

    public CellDescriptor withForeground(final ColorName colorName) {
        return new CellDescriptor(background, colorName, attributes);
    }

    public ColorName getForeground() {
        return foreground;
    }

    public ColorName getBackground() {
        return background;
    }

    public CharacterAttribute[] getAttributes() {
        return Arrays.copyOf(attributes, attributes.length);
    }

    public boolean hasForeground() {
        return foreground != ColorName.NO_COLOR;
    }

    public boolean hasBackground() {
        return background != ColorName.NO_COLOR;
    }

    @Override
    public String toString() {
        return "CellDescriptor{" +
                ", foreground=" + foreground +
                ", background=" + background +
                ", attributes=" + Arrays.toString(attributes) +
                '}';
    }
}
