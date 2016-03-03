package org.jtext.ui.graphics;

import org.jtext.curses.CellDescriptor;
import org.jtext.curses.CharacterAttribute;
import org.jtext.curses.ColorName;
import org.jtext.curses.Driver;
import org.jtext.curses.Point;
import org.jtext.ui.attribute.Border;
import org.jtext.ui.theme.ColorManager;

public class Graphics {

    private final Rectangle area;
    private final Driver driver;
    private final ColorManager colorManager;

    public Graphics(final Rectangle area, final Driver driver, final ColorManager colorManager) {
        this.area = area;
        this.driver = driver;
        this.colorManager = colorManager;
        driver.clearStyle();
    }

    public Graphics restrict(final Rectangle area) {
        return new Graphics(area.relativeTo(this.area.topLeft()), driver, colorManager);
    }

    public void setAttributes(final CharacterAttribute[] attributes) {
        driver.onAttributes(attributes);
    }

    public void setAttribute(final CharacterAttribute attribute) {
        driver.onAttribute(attribute);
    }

    public void removeAttribute(final CharacterAttribute attribute) {
        driver.offAttribute(attribute);
    }

    public void setBackgroundColor(final ColorName color) {
        final ColorName fg = ColorName.getById(driver.getForegroundColor());
        driver.setColor(colorManager.getPairId(fg, color));
    }

    public void setForegroundColor(final ColorName color) {
        final ColorName bg = ColorName.getById(driver.getBackgroundColor());
        driver.setColor(colorManager.getPairId(color, bg));
    }

    public void drawVerticalLine(final Point point, final char ch, final int length) {
        final Line line = area.cropRelative(Line.vertical(point, length));
        if (line.getLength() > 0) {
            final Point p = toReal(point);
            driver.drawVerticalLineAt(p.getX(), p.getY(), ch, line.getLength());
        }
    }

    public void drawHorizontalLine(final Point point, final char ch, final int length) {
        final Line inside = area.cropRelative(Line.horizontal(point, length));
        if (inside.getLength() > 0) {
            final Point p = toReal(point);
            driver.drawHorizontalLineAt(p.getX(), p.getY(), ch, inside.getLength());
        }
    }

    public void printString(final Point point, final String string) {
        final Line inside = area.cropRelative(Line.horizontal(point, string.length()));
        if (inside.getLength() > 0) {
            final Point p = toReal(point);
            driver.printStringAt(p.getX(), p.getY(), string.substring(0, inside.getLength()));
        }
    }

    public void putChar(final Point point, final char ch) {
        if (area.hasRelative(point)) {
            final Point p = toReal(point);
            driver.putCharAt(p.getX(), p.getY(), ch);
        }
    }

    private Point toReal(final Point point) {
        return area.topLeft().shift(point);
    }

    public Point getTopLeft() {
        return area.topLeft();
    }

    public Point getCursor() {
        return driver.getCursor();
    }

    public void fillBackground(final ColorName color) {
        setBackgroundColor(color);
        Point point = area.topLeft();
        for (int i = 0; i < area.getHeight(); i++) {
            driver.drawHorizontalLineAt(point.getX(), point.getY(), ' ', area.getWidth());
            point = point.incY();
        }
    }

    public void drawBorder(final Border border) {
        final CellDescriptor descriptor = border.getDescriptor();
        border.topLeft.ifPresent(d -> putCharReal(area.topLeft(), d, descriptor));
        border.top.ifPresent(d -> drawHorizontalLine(area.topLeft().incX(), area.getWidth() - 2, d, descriptor));
        border.topRight.ifPresent(d -> putCharReal(area.topRight(), d, descriptor));
        border.right.ifPresent(d -> drawVerticalLine(area.topRight().incY(), area.getHeight() - 2, d, descriptor));
        border.bottomRight.ifPresent(d -> putCharReal(area.bottomRight(), d, descriptor));
        border.bottom.ifPresent(d -> drawHorizontalLine(area.bottomLeft().incX(), area.getWidth() - 2, d, descriptor));
        border.bottomLeft.ifPresent(d -> putCharReal(area.bottomLeft(), d, descriptor));
        border.left.ifPresent(d -> drawVerticalLine(area.topLeft().incY(), area.getHeight() - 2, d, descriptor));
    }

    public void changeAttributeAt(final Point point, final int length, CellDescriptor descriptor) {
        final Line inside = area.cropRelative(Line.horizontal(point, length));
        if (inside.getLength() > 0) {
            final Point p = toReal(point);
            driver.changeAttributeAt(p.getX(), p.getY(), inside.getLength(),
                    colorManager.getPairId(descriptor.getForeground(), descriptor.getBackground()),
                    descriptor.getAttributes());
        }
    }

    private void drawVerticalLine(Point point, int length, final char ch, CellDescriptor descriptor) {
        setColorsAndAttributes(descriptor);
        driver.drawVerticalLineAt(point.getX(), point.getY(), ch, length);
    }

    private void drawHorizontalLine(Point point, int length, final char ch, CellDescriptor descriptor) {
        setColorsAndAttributes(descriptor);
        driver.drawHorizontalLineAt(point.getX(), point.getY(), ch, length);
    }

    private void putCharReal(final Point point, final char ch, final CellDescriptor descriptor) {
        setColorsAndAttributes(descriptor);
        driver.putCharAt(point.getX(), point.getY(), ch);
    }

    public void putCharAt(final Point point, final char ch, final CellDescriptor descriptor) {
        putCharReal(toReal(point), ch, descriptor);
    }

    private void setColorsAndAttributes(CellDescriptor descriptor) {
        driver.setColor(colorManager.getPairId(descriptor.getForeground(), descriptor.getBackground()));
        driver.onAttributes(descriptor.getAttributes());
    }

    public Rectangle getArea() {
        return area;
    }
}
