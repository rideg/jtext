package org.jtext.ui.graphics;

public class Rectangle {

    public final int x;
    public final int y;
    public final int width;
    public final int height;

    private Rectangle(final int x, final int y, final int width, final int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

    public static Rectangle of(final Point point, final int width, final int height) {
        return new Rectangle(point.x, point.y, width, height);
    }

    public Rectangle move(final Point point) {
        return Rectangle.of(point.x, point.y, width, height);
    }

    public Rectangle resize(final int width, final int height) {
        return Rectangle.of(topLeft(), width, height);
    }
}
