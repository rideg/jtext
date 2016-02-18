package org.jtext.ui.attribute;

import org.jtext.curses.CellDescriptor;

import java.util.Optional;

@SuppressWarnings("checkstyle:visibilitymodifier")
public class Border {

    private static final Border NO_BORDER =
            new Border(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                       Optional.empty(), Optional.empty(), Optional.empty());

    private static final Border SINGLE = new Border(CellDescriptor.empty(), '┌', '─', '┐', '│', '┘', '─', '└', '│');

    private static final Border DUAL = new Border(CellDescriptor.empty(), '╔', '═', '╗', '║', '╝', '═', '╚', '║');

    private static final Border ROUNDED_SINGLE =
            new Border(CellDescriptor.empty(), '╭', '─', '╮', '│', '╯', '─', '╰', '│');
    public final Optional<CellDescriptor> topLeft;
    public final Optional<CellDescriptor> top;
    public final Optional<CellDescriptor> topRight;
    public final Optional<CellDescriptor> right;
    public final Optional<CellDescriptor> bottomRight;
    public final Optional<CellDescriptor> bottom;
    public final Optional<CellDescriptor> bottomLeft;
    public final Optional<CellDescriptor> left;

    public Border(final CellDescriptor descriptor, final char topLeft, final char top, final char topRight,
                  final char right, final char bottomRight, final char bottom, final char bottomLeft, final char left) {

        this(Optional.of(descriptor.withCh(topLeft)), Optional.of(descriptor.withCh(top)),
             Optional.of(descriptor.withCh(topRight)), Optional.of(descriptor.withCh(right)),
             Optional.of(descriptor.withCh(bottomRight)), Optional.of(descriptor.withCh(bottom)),
             Optional.of(descriptor.withCh(bottomLeft)), Optional.of(descriptor.withCh(left)));

    }

    public Border(final Optional<CellDescriptor> topLeft, final Optional<CellDescriptor> top,
                  final Optional<CellDescriptor> topRight, final Optional<CellDescriptor> right,
                  final Optional<CellDescriptor> bottomRight, final Optional<CellDescriptor> bottom,
                  final Optional<CellDescriptor> bottomLeft, final Optional<CellDescriptor> left) {


        this.topLeft = topLeft;
        this.top = top;
        this.topRight = topRight;
        this.right = right;
        this.bottomRight = bottomRight;
        this.bottom = bottom;
        this.bottomLeft = bottomLeft;
        this.left = left;
    }

    public static Border no() {
        return NO_BORDER;
    }

    public static Border single() {
        return SINGLE;
    }

    public static Border dual() {
        return DUAL;
    }

    public static Border rounded() {
        return ROUNDED_SINGLE;
    }

    public Border changeCell(final CellDescriptor descriptor) {
        return new Border(topLeft.map(v -> descriptor.withCh(v.character.get())),
                          top.map(v -> descriptor.withCh(v.character.get())),
                          topRight.map(v -> descriptor.withCh(v.character.get())),
                          right.map(v -> descriptor.withCh(v.character.get())),
                          bottomRight.map(v -> descriptor.withCh(v.character.get())),
                          bottom.map(v -> descriptor.withCh(v.character.get())),
                          bottomLeft.map(v -> descriptor.withCh(v.character.get())),
                          left.map(v -> descriptor.withCh(v.character.get())));
    }


    public int getTopThickness() {
        return top.isPresent() ||
               (!left.isPresent() && topLeft.isPresent()) ||
               (!right.isPresent() && topRight.isPresent()) ? 1 : 0;
    }

    public int getLeftThickness() {
        return left.isPresent() ||
               (!top.isPresent() && topLeft.isPresent()) ||
               (!bottom.isPresent() && bottomLeft.isPresent()) ? 1 : 0;
    }

    public int getRightThickness() {
        return right.isPresent() ||
               (!top.isPresent() && topRight.isPresent()) ||
               (!bottom.isPresent() && bottomRight.isPresent()) ? 1 : 0;
    }

    public int getBottomThickness() {
        return bottom.isPresent() ||
               (!left.isPresent() && bottomLeft.isPresent()) ||
               (!right.isPresent() && bottomRight.isPresent()) ? 1 : 0;
    }

}
