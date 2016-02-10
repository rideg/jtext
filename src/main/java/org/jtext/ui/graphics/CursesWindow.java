package org.jtext.ui.graphics;

import org.jtext.curses.CellDescriptor;
import org.jtext.curses.CharacterAttribute;
import org.jtext.curses.CharacterColor;
import org.jtext.curses.Driver;
import org.jtext.ui.attribute.Border;

public class CursesWindow {

    private final Driver driver;
    private final int id;
    private final WindowState state;


    public CursesWindow(final Driver driver, final WindowState state) {
        this.driver = driver;
        this.state = state;
        final Rectangle area = state.getArea();
        this.id = driver.createWindow(area.x, area.y, area.width, area.height);
    }

    public int getId() {
        return id;
    }

    public WindowState getState() {
        return state;
    }

    public boolean isVisible() {
        return state.isVisible();
    }

    public Rectangle getArea() {
        return state.getArea();
    }

    public int getZIndex() {
        return state.getZIndex();
    }

    public void setColor(final CharacterColor foreground, final CharacterColor background) {
        driver.setColor(id, foreground, background);
    }

    public void setBackgroundColor(final CharacterColor color) {
        driver.setBackgroundColor(id, color);
    }

    public void setForegroundColor(final CharacterColor color) {
        driver.setForegroundColor(id, color);
    }

    public void onAttributes(final CharacterAttribute[] attributes) {
        driver.onAttributes(id, attributes);
    }

    public void onAttribute(final CharacterAttribute attribute) {
        driver.onAttribute(id, attribute);
    }

    public void offAttribute(final CharacterAttribute attribute) {
        driver.offAttribute(id, attribute);
    }

    public void drawHorizontalLineAt(final Point point, char character, int length) {
        driver.drawHorizontalLineAt(id, point.x, point.y, character, length);
    }

    public void drawVerticalLineAt(final Point point, char character, int length) {
        driver.drawVerticalLineAt(id, point.x, point.y, character, length);
    }

    public void printStringAt(final Point point, final String string) {
        driver.printStringAt(id, point.x, point.y, string);
    }

    public void putCharAt(final Point point, final char character) {
        driver.putCharAt(id, point.x, point.y, character);
    }

    public void changeAttributeAt(final Point point, final int length, final CharacterColor foregroundColor,
                                  final CharacterColor backgroundColor, final CharacterAttribute[] attributes) {
        driver.changeAttributeAt(id, point.x, point.y, length, foregroundColor, backgroundColor, attributes);
    }

    public void moveCursor(final Point point) {
        driver.moveCursor(id, point.x, point.y);
    }

    public void drawVerticalLine(final char character, final int length) {
        driver.drawVerticalLine(id, character, length);
    }

    public void drawHorizontalLine(final char character, final int length) {
        driver.drawHorizontalLine(id, character, length);
    }

    public void printString(final String string) {
        driver.printString(id, string);
    }

    public void putChar(final char character) {
        driver.putChar(id, character);
    }

    public void changeAttribute(final CharacterAttribute[] attributes) {
        driver.changeAttribute(id, attributes);
    }

    public void clear() {
        driver.clear(id);
    }

    public void unDirty() {
    }

    public void clearStyle() {
        driver.clearStyle(id);
    }

    public int getCursorX() {
        return driver.getCursorX(id);
    }

    public int getCursorY() {
        return driver.getCursorY(id);
    }

    public void move(final Point point) {
        driver.moveWindow(id, point.x, point.y);
        state.move(point);
    }


    public void resize(final Dimension dimension) {
        driver.resizeWindow(id, dimension.width, dimension.height);
        state.resize(dimension);
    }

    public void fillBackground(final CellDescriptor descriptor) {
        driver.setBackground(id, descriptor);
    }

    public void changeBackground(final CellDescriptor descriptor) {
        driver.changeBackground(id, descriptor);
    }

    public void drawBox(final Border border) {
        driver.drawBox(id,
                       border.topLeft,
                       border.top,
                       border.topRight,
                       border.right,
                       border.bottomRight,
                       border.bottom,
                       border.bottomLeft,
                       border.left);
    }

    public void drawBoxWithoutAttributes(final Border border) {
        driver.drawBox(id,
                       border.topLeft.getCharacter(),
                       border.top.getCharacter(),
                       border.topRight.getCharacter(),
                       border.right.getCharacter(),
                       border.bottomRight.getCharacter(),
                       border.bottom.getCharacter(),
                       border.bottomLeft.getCharacter(),
                       border.left.getCharacter());
    }

    public void drawBox(final CellDescriptor topLeft,
                        final CellDescriptor top,
                        final CellDescriptor topRight,
                        final CellDescriptor right,
                        final CellDescriptor bottomRight,
                        final CellDescriptor bottom,
                        final CellDescriptor bottomLeft,
                        final CellDescriptor left) {
        driver.drawBox(id, topLeft, top, topRight, right, bottomRight, bottom, bottomLeft, left);
    }

    public void drawBox(final char topLeft,
                        final char top,
                        final char topRight,
                        final char right,
                        final char bottomRight,
                        final char bottom,
                        final char bottomLeft,
                        final char left) {
        driver.drawBox(id, topLeft, top, topRight, right, bottomRight, bottom, bottomLeft, left);
    }

    public void show() {
        state.show();
    }

    public void hide() {
        state.hide();
    }

    public void setZIndex(final int newIndex) {
        state.setZIndex(newIndex);
    }

    public Point topLeft() {
        return state.getArea().topLeft();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        CursesWindow window = (CursesWindow) o;

        return id == window.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}

