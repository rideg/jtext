package org.jtext.ui.widget;

import org.jtext.curses.ColorName;
import org.jtext.curses.Point;
import org.jtext.ui.event.GainFocusEvent;
import org.jtext.ui.event.LostFocusEvent;
import org.jtext.ui.graphics.Graphics;
import org.jtext.ui.graphics.Occupation;
import org.jtext.ui.graphics.Position;
import org.jtext.ui.graphics.Widget;

import java.util.Arrays;
import java.util.Optional;

public class MenuElement extends Widget {

    private final String text;
    private int focusedWidth;
    private boolean focused;

    public MenuElement(final String text) {
        this.text = text;
        focusedWidth = text.length();
        addHandler(GainFocusEvent.class, this::gainFocus);
        addHandler(LostFocusEvent.class, this::lostFocus);
    }

    @Override
    public void draw(final Graphics graphics) {
        if (focused) {
            graphics.fillBackground(getTheme().getColor("focused.background"));
        } else {
            determineBackground().ifPresent(graphics::fillBackground);
        }
        graphics.setForegroundColor(getTheme().getColor(getPrefix() + ".foreground"));
        graphics.printString(Point.zero(), text + getSpaces());
    }

    private Optional<ColorName> determineBackground() {
        if (getParent().isPresent()) {
            final Widget parent = getParent().orElse(null);
            if (parent instanceof WidgetWithBackground) {
                return ((WidgetWithBackground) parent).backgroundColor();
            }
        }
        return Optional.empty();
    }

    private void gainFocus(final GainFocusEvent event) {
        this.focused = true;
        event.stopPropagating();
        requestRepaint();
    }

    private void lostFocus(final LostFocusEvent event) {
        this.focused = false;
        event.stopPropagating();
        requestRepaint();
    }

    @Override
    public Occupation getPreferredWidth() {
        return Occupation.fixed(focused ? focusedWidth : text.length());
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

    public void setFocusedWidth(int width) {
        focusedWidth = width > text.length() ? width : text.length();
    }

    public String getSpaces() {
        if (focused && focusedWidth > text.length()) {
            char[] chars = new char[focusedWidth - text.length()];
            Arrays.fill(chars, ' ');
            return new String(chars);
        }
        return "";
    }
}
