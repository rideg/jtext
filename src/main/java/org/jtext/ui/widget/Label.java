package org.jtext.ui.widget;

import org.jtext.curses.CellDescriptor;
import org.jtext.curses.Point;
import org.jtext.ui.graphics.Graphics;
import org.jtext.ui.graphics.Occupation;
import org.jtext.ui.graphics.Position;
import org.jtext.ui.graphics.Widget;
import org.jtext.ui.model.DocumentModel;
import org.jtext.ui.model.TextModel;

public class Label extends Widget {

    private final DocumentModel model;
    private final CellDescriptor descriptor;

    public Label(final CellDescriptor descriptor, final String text) {
        this(descriptor, new TextModel(text));
    }

    public Label(final CellDescriptor descriptor, final DocumentModel model) {
        this.model = model;
        this.descriptor = descriptor;
    }

    @Override
    public void draw(final Graphics graphics) {
        determineBackground(graphics);
        if (descriptor.hasForeground()) {
            graphics.setForegroundColor(descriptor.getForeground());
        }
        graphics.setAttributes(descriptor.getAttributes());
        graphics.printString(Point.at(0, 0), model.getChars());
    }

    private void determineBackground(final Graphics graphics) {
        if (descriptor.hasBackground()) {
            graphics.setBackgroundColor(descriptor.getBackground());
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
        return Occupation.fixed(model.length());
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
