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
        moveCursor(area.x, area.y);
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

    public void drawRectangleAt(final Rectangle rectangle, final Border border) {

    }

    public void drawRectangle(final int width, final int height, final Border border) {
        Point start = getCursor();
        putChar(border.getTopLeft());
        drawHorizontalLineAt(start.x + 1, start.y, border.getHorizontal(), width - 2);
        putCharAt(start.x + width - 1, start.y, border.getTopRight());
        drawVerticalLineAt(start.x + width - 1, start.y + 1, border.getVertical(), height - 2);
        drawVerticalLineAt(start.x, start.y + 1, border.getVertical(), height - 2);
        putCharAt(start.x, start.y + height - 1, border.getBottomLeft());
        drawHorizontalLineAt(start.x + 1, start.y + height - 1, border.getHorizontal(), width - 2);
        putCharAt(start.x + width - 1, start.y + height - 1, border.getBottomRight());
    }


    public void drawVerticalLineAt(final int x, final int y, final char ch, final int length) {
        curses.drawVerticalLineAt(x, y, ch, length);
    }

    public void drawVerticalLine(final char ch, final int length) {
        curses.drawVerticalLine(ch, length);
    }

    public void drawHorizontalLineAt(final int x, final int y, final char ch, final int lenght) {
        curses.drawHorizontalLineAt(x, y, ch, lenght);
    }

    public void drawHorizontalLine(final char ch, final int length) {
        curses.drawHorizontalLine(ch, length);
    }

    public void printStringAt(final int x, final int y, final String string) {
        curses.printStringAt(x, y, string);
    }

    public void printString(final String string) {
        curses.printString(string);
    }

    public void putCharAt(final int x, final int y, final char ch) {
        curses.putCharAt(x, y, ch);
    }

    public void putChar(final char ch) {
        curses.putChar(ch);
    }

    public void moveCursor(final int x, final int y) {
        curses.moveCursor(x, y);
    }


    public Point getTopLeft() {
        return area.getTopLeft();
    }

    public Point getCursor() {
        return Point.of(curses.getCursorX(), curses.getCursorY());
    }


}
