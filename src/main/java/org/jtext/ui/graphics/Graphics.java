package org.jtext.ui.graphics;

import org.jtext.curses.CharacterAttribute;
import org.jtext.curses.CharacterColor;
import org.jtext.curses.Curses;
import org.jtext.ui.attribute.Border;

import java.util.Set;

public class Graphics {

    private final Rectangle area;
    private final Curses curses;
    private boolean isRelative = true;


    public Graphics(final Rectangle area, final Curses curses) {
        this.area = area;
        this.curses = curses;
    }

    public Graphics(final Rectangle area, final Graphics graphics) {
        this(area.relativeTo(graphics.getTopLeft()), graphics.curses);
    }

    public void setAbsoluteMode() {
        isRelative = false;
    }

    public void setRelativeMode() {
        isRelative = true;
    }

    public void startDraw() {
        moveCursor(area.topLeft());
    }

    public void setAttributes(final Set<CharacterAttribute> attributes) {
        curses.onAttributes(attributes.toArray(new CharacterAttribute[attributes.size()]));
    }

    public void setAttribute(final CharacterAttribute attribute) {
        curses.onAttribute(attribute);
    }

    public void removeAttribute(final CharacterAttribute attribute) {
        curses.offAttribute(attribute);
    }

    public Set<CharacterAttribute> getAttributes() {
        return null;
    }

    public CharacterColor getBackgroundColor() {
        return null;
    }

    public CharacterColor getForegroundColor() {
        return null;
    }

    public void setBackgroundColor(final CharacterColor color) {
        curses.setBackgroundColor(color);
    }

    public void setForegroundColor(final CharacterColor color) {
        curses.setForegroundColor(color);
    }

    public void drawRectangle(final Rectangle rectangle, final Border border) {
        drawHorizontalLine(rectangle.topLeft(), border.horizontal, rectangle.width);
        drawHorizontalLine(rectangle.bottomLeft(), border.horizontal, rectangle.width);
        drawVerticalLine(rectangle.topLeft(), border.vertical, rectangle.height);
        drawVerticalLine(rectangle.topRight(), border.vertical, rectangle.height);
        putChar(rectangle.topLeft(), border.topLeft);
        putChar(rectangle.topRight(), border.topRight);
        putChar(rectangle.bottomRight(), border.bottomRight);
        putChar(rectangle.bottomLeft(), border.bottomLeft);
    }


    public void drawVerticalLine(final Point point, final char ch, final int length) {
        final Point p = toReal(point);
        curses.drawVerticalLineAt(p.x, p.y, ch, length);
    }

    public void drawHorizontalLine(final Point point, final char ch, final int length) {
        final Point p = toReal(point);
        curses.drawHorizontalLineAt(p.x, p.y, ch, length);
    }

    public void printString(final Point point, final String string) {
        final Point p = toReal(point);
        curses.printStringAt(p.x, p.y, string);
    }

    public void putChar(final Point point, final char ch) {
        final Point p = toReal(point);
        curses.putCharAt(p.x, p.y, ch);
    }

    private void moveCursor(final Point point) {
        final Point p = toReal(point);
        curses.moveCursor(p.x, p.y);
    }

    private Point toReal(final Point point) {
        return isRelative ? Point.at(point.x + area.x, point.y + area.y) : point;
    }

    public Point getTopLeft() {
        return area.topLeft();
    }

    public Point getCursor() {
        return Point.at(curses.getCursorX(), curses.getCursorY());
    }


}
