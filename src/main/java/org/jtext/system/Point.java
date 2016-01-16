package org.jtext.system;

public class Point {

    final int x;
    final int y;

    public Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public Point nextRow() {
        return new Point(x, y + 1);
    }

    public Point nextColumn() {
        return new Point(x + 1, y);
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
