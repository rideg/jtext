package org.jtext.ui.widget;

import org.jtext.curses.CharacterColor;
import org.jtext.ui.attribute.Border;
import org.jtext.ui.attribute.Padding;
import org.jtext.ui.event.UIEvent;
import org.jtext.ui.graphics.Container;
import org.jtext.ui.graphics.Graphics;
import org.jtext.ui.graphics.LayoutManager;
import org.jtext.ui.graphics.OccupationType;

import java.util.Optional;

public class Panel extends Container {

    private final Optional<CharacterColor> background = Optional.empty();
    private final Optional<Border> border = Optional.empty();
    private final Optional<Padding> padding = Optional.empty();
    private final OccupationType preferredWith;
    private final OccupationType preferredHeight;

    public Panel(final LayoutManager layoutManager,
                 final OccupationType preferredWith,
                 final OccupationType preferredHeight) {
        super(layoutManager);
        this.preferredWith = preferredWith;
        this.preferredHeight = preferredHeight;
    }


    @Override
    public void draw(Graphics graphics) {
        background.ifPresent(graphics::fillBackground);
        border.ifPresent(graphics::drawBorder);
        if (padding.isPresent()) {
            graphics = new Graphics(padding.get().apply(border).shrink(graphics.getArea()), graphics);
        }
        super.draw(graphics);
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
