package org.jtext.ui.widget;

import org.jtext.curses.CellDescriptor;
import org.jtext.ui.graphics.Graphics;
import org.jtext.ui.graphics.Occupation;
import org.jtext.ui.graphics.Point;
import org.jtext.ui.graphics.Position;
import org.jtext.ui.graphics.Widget;

public class Label extends Widget {

    private final CellDescriptor descriptor;
    private final String text;

    public Label(final CellDescriptor descriptor, final String text) {
        this.descriptor = descriptor;
        this.text = text;
    }

    @Override
    public void draw(final Graphics graphics) {
        determineBackground(graphics);
        descriptor.foreground.ifPresent(graphics::setForegroundColor);
        graphics.setAttributes(descriptor.attributes);
        graphics.printString(Point.at(0, 0), text);
    }

    private void determineBackground(final Graphics graphics) {
        if (descriptor.background.isPresent()) {
            graphics.setBackgroundColor(descriptor.background.get());
        } else {
            getParent().ifPresent(parent -> {
                if (parent instanceof WidgetWithBackground) {
                    ((WidgetWithBackground) parent).backgroundColor().ifPresent(graphics::setBackgroundColor);
                }
            });
        }
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
    public Occupation getMinWidth() {
        return Occupation.fixed(3);
    }

    @Override
    public Occupation getMinHeight() {
        return Occupation.fixed(1);
    }

    @Override
    public Position getPosition() {
        return Position.RELATIVE;
    }

}
