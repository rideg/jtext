package org.jtext.ui.graphics;

import org.jtext.curses.CellDescriptor;
import org.jtext.curses.CharacterAttribute;
import org.jtext.curses.CharacterColor;
import org.jtext.curses.Driver;

public class CursesWindow {

    private final Driver driver;
    private final int windowId;

    private Rectangle area;
    private int zIndex;
    private boolean dirty;
    private boolean areaChanged;
    private boolean visible;


    public CursesWindow(final Driver driver, int windowId, Rectangle area, int zIndex, boolean visible) {
        this.driver = driver;
        this.windowId = windowId;
        this.area = area;
        this.zIndex = zIndex;
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public Point getTopLeft() {
        return area.topLeft();
    }

    public int getZIndex() {
        return zIndex;
    }

    public boolean isDirty() {
        return dirty;
    }

    public boolean isAreaChanged() {
        return areaChanged;
    }

    public void setColor(final CharacterColor foreground, final CharacterColor background) {
        driver.setColor(windowId, foreground, background);
    }

    public void setBackgroundColor(final CharacterColor color) {
        driver.setBackgroundColor(windowId, color);
    }

    public void setForegroundColor(final CharacterColor color) {
        driver.setForegroundColor(windowId, color);
    }

    public void onAttributes(final CharacterAttribute[] attributes) {
        driver.onAttributes(windowId, attributes);
    }

    public void onAttribute(final CharacterAttribute attribute) {
        driver.onAttribute(windowId, attribute);
    }

    public void offAttribute(final CharacterAttribute attribute) {
        driver.offAttribute(windowId, attribute);
    }

    public void drawHorizontalLineAt(final Point point, char character, int length) {
        driver.drawHorizontalLineAt(windowId, point.x, point.y, character, length);
        dirty();
    }

    public void drawVerticalLineAt(final Point point, char character, int length) {
        driver.drawVerticalLineAt(windowId, point.x, point.y, character, length);
        dirty();
    }

    public void printStringAt(final Point point, final String string) {
        driver.printStringAt(windowId, point.x, point.y, string);
        dirty();
    }

    public void putCharAt(final Point point, final char character) {
        driver.putCharAt(windowId, point.x, point.y, character);
        dirty();
    }

    public void changeAttributeAt(final Point point, final int length, final CharacterColor foregroundColor,
                                  final CharacterColor backgroundColor, final CharacterAttribute[] attributes) {
        driver.changeAttributeAt(windowId, point.x, point.y, length, foregroundColor, backgroundColor, attributes);
        dirty();
    }

    public void moveCursor(final Point point) {
        driver.moveCursor(windowId, point.x, point.y);
    }

    public void drawVerticalLine(final char character, final int length) {
        driver.drawVerticalLine(windowId, character, length);
        dirty();
    }

    public void drawHorizontalLine(final char character, final int length) {
        driver.drawHorizontalLine(windowId, character, length);
        dirty();
    }

    public void printString(final String string) {
        driver.printString(windowId, string);
        dirty();
    }

    public void putChar(final char character) {
        driver.putChar(windowId, character);
        dirty();
    }

    public void changeAttribute(final CharacterAttribute[] attributes) {
        driver.changeAttribute(windowId, attributes);
    }

    public void clear() {
        driver.clear(windowId);
        dirty();
    }

    public void refresh() {
        driver.refresh(windowId);
        dirty = false;
    }


    public void clearStyle() {
        driver.clearStyle(windowId);
    }

    public int getCursorX() {
        return driver.getCursorX(windowId);
    }

    public int getCursorY() {
        return driver.getCursorY(windowId);
    }

    public void move(final Point point) {
        driver.moveWindow(windowId, point.x, point.y);
        area = area.move(point);
        areaChanged();
    }

    public void resize(final int width, final int height) {
        driver.resizeWindow(windowId, width, height);
        area = area.resize(width, height);
        dirty();
        areaChanged();
    }

    public void setBackground(final CellDescriptor descriptor) {
        driver.setBackground(windowId, descriptor);
        dirty();
    }

    public void changeBackground(final CellDescriptor descriptor) {
        driver.changeBackground(windowId, descriptor);
        dirty();
    }

    public void drawBox(final CellDescriptor topLeft,
                        final CellDescriptor top,
                        final CellDescriptor topRight,
                        final CellDescriptor right,
                        final CellDescriptor bottomRight,
                        final CellDescriptor bottom,
                        final CellDescriptor bottomLeft,
                        final CellDescriptor left) {
        driver.drawBox(windowId, topLeft, top, topRight, right, bottomRight, bottom, bottomLeft, left);
        dirty();
    }

    public void drawBox(final char topLeft,
                        final char top,
                        final char topRight,
                        final char right,
                        final char bottomRight,
                        final char bottom,
                        final char bottomLeft,
                        final char left) {
        driver.drawBox(windowId, topLeft, top, topRight, right, bottomRight, bottom, bottomLeft, left);
        dirty();
    }

    public void show() {
        if (!visible) dirty();
        visible = true;
    }

    public void hide() {
        dirty = false;
        areaChanged();
        visible = false;
    }

    public void setZIndex(final int newIndex) {
        if (newIndex > zIndex) dirty();
        zIndex = newIndex;
    }

    private void areaChanged() {
        areaChanged = true;
    }

    private void dirty() {
        if (visible) {
            dirty = true;
        }
    }
}

