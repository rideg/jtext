package org.jtext.ui.attribute;

import org.jtext.curses.CellDescriptor;

import java.util.Optional;

@SuppressWarnings("checkstyle:visibilitymodifier")
public class Border {

    private static final Border NO_BORDER = new Border(CellDescriptor.empty(),
            Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
            Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());

    private static final Border SINGLE = new Border(CellDescriptor.empty(), '┌', '─', '┐', '│', '┘', '─', '└', '│');

    private static final Border DUAL = new Border(CellDescriptor.empty(), '╔', '═', '╗', '║', '╝', '═', '╚', '║');

    private static final Border ROUNDED_SINGLE =
            new Border(CellDescriptor.empty(), '╭', '─', '╮', '│', '╯', '─', '╰', '│');

    private final CellDescriptor descriptor;

    public final Optional<Character> topLeft;
    public final Optional<Character> top;
    public final Optional<Character> topRight;
    public final Optional<Character> right;
    public final Optional<Character> bottomRight;
    public final Optional<Character> bottom;
    public final Optional<Character> bottomLeft;
    public final Optional<Character> left;

    public Border(final CellDescriptor descriptor, final char topLeft, final char top, final char topRight,
                  final char right, final char bottomRight, final char bottom, final char bottomLeft, final char left) {

        this(descriptor, Optional.of(topLeft), Optional.of(top),
                Optional.of(topRight), Optional.of(right),
                Optional.of(bottomRight), Optional.of(bottom),
                Optional.of(bottomLeft), Optional.of(left));

    }

    public Border(final CellDescriptor descriptor,
                  final Optional<Character> topLeft, final Optional<Character> top,
                  final Optional<Character> topRight, final Optional<Character> right,
                  final Optional<Character> bottomRight, final Optional<Character> bottom,
                  final Optional<Character> bottomLeft, final Optional<Character> left) {

        this.descriptor = descriptor;
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
        return new Border(descriptor, topLeft, top, topRight, right, bottomRight, bottom, bottomLeft, left);
    }

    public CellDescriptor getDescriptor() {
        return descriptor;
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
