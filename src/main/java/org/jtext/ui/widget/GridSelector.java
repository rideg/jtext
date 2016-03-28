package org.jtext.ui.widget;

import org.jtext.curses.ControlKey;
import org.jtext.ui.event.FocusMovedEvent;
import org.jtext.ui.event.KeyPressedEvent;
import org.jtext.ui.graphics.Container;
import org.jtext.ui.graphics.Occupation;
import org.jtext.ui.graphics.Position;
import org.jtext.ui.graphics.Scene;
import org.jtext.ui.layout.GridSelectorLayout;
import org.jtext.ui.util.KeyEventProcessor;

import java.util.List;

public class GridSelector extends Container<MenuElement> {

    private final int maxWidth;
    private final int maxHeight;

    private final GridSelectorLayout layout;
    private final KeyEventProcessor keyEventProcessor;
    private int activeIndex;

    public GridSelector(final int maxWidth, final int maxHeight, final List<MenuElement> items) {
        this(new GridSelectorLayout(maxWidth), maxWidth, maxHeight, items);
    }

    private GridSelector(final GridSelectorLayout layout, final int maxWidth, final int maxHeight,
                         final List<MenuElement> items) {
        super(layout);
        this.layout = layout;
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        this.keyEventProcessor = new KeyEventProcessor(true);
        items.stream().forEach(this::add);
        setUpEventHandling();
        addHandler(KeyPressedEvent.class, keyEventProcessor::handle);
    }

    private void setUpEventHandling() {
        keyEventProcessor.register(ControlKey.HORIZONTAL_TAB, this::moveNext);
        keyEventProcessor.register(ControlKey.SHIFT_TAB, this::movePrev);
        keyEventProcessor.register(ControlKey.DOWN, this::moveNext);
        keyEventProcessor.register(ControlKey.UP, this::movePrev);
        keyEventProcessor.register(ControlKey.RIGHT, this::moveRight);
        keyEventProcessor.register(ControlKey.LEFT, this::moveLeft);
    }

    private void moveNext() {
        activeIndex = (activeIndex + 1) % layout.getWidgets().size();
        select(activeIndex);
    }

    private void movePrev() {
        activeIndex--;
        if (activeIndex < 0) {
            activeIndex = layout.getWidgets().size() - 1;
        }
        select(activeIndex);
    }

    private void moveRight() {

    }

    private void moveLeft() {

    }

    @Override
    public Occupation getPreferredWidth() {
        return Occupation.fixed(maxWidth);
    }

    @Override
    public Occupation getPreferredHeight() {
        if (layout.getPreferredHeight().toReal(0) > maxHeight) {
            return Occupation.fixed(maxHeight);
        }
        return layout.getPreferredHeight();
    }

    @Override
    public Position getPosition() {
        return Position.RELATIVE;
    }

    public void select(final int index) {
        if (index > layout.getWidgets().size()) {
            throw new IndexOutOfBoundsException();
        }
        emit(Scene.FOCUS_MOVED_EVENT_TOPIC, new FocusMovedEvent(layout.getWidgets().get(index)));
    }
}
