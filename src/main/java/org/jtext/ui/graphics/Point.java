package org.jtext.ui.graphics;

public class Point {

    public final int x;
    public final int y;

    private Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public static Point at(final int x, final int y) {
        return new Point(x, y);
    }

    public Point decY() {
        return Point.at(x, y - 1);
    }

    public Point decX() {
        return Point.at(x - 1, y);
    }

    public Point incY() {
        return Point.at(x, y + 1);
    }

    public Point incX() {
        return Point.at(x + 1, y);
    }
}
