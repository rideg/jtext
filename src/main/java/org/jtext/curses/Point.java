package org.jtext.curses;

public final class Point {

    private final int x;
    private final int y;

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

    public Point shiftX(final int value) {
        return Point.at(x + value, y);
    }

    public Point shiftY(final int value) {
        return Point.at(x, y + value);
    }

    public Point shift(final int shiftX, final int shiftY) {
        return Point.at(x + shiftX, y + shiftY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        return x == point.x && y == point.y;

    }

    public Point shift(Point point) {
        return Point.at(x + point.x, y + point.y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point flip() {
        return Point.at(y, x);
    }

    public boolean isRightDownFrom(final Point point) {
        return x >= point.x && y >= point.y;
    }

    public boolean isUpLeftFrom(Point point) {
        return x <= point.x && y <= point.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
