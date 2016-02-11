package org.jtext.ui.widget;

import org.jtext.curses.CellDescriptor;
import org.jtext.ui.graphics.*;

import java.util.Arrays;
import java.util.HashSet;

public class Label extends Widget {

    private final CellDescriptor descriptor;
    private final String text;

    public Label(final CellDescriptor descriptor, final String text) {
        this.descriptor = descriptor;
        this.text = text;
    }

    @Override
    public void draw(final Graphics graphics) {
        graphics.setBackgroundColor(descriptor.getBackgroundColor());
        graphics.setForegroundColor(descriptor.getForegroundColor());
        graphics.setAttributes(new HashSet<>(Arrays.asList(descriptor.getAttributes())));
        graphics.printString(Point.at(0, 0), text);
    }

    @Override
    public OccupationType getPreferredWidth() {
        return OccupationType.fixed(text.length());
    }

    @Override
    public OccupationType getPreferredHeight() {
        return OccupationType.fixed(1);
    }

    @Override
    public Position getPosition() {
        return Position.RELATIVE;
    }

}
