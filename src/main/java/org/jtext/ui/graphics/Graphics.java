package org.jtext.ui.graphics;

import org.jtext.curses.CellDescriptor;
import org.jtext.curses.CharacterAttribute;
import org.jtext.curses.CharacterColor;
import org.jtext.curses.Driver;
import org.jtext.ui.attribute.Border;

import java.util.Set;

public class Graphics {

    private final Rectangle area;
    private final Driver driver;
    private boolean isRelative = true;

    public Graphics(final Rectangle area, final Driver driver) {
        this.area = area;
        this.driver = driver;
    }

    private void executeInLock(final Runnable runnable) {
        driver.lock();
        try {
            runnable.run();
        } finally {
            driver.unlock();
        }
    }

    public Graphics restrict(final Rectangle area) {
        return new Graphics(area.relativeTo(this.area.topLeft()), driver);
    }

    public void setAbsoluteMode() {
        isRelative = false;
    }

    public void setRelativeMode() {
        isRelative = true;
    }

    public void setAttributes(final Set<CharacterAttribute> attributes) {
        driver.onAttributes(attributes.toArray(new CharacterAttribute[attributes.size()]));
    }

    public void setAttribute(final CharacterAttribute attribute) {
        driver.onAttribute(attribute);
    }

    public void removeAttribute(final CharacterAttribute attribute) {
        driver.offAttribute(attribute);
    }

    public void setBackgroundColor(final CharacterColor color) {
        driver.setBackgroundColor(color);
    }

    public void setForegroundColor(final CharacterColor color) {
        driver.setForegroundColor(color);
    }

    public void drawVerticalLine(final Point point, final char ch, final int length) {
        final Line line = area.cropRelative(Line.vertical(point, length));
        if (line.length > 0) {
            final Point p = toReal(point);
            executeInLock(() -> driver.drawVerticalLineAt(p.x, p.y, ch, line.length));
        }
    }

    public void drawHorizontalLine(final Point point, final char ch, final int length) {
        final Line inside = area.cropRelative(Line.horizontal(point, length));
        if (inside.length > 0) {
            final Point p = toReal(point);
            driver.drawHorizontalLineAt(p.x, p.y, ch, inside.length);
        }
    }

    public void printString(final Point point, final String string) {
        final Line inside = area.cropRelative(Line.horizontal(point, string.length()));
        if (inside.length > 0) {
            final Point p = toReal(point);
            executeInLock(() -> driver.printStringAt(p.x, p.y, string.substring(0, inside.length)));
        }
    }

    public void putChar(final Point point, final char ch) {
        if (area.has(point)) {
            final Point p = toReal(point);
            executeInLock(() -> driver.putCharAt(p.x, p.y, ch));
        }
    }

    private Point toReal(final Point point) {
        return isRelative ? Point.at(point.x + area.x, point.y + area.y) : point;
    }

    public Point getTopLeft() {
        return area.topLeft();
    }

    public Point getCursor() {
        return Point.at(driver.getCursorX(), driver.getCursorY());
    }

    public void fillBackground(final CharacterColor characterColor) {
        executeInLock(() -> {
            driver.setBackgroundColor(characterColor);
            driver.setForegroundColor(CharacterColor.GREEN);
            Point point = area.topLeft();
            for (int i = 0; i < area.height; i++) {
                driver.drawHorizontalLineAt(point.x, point.y, ' ', area.width);
                point = point.incY();
            }
        });
    }

    public void drawBorder(final Border border) {
        executeInLock(() -> {
            border.topLeft.ifPresent(d -> putCharAt(area.topLeft(), d));
            border.top.ifPresent(d -> drawHorizontalLine(area.topLeft().incX(), area.width - 2, d));
            border.topRight.ifPresent(d -> putCharAt(area.topRight(), d));
            border.right.ifPresent(d -> drawVerticalLine(area.topRight().incY(), area.height - 2, d));
            border.bottomRight.ifPresent(d -> putCharAt(area.bottomRight(), d));
            border.bottom.ifPresent(d -> drawHorizontalLine(area.bottomLeft().incX(), area.width - 2, d));
            border.bottomLeft.ifPresent(d -> putCharAt(area.bottomLeft(), d));
            border.left.ifPresent(d -> drawVerticalLine(area.topLeft().incY(), area.height - 2, d));
        });
    }

    private void drawVerticalLine(Point point, int length, CellDescriptor descriptor) {
        setColorsAndAttributes(descriptor);
        driver.drawVerticalLineAt(point.x, point.y, descriptor.getCharacter(), length);
    }

    private void drawHorizontalLine(Point point, int length, CellDescriptor descriptor) {
        setColorsAndAttributes(descriptor);
        driver.drawHorizontalLineAt(point.x, point.y, descriptor.getCharacter(), length);
    }

    public void putCharAt(final Point point, final CellDescriptor descriptor) {
        setColorsAndAttributes(descriptor);
        driver.putCharAt(point.x, point.y, descriptor.getCharacter());
    }

    private void setColorsAndAttributes(CellDescriptor descriptor) {
        driver.setColor(descriptor.getForegroundColor(), descriptor.getBackgroundColor());
        driver.onAttributes(descriptor.getAttributes());
    }

    public Rectangle getArea() {
        return area;
    }
}
