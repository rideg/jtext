package org.jtext.ui.widget;

import org.jtext.curses.ColorName;
import org.jtext.curses.ControlKey;
import org.jtext.ui.event.FocusMovedEvent;
import org.jtext.ui.event.GainFocusEvent;
import org.jtext.ui.event.KeyPressedEvent;
import org.jtext.ui.graphics.Container;
import org.jtext.ui.graphics.Occupation;
import org.jtext.ui.graphics.Position;
import org.jtext.ui.graphics.Scene;
import org.jtext.ui.layout.GridSelectorLayout;
import org.jtext.ui.util.KeyEventProcessor;

import java.util.List;
import java.util.Optional;

public class GridSelector extends Container<MenuElement> implements WidgetWithBackground {

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
        addHandler(GainFocusEvent.class, this::onFocusGained);
    }

    private void onFocusGained(final GainFocusEvent event) {
        select(activeIndex);
    }

    private void setUpEventHandling() {
        keyEventProcessor.register(ControlKey.HORIZONTAL_TAB, this::moveNext);
        keyEventProcessor.register(ControlKey.SHIFT_TAB, this::movePrev);
        keyEventProcessor.register(ControlKey.DOWN, this::moveNext);
        keyEventProcessor.register(ControlKey.UP, this::movePrev);
        keyEventProcessor.register(ControlKey.RIGHT, this::moveRight);
        keyEventProcessor.register(ControlKey.LEFT, this::moveLeft);
        keyEventProcessor.register(ControlKey.HOME, this::moveHome);
        keyEventProcessor.register(ControlKey.END, this::moveEnd);
    }

    private void moveNext() {
        select((activeIndex + 1) % layout.getWidgets().size());
    }

    private void movePrev() {
        select(activeIndex == 0 ? layout.getWidgets().size() - 1 : activeIndex - 1);
    }

    private void moveRight() {
        final int numberOfRows = layout.getPreferredHeight().toReal(0);
        final int numberOfColumns = layout.getWidgets().size() / numberOfRows;

        int row = activeIndex % numberOfRows;
        int column = activeIndex / numberOfRows;

        if (column == numberOfColumns - 1) {
            row = Math.min(row + 1, numberOfRows - 1);
            column = 0;
        } else {
            column++;
        }
        select(row + column * numberOfRows);
    }

    private void moveLeft() {
        final int numberOfRows = layout.getPreferredHeight().toReal(0);
        final int numberOfColumns = layout.getWidgets().size() / numberOfRows;

        int row = activeIndex % numberOfRows;
        int column = activeIndex / numberOfRows;

        if (column == 0) {
            row = Math.max(row - 1, 0);
            column = numberOfColumns - 1;
        } else {
            column--;
        }
        select(row + column * numberOfRows);
    }

    private void moveEnd() {
        final int numberOfRows = layout.getPreferredHeight().toReal(0);
        int row = activeIndex % numberOfRows;
        final int numberOfColumns = layout.getWidgets().size() / numberOfRows;
        select(row + (numberOfColumns - 1) * numberOfRows);
    }

    private void moveHome() {
        final int numberOfRows = layout.getPreferredHeight().toReal(0);
        select(activeIndex % numberOfRows);
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
        activeIndex = index;
        emit(Scene.FOCUS_MOVED_EVENT_TOPIC, new FocusMovedEvent(layout.getWidgets().get(index)));
    }

    @Override
    public Optional<ColorName> backgroundColor() {
        return Optional.of(getTheme().getColor("background"));
    }
}
