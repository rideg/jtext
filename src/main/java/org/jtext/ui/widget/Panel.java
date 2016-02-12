package org.jtext.ui.widget;

import org.jtext.curses.CharacterColor;
import org.jtext.ui.attribute.Border;
import org.jtext.ui.attribute.Padding;
import org.jtext.ui.event.UIEvent;
import org.jtext.ui.graphics.Container;
import org.jtext.ui.graphics.Graphics;
import org.jtext.ui.graphics.OccupationType;
import org.jtext.ui.layout.Layout;

import java.util.Optional;

public class Panel extends Container {

    private final Optional<CharacterColor> background = Optional.empty();
    private final Border border = Border.NO_BORDER;
    private final Padding padding = Padding.NO_PADDING;
    private final OccupationType preferredWith;
    private final OccupationType preferredHeight;

    public Panel(final Layout layout,
                 final OccupationType preferredWith,
                 final OccupationType preferredHeight) {
        super(layout);
        this.preferredWith = preferredWith;
        this.preferredHeight = preferredHeight;
    }


    @Override
    public void draw(Graphics graphics) {
        background.ifPresent(graphics::fillBackground);
        graphics.drawBorder(border);
        super.draw(graphics.restrict(padding.apply(border).shrink(graphics.getArea())));
    }

    @Override
    public OccupationType getPreferredWidth() {
        return preferredWith;
    }

    @Override
    public OccupationType getPreferredHeight() {
        return preferredHeight;
    }

    @Override
    protected void handleEvent(UIEvent event) {

    }
}
