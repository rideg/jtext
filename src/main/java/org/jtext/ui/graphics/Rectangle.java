package org.jtext.ui.graphics;

import org.jtext.curses.Point;

import java.util.Objects;

public final class Rectangle {

    private static final Rectangle EMPTY = Rectangle.of(0, 0, 0, 0);

    private final Point topLeft;
    private final Dimension dimension;

    private Rectangle(final Point topLeft, final Dimension dimension) {
        Objects.requireNonNull(topLeft);
        Objects.requireNonNull(dimension);
        this.topLeft = topLeft;
        this.dimension = dimension;
    }

    public static Rectangle empty() {
        return EMPTY;
    }

    public static Rectangle of(final int x, final int y, final int width, final int height) {
        return new Rectangle(Point.at(x, y), Dimension.of(width, height));
    }

    public static Rectangle of(final Point point, final Dimension dimension) {
        return new Rectangle(point, dimension);
    }

    public Dimension getDimension() {
        return dimension;
    }

    public int getWidth() {
        return dimension.getWidth();
    }

    public int getHeight() {
        return dimension.getHeight();
    }

    public Point topLeft() {
        return topLeft;
    }

    public Point topRight() {
        return topLeft.shiftX(dimension.getWidth() - 1);
    }

    public Point bottomLeft() {
        return topLeft.shiftY(dimension.getHeight() - 1);
    }

    public Point bottomRight() {
        return topLeft.shift(dimension.getWidth() - 1, dimension.getHeight() - 1);
    }

    public Rectangle relativeTo(final Point p) {
        return Rectangle.of(topLeft.shift(p), dimension);
    }

    public Rectangle shiftX(final int shift) {
        return Rectangle.of(topLeft.shiftX(shift), dimension);
    }

    public Rectangle shiftY(final int shift) {
        return Rectangle.of(topLeft.shiftY(shift), dimension);
    }

    public Rectangle move(final Point point) {
        return Rectangle.of(point, dimension);
    }

    public Rectangle resize(final int width, final int height) {
        return Rectangle.of(topLeft, Dimension.of(width, height));
    }

    public Rectangle resize(final Dimension dimension) {
        return Rectangle.of(topLeft, dimension);
    }

    public Rectangle copy() {
        return Rectangle.of(topLeft, dimension);
    }

    public Rectangle flip() {
        return Rectangle.of(topLeft.flip(), dimension.flip());
    }

    public boolean has(final Point point) {
        return point.isRightDownFrom(topLeft()) && point.isUpLeftFrom(bottomRight());
    }

    public boolean hasRelative(final Point p) {
        return p.isRightDownFrom(Point.at(0, 0)) && p.isUpLeftFrom(Point.at(getWidth(), getHeight()));
    }

    public Line cropRelative(final Line line) {
        if (line.length > 0) {
            if (hasRelative(line.end()) || hasRelative(line.start()) || hasCommonPoints(line)) {
                return Line.line(closestInner(line.start()), closestInner(line.end()), line.direction);
            }
        }
        return new Line(0, 0, 0, line.direction);
    }

    private boolean hasCommonPoints(final Line line) {
        return line.isHorizontal() ? 0 <= line.y && line.y < getHeight() && line.x < 0
                : 0 <= line.x && line.x < getWidth() && line.y < 0;
    }

    private Point closestInner(final Point start) {
        return Point.at(Math.min(Math.max(start.getX(), 0), getWidth() - 1),
                Math.min(Math.max(start.getY(), 0), getHeight() - 1));
    }

    public Dimension shrink(int horizontal, int vertical) {
        return dimension.shrink(horizontal, vertical);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rectangle)) {
            return false;
        }
        final Rectangle rectangle = (Rectangle) o;
        return topLeft.equals(rectangle.topLeft) &&
                dimension.equals(rectangle.dimension);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "topLeft=" + topLeft +
                ", dimension=" + dimension +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(topLeft, dimension);
    }
}
