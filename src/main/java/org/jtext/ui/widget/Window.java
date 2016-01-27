package org.jtext.ui.widget;

import org.jtext.curses.CharacterAttribute;
import org.jtext.curses.CharacterColor;
import org.jtext.curses.Driver;
import org.jtext.ui.attribute.Border;
import org.jtext.ui.graphics.Point;
import org.jtext.ui.graphics.Rectangle;

import java.util.Set;

public class Window {

    private final int id;
    private final Driver driver;
    private Rectangle area;

    public Window(final int id, final Rectangle area, final Driver driver) {
        this.id = id;
        this.driver = driver;
        this.area = area;
    }

    public Point getTopLeft() {
        return area.topLeft();
    }

    public int getWidth() {
        return area.width;
    }

    public int getHeight() {
        return area.height;
    }

    public void setBackgroundColor(final CharacterColor color) {

    }

    public void setForegroundColor(final CharacterColor color) {

    }

    public void setAttributes(final Set<CharacterAttribute> attributes) {

    }

    public void putChar(final int x, final int y, final char ch) {

    }

    public void putString(final int x, final int y, final String string) {

    }

    public void drawHorizontalLine(final int x, final int y, final char ch) {

    }

    public void drawVerticalLine(final int x, final int y, final char ch) {

    }

    public void drawBorder(final Border border) {

    }

    public void clear() {

    }

    public void clearStyle() {

    }

    public void clearLine(final int y) {

    }

    public void move(final Point point) {
        area = area.move(point);
    }

    public void resize(final int width, final int height) {
        area = area.resize(width, height);
    }

    final void refresh() {

    }

}
