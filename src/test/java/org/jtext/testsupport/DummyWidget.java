package org.jtext.testsupport;

import org.jtext.ui.attribute.Margin;
import org.jtext.ui.graphics.Graphics;
import org.jtext.ui.graphics.Occupation;
import org.jtext.ui.graphics.Position;
import org.jtext.ui.graphics.Widget;

public class DummyWidget extends Widget {

    private Occupation preferredWidth;
    private Occupation preferredHeight;
    private Occupation minWidth;
    private Occupation minHeight;
    private Occupation maxWidth;
    private Occupation maxHeight;
    private Position position;

    private Graphics graphics;

    public DummyWidget(final Occupation preferredWidth, final Occupation preferredHeight, final Occupation minWidth,
                       final Occupation minHeight, final Occupation maxWidth, final Occupation maxHeight,
                       final Position position, final boolean visible) {
        this.preferredWidth = preferredWidth;
        this.preferredHeight = preferredHeight;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.position = position;
        if (!visible) hide();
    }

    public DummyWidget(final Occupation preferredWidth, final Occupation preferredHeight, final Occupation minWidth,
                       final Occupation minHeight, final Occupation maxWidth, final Occupation maxHeight,
                       final Margin margin, final Position position) {
        this(preferredWidth, preferredHeight, minWidth, minHeight, maxWidth, maxHeight, position, true);
        setMargin(margin);
    }

    @Override
    public void draw(final Graphics graphics) {
        this.graphics = graphics;
    }

    @Override
    public Occupation getPreferredWidth() {
        return preferredWidth;
    }

    public void setPreferredWidth(final Occupation preferredWidth) {
        this.preferredWidth = preferredWidth;
    }

    @Override
    public Occupation getPreferredHeight() {
        return preferredHeight;
    }

    public void setPreferredHeight(final Occupation preferredHeight) {
        this.preferredHeight = preferredHeight;
    }

    @Override
    public Occupation getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(final Occupation minWidth) {
        this.minWidth = minWidth;
    }

    @Override
    public Occupation getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(final Occupation minHeight) {
        this.minHeight = minHeight;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public void setPosition(final Position position) {
        this.position = position;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public Occupation getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(final Occupation maxWidth) {
        this.maxWidth = maxWidth;
    }

    @Override
    public Occupation getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(final Occupation maxHeight) {
        this.maxHeight = maxHeight;
    }
}
