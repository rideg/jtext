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

    public Point getTopLeft() {
        return Point.of(x, y);
    }

    public Rectangle relativeTo(final Point p) {
        return Rectangle.of(p.x + x, p.y, width, height);
    }

    public static Rectangle of(final int x, final int y, final int width, final int height) {
        return new Rectangle(x, y, width, height);
    }

    public static Rectangle of(final Point point, final int width, final int height) {
        return new Rectangle(point.x, point.y, width, height);
    }
}
