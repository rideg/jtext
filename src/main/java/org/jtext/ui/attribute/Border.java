package org.jtext.ui.attribute;

public class Border {

    public static final Border SINGLE = new Border('┃', '━', '┏', '┓', '┗', '┛');

    public static final Border DOUBLE = new Border('=',
                                                    (char) 186,
                                                    (char) 201,
                                                    (char) 187,
                                                    (char) 200,
                                                    (char) 188);


    public final char vertical;
    public final char horizontal;
    public final char topLeft;
    public final char topRight;
    public final char bottomLeft;
    public final char bottomRight;


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

}
