package org.jtext.ui.widget;

import org.jtext.curses.CharacterColor;
import org.jtext.ui.attribute.Border;
import org.jtext.ui.event.UIEvent;
import org.jtext.ui.graphics.*;

import java.util.Optional;

public class Panel extends Container {

    private final Optional<CharacterColor> background = Optional.empty();
    private final Optional<Border> border = Optional.empty();
    private final OccupationType preferredWith;
    private final OccupationType preferredHeight;

    public Panel(final int id, final LayoutManager layoutManager,
                 final OccupationType preferredWith,
                 final OccupationType preferredHeight) {
        super(id, layoutManager);
        this.preferredWith = preferredWith;
        this.preferredHeight = preferredHeight;
    }

    @Override
    public void draw(Graphics graphics) {
        background.ifPresent(graphics::fillBackground);
        border.ifPresent(graphics::drawBorder);


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
    public Position getPosition() {
        return null;
    }

    @Override
    protected void reValidate() {

    }

    @Override
    protected void handleEvent(UIEvent event) {

    }
}
