package org.jtext.ui.graphics;

import org.jtext.curses.Point;
import org.jtext.ui.attribute.Direction;

import java.util.Objects;

public final class Line {

    private final Point start;
    private final int length;
    private final Direction direction;

    public Line(final int x, final int y, final int length, final Direction direction) {
        this(Point.at(x, y), length, direction);
    }

    private Line(final Point start, final int length, final Direction direction) {
        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be negative!");
        }
        this.start = start;
        this.length = length;
        this.direction = direction;
    }

    public int getLength() {
        return length;
    }

    public Direction getDirection() {
        return direction;
    }

    public static Line horizontal(final Point a, final Point b) {
        if (a.getY() != b.getY()) {
            throw new IllegalArgumentException("Cannot create a straight horizontal line between the two points");
        }
        if (a.getX() < b.getX()) {
            return horizontal(a.getX(), a.getY(), b.getX() - a.getX() + 1);
        } else {
            return horizontal(b.getX(), b.getY(), a.getX() - b.getX() + 1);
        }
    }

    public static Line vertical(final Point a, final Point b) {
        if (a.getX() != b.getX()) {
            throw new IllegalArgumentException("Cannot create a straight vertical line between the two points");
        }
        if (a.getY() < b.getY()) {
            return vertical(a.getX(), a.getY(), b.getY() - a.getY() + 1);
        } else {
            return vertical(b.getX(), b.getY(), a.getY() - b.getY() + 1);
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
        return isHorizontal() ? start.shiftX(length - 1) : start.shiftY(length - 1);
    }

    public Point start() {
        return start;
    }

    public boolean isHorizontal() {
        return direction == Direction.HORIZONTAL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Line)) {
            return false;
        }
        final Line line = (Line) o;
        return length == line.length &&
                Objects.equals(start, line.start) &&
                direction == line.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, length, direction);
    }

    @Override
    public String toString() {
        return "Line{" +
                "start=" + start +
                ", length=" + length +
                ", direction=" + direction +
                '}';
    }
}
