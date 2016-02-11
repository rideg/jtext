package org.jtext.ui.graphics;

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

    public Graphics restrict(final Rectangle area) {
        return new Graphics(area.move(this.area.topLeft()), driver);
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
        final Point p = toReal(point);
        driver.drawVerticalLineAt(p.x, p.y, ch, length);
    }

    public void drawHorizontalLine(final Point point, final char ch, final int length) {
        final Point p = toReal(point);
        driver.drawHorizontalLineAt(p.x, p.y, ch, length);
    }

    public void printString(final Point point, final String string) {
        final Point p = toReal(point);
        driver.printStringAt(p.x, p.y, string);
    }

    public void putChar(final Point point, final char ch) {
        final Point p = toReal(point);
        driver.putCharAt(p.x, p.y, ch);
    }

    private Point toReal(final Point point) {
        return isRelative ? Point.at(point.x + area.x, point.y + area.y) : point;
    }

    public Point getTopLeft() {
        return area.topLeft();
    }

    public Rectangle getArea() {
        return area;
    }

    public Point getCursor() {
        return Point.at(driver.getCursorX(), driver.getCursorY());
    }

    public void fillBackground(final CharacterColor characterColor) {
        driver.setBackgroundColor(characterColor);
        Point point = area.topLeft();
        for (int i = 0; i < area.height; i++) {
            driver.drawHorizontalLineAt(point.x, point.y, ' ', area.width);
            point = point.incY();
        }
    }

    public void drawBorder(final Border border) {

    }

    public void clear() {

    }
}
