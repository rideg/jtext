package org.jtext.widget.attribute;

public class Border {

    private static final Border SINGLE = new Border('-', '|',
                                                    (char) 218,
                                                    (char) 191,
                                                    (char) 192,
                                                    (char) 217);

    private static final Border DOUBLE = new Border('=',
                                                    (char) 186,
                                                    (char) 201,
                                                    (char) 187,
                                                    (char) 200,
                                                    (char) 188);


    private final char vertical;
    private final char horizontal;
    private final char topLeft;
    private final char topRight;
    private final char bottomLeft;
    private final char bottomRight;


    public Border(final char vertical, final char horizontal,
                  final char topLeft, final char topRight,
                  final char bottomLeft, final char bottomRight) {
        this.vertical = vertical;
        this.horizontal = horizontal;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }

    public char getVertical() {
        return vertical;
    }

    public char getHorizontal() {
        return horizontal;
    }

    public char getTopLeft() {
        return topLeft;
    }

    public char getTopRight() {
        return topRight;
    }

    public char getBottomLeft() {
        return bottomLeft;
    }

    public char getBottomRight() {
        return bottomRight;
    }
}
