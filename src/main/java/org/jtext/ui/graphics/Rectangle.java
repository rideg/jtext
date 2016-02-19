package org.jtext.ui.graphics;

@SuppressWarnings("checkstyle:visibilitymodifier")
public final class Rectangle {

    private static final Rectangle EMPTY = Rectangle.of(0, 0, 0, 0);

    public final int x;
    public final int y;
    public final int width;
    public final int height;

    private Rectangle(final int x, final int y, final int width, final int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Dimension cannot be negative!");
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public static Rectangle empty() {
        return EMPTY;
    }

    public static Rectangle of(final int x, final int y, final int width, final int height) {
        return new Rectangle(x, y, width, height);
    }

    public static Rectangle of(final Point point, final Dimension dimension) {
        return new Rectangle(point.x, point.y, dimension.width, dimension.height);
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

    public Rectangle shiftX(final int shift) {
        return Rectangle.of(x + shift, y, width, height);
    }

    public Rectangle shiftY(final int shift) {
        return Rectangle.of(x, y + shift, width, height);
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

    public Rectangle flip() {
        return Rectangle.of(y, x, height, width);
    }

    public boolean has(final Point p) {
        return p.x >= x && p.y >= y && p.x < x + width && p.y < x + height;
    }

    public boolean hasRelative(final Point p) {
        return p.x >= 0 && p.y >= 0 && p.x < width && p.y < height;
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
        return line.isHorizontal() ? 0 <= line.y && line.y < height && line.x < 0
                                   : 0 <= line.x && line.x < width && line.y < 0;
    }

    private Point closestInner(final Point start) {
        return Point.at(Math.min(Math.max(start.x, 0), width - 1), Math.min(Math.max(start.y, 0), height - 1));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Rectangle rectangle = (Rectangle) o;

        return x == rectangle.x && y == rectangle.y && width == rectangle.width && height == rectangle.height;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
               "x=" + x +
               ", y=" + y +
               ", width=" + width +
               ", height=" + height +
               '}';
    }
}
