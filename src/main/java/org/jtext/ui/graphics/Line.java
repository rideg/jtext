package org.jtext.ui.graphics;

import org.jtext.ui.attribute.Direction;

public class Line {

    public final int x;
    public final int y;
    public final int length;
    public final Direction direction;


    public Line(final int x, final int y, final int length, final Direction direction) {
        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be negative!");
        }
        this.x = x;
        this.y = y;
        this.length = length;
        this.direction = direction;
    }


    private Line(final Point start, final int length, final Direction direction) {
        this(start.x, start.y, length, direction);
    }


    public static Line horizontal(final Point a, final Point b) {
        if (a.y != b.y) {
            throw new IllegalArgumentException("Cannot create a straight horizontal line between the two points");
        }
        if (a.x < b.x) {
            return horizontal(a.x, a.y, b.x - a.x + 1);
        } else {
            return horizontal(b.x, b.y, a.x - b.x + 1);
        }
    }

    public static Line vertical(final Point a, final Point b) {
        if (a.x != b.x) {
            throw new IllegalArgumentException("Cannot create a straight vertical line between the two points");
        }
        if (a.y < b.y) {
            return vertical(a.x, a.y, b.y - a.y + 1);
        } else {
            return vertical(b.x, b.y, a.y - b.y + 1);
        }
    }

    public static Line line(final Point a, final Point b, final Direction direction) {
        return direction == Direction.HORIZONTAL ? horizontal(a, b) : vertical(a, b);
    }

    public static Line horizontal(final int x, final int y, final int length) {
        return new Line(x, y, length, Direction.HORIZONTAL);
    }

    public static Line vertical(final int x, final int y, final int length) {
        return new Line(x, y, length, Direction.VERTICAL);
    }

    public static Line horizontal(final Point start, final int length) {
        return new Line(start, length, Direction.HORIZONTAL);
    }

    public static Line vertical(final Point start, final int length) {
        return new Line(start, length, Direction.VERTICAL);
    }

    public Point end() {
        return isHorizontal() ? Point.at(x + length - 1, y) : Point.at(x, y + length - 1);
    }

    public Point start() {
        return Point.at(x, y);
    }

    public boolean isHorizontal() {
        return direction == Direction.HORIZONTAL;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Line line = (Line) o;

        if (x != line.x) return false;
        if (y != line.y) return false;
        if (length != line.length) return false;
        return direction == line.direction;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + length;
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Line{" +
               "x=" + x +
               ", y=" + y +
               ", length=" + length +
               ", direction=" + direction +
               '}';
    }
}
