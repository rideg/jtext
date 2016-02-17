package org.jtext.ui.widget;

import org.jtext.curses.CharacterColor;
import org.jtext.ui.attribute.Border;
import org.jtext.ui.attribute.Padding;
import org.jtext.ui.event.UIEvent;
import org.jtext.ui.graphics.Container;
import org.jtext.ui.graphics.Graphics;
import org.jtext.ui.graphics.Occupation;
import org.jtext.ui.graphics.Rectangle;
import org.jtext.ui.layout.Layout;

import java.util.Optional;

public class Panel extends Container {

    private final Optional<CharacterColor> background;
    private final Border border;
    private final Padding padding;
    private final Occupation preferredWith;
    private final Occupation preferredHeight;

    public Panel(final Layout layout,
                 final Occupation preferredWith,
                 final Occupation preferredHeight) {
        this(layout, preferredWith, preferredHeight, Border.no(), Padding.no(), Optional.empty());

    }

    private Panel(final Layout layout,
                  final Occupation preferredWith,
                  final Occupation preferredHeight,
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
                 final Occupation preferredWith,
                 final Occupation preferredHeight,
                 final Border border,
                 final Padding padding,
                 final CharacterColor backgroundColor) {
        this(layout, preferredWith, preferredHeight, border, padding, Optional.of(backgroundColor));
    }

    public Panel(final Layout layout,
                 final Occupation preferredWidth,
                 final Occupation preferredHeight,
                 final Border border,
                 final Padding padding) {
        this(layout, preferredWidth, preferredHeight, border, padding, Optional.empty());
    }


    @Override
    public void draw(Graphics graphics) {
        background.ifPresent(graphics::fillBackground);
        graphics.drawBorder(border);
        super.draw(graphics.restrict(padding.consider(border).apply(graphics.area.dimension())));
    }

    @Override
    public Occupation getPreferredWidth() {
        return preferredWith;
    }

    @Override
    public Occupation getPreferredHeight() {
        return preferredHeight;
    }

    @Override
    protected void handleEvent(UIEvent event) {

    }

    @Override
    public void setArea(Rectangle area) {
        super.setArea(padding.consider(border).apply(area));
    }
}
