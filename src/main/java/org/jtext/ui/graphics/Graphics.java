package org.jtext.ui.graphics;

import org.jtext.curses.CellDescriptor;
import org.jtext.curses.CharacterAttribute;
import org.jtext.curses.CharacterColor;
import org.jtext.ui.attribute.Border;

import java.util.Set;

public class Graphics {

    private final Rectangle area;
    private final CursesWindow window;
    private boolean isRelative = true;


    public Graphics(final Rectangle area, final CursesWindow window) {
        this.area = area;
        this.window = window;
    }

    public Graphics(final Rectangle area, final Graphics graphics) {
        this(area.relativeTo(graphics.getTopLeft()), graphics.window);
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
        window.onAttributes(attributes.toArray(new CharacterAttribute[attributes.size()]));
    }

    public void setAttribute(final CharacterAttribute attribute) {
        window.onAttribute(attribute);
    }

    public void removeAttribute(final CharacterAttribute attribute) {
        window.offAttribute(attribute);
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
        window.setBackgroundColor(color);
    }

    public void setForegroundColor(final CharacterColor color) {
        window.setForegroundColor(color);
    }

    public void drawVerticalLine(final Point point, final char ch, final int length) {
        final Point p = toReal(point);
        window.drawVerticalLineAt(p, ch, length);
    }

    public void drawHorizontalLine(final Point point, final char ch, final int length) {
        final Point p = toReal(point);
        window.drawHorizontalLineAt(p, ch, length);
    }

    public void printString(final Point point, final String string) {
        final Point p = toReal(point);
        window.printStringAt(p, string);
    }

    public void putChar(final Point point, final char ch) {
        final Point p = toReal(point);
        window.putCharAt(p, ch);
    }

    private void moveCursor(final Point point) {
        final Point p = toReal(point);
        window.moveCursor(p);
    }

    private Point toReal(final Point point) {
        return isRelative ? Point.at(point.x + area.x, point.y + area.y) : point;
    }

    public Point getTopLeft() {
        return area.topLeft();
    }

    public Point getCursor() {
        return Point.at(window.getCursorX(), window.getCursorY());
    }

    public void fillBackground(final CharacterColor characterColor) {
        window.fillBackground(CellDescriptor.builder().bg(characterColor).create());
    }

    public void drawBorder(final Border border) {

    }
}
