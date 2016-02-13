package org.jtext.ui.graphics;

public class Rectangle {

    public static final Rectangle UNITY = Rectangle.of(0, 0, 1, 1);
    public final int x;
    public final int y;
    public final int width;
    public final int height;

    private Rectangle(final int x, final int y, final int width, final int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Dimension cannot be negative!");
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Dimension dimension() {
        return Dimension.of(width, height);
    }

    public Point topLeft() {
        return Point.at(x, y);
    }

    public Point topRight() {
        return Point.at(x + width - 1, y);
    }

    public Point bottomLeft() {
        return Point.at(x, y + height - 1);
    }

    public Point bottomRight() {
        return Point.at(x + width - 1, y + height - 1);
    }

    public Rectangle relativeTo(final Point p) {
        return Rectangle.of(p.x + x, p.y + y, width, height);
    }

    public static Rectangle of(final int x, final int y, final int width, final int height) {
        return new Rectangle(x, y, width, height);
    }

    public static Rectangle of(final Point point, final Dimension dimension) {
        return new Rectangle(point.x, point.y, dimension.width, dimension.height);
    }

    public Rectangle move(final Point point) {
        return Rectangle.of(point.x, point.y, width, height);
    }

    public Rectangle resize(final int width, final int height) {
        return Rectangle.of(topLeft(), Dimension.of(width, height));
    }

    public Rectangle resize(final Dimension dimension) {
        return Rectangle.of(topLeft(), dimension);
    }

    public Rectangle copy() {
        return Rectangle.of(x, y, width, height);
    }
}
