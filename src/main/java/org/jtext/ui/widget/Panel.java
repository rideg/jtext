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

    private final Optional<CharacterColor> background;
    private final Border border;
    private final Padding padding;
    private final OccupationType preferredWith;
    private final OccupationType preferredHeight;

    public Panel(final Layout layout,
                 final OccupationType preferredWith,
                 final OccupationType preferredHeight) {
        this(layout, preferredWith, preferredHeight, Border.NO_BORDER, Padding.NO_PADDING, Optional.empty());

    }

    private Panel(final Layout layout,
                  final OccupationType preferredWith,
                  final OccupationType preferredHeight,
                  final Border border,
                  final Padding padding,
                  final Optional<CharacterColor> backgroundColor
    ) {
        super(layout);
        this.preferredWith = preferredWith;
        this.preferredHeight = preferredHeight;
        this.border = border;
        this.padding = padding;
        this.background = backgroundColor;

    }

    public Panel(final Layout layout,
                 final OccupationType preferredWith,
                 final OccupationType preferredHeight,
                 final Border border,
                 final Padding padding,
                 final CharacterColor backgroundColor) {
        this(layout, preferredWith, preferredHeight, border, padding, Optional.of(backgroundColor));
    }


    @Override
    public void draw(Graphics graphics) {
        background.ifPresent(graphics::fillBackground);
        graphics.drawBorder(border);
        super.draw(graphics.restrict(padding.consider(border).apply(graphics.area)));
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
