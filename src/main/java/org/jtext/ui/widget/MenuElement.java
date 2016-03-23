package org.jtext.ui.widget;

import org.jtext.curses.Point;
import org.jtext.ui.graphics.Graphics;
import org.jtext.ui.graphics.Occupation;
import org.jtext.ui.graphics.Position;
import org.jtext.ui.graphics.Widget;

public class MenuElement extends Widget {

    private final String text;
    private boolean focused;

    public MenuElement(final String text) {
        this.text = text;
    }

    @Override
    public void draw(final Graphics graphics) {
        if (focused) {
            graphics.fillBackground(getTheme().getColor("focused.background"));
        }
        graphics.setForegroundColor(getTheme().getColor(getPrefix() + ".foreground"));
        graphics.printString(Point.at(0, 0), text);
    }

    @Override
    public Occupation getPreferredWidth() {
        return Occupation.fixed(text.length());
    }

    @Override
    public Occupation getPreferredHeight() {
        return Occupation.fixed(1);
    }

    @Override
    public Position getPosition() {
        return Position.RELATIVE;
    }

    public void gainFocus() {
        focused = true;
    }

    public void lostFocus() {
        focused = false;
    }

    public String getPrefix() {
        return focused ? "focused" : "unfocused";
    }
}
