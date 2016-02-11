package org.jtext.ui.attribute;

import org.jtext.curses.CellDescriptor;
import org.jtext.curses.CharacterColor;

public class Border {

    public static final Border SINGLE = new Border(CellDescriptor.builder().create(),
                                                   '┌', '─', '┐', '│', '┘', '─', '└', '│'
    );

    public static final Border DOUBLE = new Border(CellDescriptor.builder().create(),
                                                   '╔', '═', '╗', '║', '╝', '═', '╚', '║'
    );

    public static final Border ROUNDED_SINGLE = new Border(CellDescriptor.builder().create(),
                                                           '╭', '─', '╮', '│', '╯', '─', '╰', '│'
    );

    public final CellDescriptor topLeft;
    public final CellDescriptor top;
    public final CellDescriptor topRight;
    public final CellDescriptor right;
    public final CellDescriptor bottomRight;
    public final CellDescriptor bottom;
    public final CellDescriptor bottomLeft;
    public final CellDescriptor left;


    public Border(final CellDescriptor descriptor,
                  final char topLeft, final char top, final char topRight, final char right,
                  final char bottomRight, final char bottom, final char bottomLeft, final char left) {

        this(
                descriptor.ch(topLeft),
                descriptor.ch(top),
                descriptor.ch(topRight),
                descriptor.ch(right),
                descriptor.ch(bottomRight),
                descriptor.ch(bottom),
                descriptor.ch(bottomLeft),
                descriptor.ch(left)
            );

    }

    public Border(final CellDescriptor topLeft, final CellDescriptor top, final CellDescriptor topRight,
                  final CellDescriptor right, final CellDescriptor bottomRight, final CellDescriptor bottom,
                  final CellDescriptor bottomLeft, final CellDescriptor left) {


        this.topLeft = topLeft;
        this.top = top;
        this.topRight = topRight;
        this.right = right;
        this.bottomRight = bottomRight;
        this.bottom = bottom;
        this.bottomLeft = bottomLeft;
        this.left = left;
    }

    public Border changeCell(final CellDescriptor descriptor) {
        return new Border(descriptor,
                          topLeft.getCharacter(),
                          top.getCharacter(),
                          topRight.getCharacter(),
                          right.getCharacter(),
                          bottomRight.getCharacter(),
                          bottom.getCharacter(),
                          bottomLeft.getCharacter(),
                          left.getCharacter());
    }


    public Border backgroundColor(final CharacterColor color) {
        return changeCell(CellDescriptor.copy(topLeft).bg(color).create());
    }
}
