package org.jtext.ui.graphics;

import org.jtext.curses.CellDescriptor;
import org.jtext.curses.CharacterAttribute;
import org.jtext.curses.Color;
import org.jtext.curses.Driver;
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

    public void setBackgroundColor(final Color color) {
        final Color fg = colorManager.getColor(driver.getForegroundColor());
        driver.setColor(colorManager.getPairId(fg, color));
    }

    public void setForegroundColor(final Color color) {
        final Color bg = colorManager.getColor(driver.getBackgroundColor());
        driver.setColor(colorManager.getPairId(color, bg));
    }

    public void drawVerticalLine(final Point point, final char ch, final int length) {
        final Line line = area.cropRelative(Line.vertical(point, length));
        if (line.length > 0) {
            final Point p = toReal(point);
            driver.drawVerticalLineAt(p.x, p.y, ch, line.length);
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
            driver.printStringAt(p.x, p.y, string.substring(0, inside.length));
        }
    }

    public void putChar(final Point point, final char ch) {
        if (area.hasRelative(point)) {
            final Point p = toReal(point);
            driver.putCharAt(p.x, p.y, ch);
        }
    }

    private Point toReal(final Point point) {
        return Point.at(point.x + area.x, point.y + area.y);
    }

    public Point getTopLeft() {
        return area.topLeft();
    }

    public Point getCursor() {
        return Point.at(driver.getCursorX(), driver.getCursorY());
    }

    public void fillBackground(final Color color) {
        setBackgroundColor(color);
        Point point = area.topLeft();
        for (int i = 0; i < area.height; i++) {
            driver.drawHorizontalLineAt(point.x, point.y, ' ', area.width);
            point = point.incY();
        }
    }

    public void drawBorder(final Border border) {
        border.topLeft.ifPresent(d -> putCharAt(area.topLeft(), d));
        border.top.ifPresent(d -> drawHorizontalLine(area.topLeft().incX(), area.width - 2, d));
        border.topRight.ifPresent(d -> putCharAt(area.topRight(), d));
        border.right.ifPresent(d -> drawVerticalLine(area.topRight().incY(), area.height - 2, d));
        border.bottomRight.ifPresent(d -> putCharAt(area.bottomRight(), d));
        border.bottom.ifPresent(d -> drawHorizontalLine(area.bottomLeft().incX(), area.width - 2, d));
        border.bottomLeft.ifPresent(d -> putCharAt(area.bottomLeft(), d));
        border.left.ifPresent(d -> drawVerticalLine(area.topLeft().incY(), area.height - 2, d));
    }

    public void changeAttributeAt(final Point point, final int length, CellDescriptor descriptor) {
        final Line inside = area.cropRelative(Line.horizontal(point, length));
        if (inside.length > 0) {
            final Point p = toReal(point);
            driver.changeAttributeAt(p.x, p.y, inside.length,
                                     colorManager.getPairId(descriptor.foreground.get(), descriptor.background.get()),
                                     descriptor.attributes);
        }
    }

    private void drawVerticalLine(Point point, int length, CellDescriptor descriptor) {
        descriptor.character.ifPresent(ch -> {
            setColorsAndAttributes(descriptor);
            driver.drawVerticalLineAt(point.x, point.y, ch, length);
        });
    }

    private void drawHorizontalLine(Point point, int length, CellDescriptor descriptor) {
        descriptor.character.ifPresent(ch -> {
            setColorsAndAttributes(descriptor);
            driver.drawHorizontalLineAt(point.x, point.y, ch, length);
        });
    }

    public void putCharAt(final Point point, final CellDescriptor descriptor) {
        descriptor.character.ifPresent(ch -> {
            setColorsAndAttributes(descriptor);
            driver.putCharAt(point.x, point.y, ch);
        });
    }

    private void setColorsAndAttributes(CellDescriptor descriptor) {
        if (descriptor.background.isPresent() && descriptor.foreground.isPresent()) {
            driver.setColor(colorManager.getPairId(descriptor.foreground.get(), descriptor.background.get()));
        } else {
            descriptor.background.ifPresent(this::setBackgroundColor);
            descriptor.foreground.ifPresent(this::setForegroundColor);
        }
        driver.onAttributes(descriptor.attributes);
    }

    public Rectangle getArea() {
        return area;
    }
}
