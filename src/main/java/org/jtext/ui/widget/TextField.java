package org.jtext.ui.widget;

import org.jtext.curses.CellDescriptor;
import org.jtext.curses.CharacterAttribute;
import org.jtext.curses.CharacterColor;
import org.jtext.curses.ControlKey;
import org.jtext.curses.ReadKey;
import org.jtext.ui.attribute.Border;
import org.jtext.ui.attribute.Padding;
import org.jtext.ui.event.GainFocusEvent;
import org.jtext.ui.event.KeyPressedEvent;
import org.jtext.ui.event.LostFocusEvent;
import org.jtext.ui.graphics.Graphics;
import org.jtext.ui.graphics.Occupation;
import org.jtext.ui.graphics.Point;
import org.jtext.ui.graphics.Position;
import org.jtext.ui.graphics.Widget;
import org.jtext.ui.util.KeyEventProcessor;

public class TextField extends Widget {

    private final KeyEventProcessor keyEventProcessor;
    private boolean focused;
    private StringBuilder text;
    private int width;
    private int cursor;
    private int clip;
    private Border border;
    private Padding padding;
    private CharacterColor background;
    private CharacterColor foreground;
    private CharacterColor unfocusedBackground;
    private CharacterColor unfocusedForeground;
    private CellDescriptor borderColor;
    private CellDescriptor unfocusedBorderColor;

    public TextField(final int width, final Border border, final Padding padding, final CharacterColor background,
                     final CharacterColor foreground, final CharacterColor unfocusedBackground,
                     final CharacterColor unfocusedForeground, final CellDescriptor borderColor,
                     final CellDescriptor unfocusedBorderColor) {
        text = new StringBuilder();
        this.border = border;
        this.padding = padding;
        this.background = background;
        this.foreground = foreground;
        this.unfocusedBackground = unfocusedBackground;
        this.unfocusedForeground = unfocusedForeground;
        this.width = width;
        this.borderColor = borderColor;
        this.unfocusedBorderColor = unfocusedBorderColor;
        this.keyEventProcessor = new KeyEventProcessor();

        registerKeyHandlers();

        addHandler(GainFocusEvent.class, this::onGainedFocus);
        addHandler(LostFocusEvent.class, this::onLostFocus);
        addHandler(KeyPressedEvent.class, this::onKeyPressed);

    }

    private void onLostFocus(final LostFocusEvent event) {
        focused = false;
        event.stopPropagating();
    }

    private void onGainedFocus(final GainFocusEvent event) {
        focused = true;
        event.stopPropagating();
    }

    private void registerKeyHandlers() {
        keyEventProcessor.register(ControlKey.NORMAL, this::typeIn);
        keyEventProcessor.register(ControlKey.BACKSPACE, this::deletePreviousChar);
        keyEventProcessor.register(ControlKey.DELETE, this::deleteChar);
        keyEventProcessor.register(ControlKey.LEFT, this::moveCursorLeft);
        keyEventProcessor.register(ControlKey.RIGHT, this::moveCursorRight);
        keyEventProcessor.register(ControlKey.HOME, this::jumpHome);
        keyEventProcessor.register(ControlKey.END, this::jumpEnd);
    }

    private void typeIn(final ReadKey e) {
        text.insert(clip + cursor, (char) e.value);
        if (cursor == width - 1) {
            clip++;
        } else {
            cursor++;
        }
    }

    private void jumpEnd() {
        if (text.length() >= width) {
            cursor = width - 1;
            clip = text.length() - cursor;
        } else {
            clip = 0;
            cursor = text.length();
        }
    }

    private void jumpHome() {
        clip = 0;
        cursor = 0;
    }

    private void moveCursorRight() {
        if (cursor < width - 1 && clip + cursor < text.length()) {
            cursor++;
        } else if (clip + cursor < text.length()) {
            clip++;
        }
    }

    private void moveCursorLeft() {
        if (cursor > 0) {
            cursor--;
        } else if (clip > 0) {
            clip--;
        }
    }

    private void deletePreviousChar() {
        if (text.length() > 0 && clip + cursor > 0) {
            text.deleteCharAt(clip + cursor - 1);
            moveCursorLeft();
        }
    }

    private void deleteChar() {
        if (text.length() > 0 && clip + cursor < text.length()) {
            text.deleteCharAt(clip + cursor);
        }
    }

    private void onKeyPressed(final KeyPressedEvent event) {
        if (keyEventProcessor.handle(event)) {
            event.stopBubbling();
        }
    }

    @Override
    public void draw(final Graphics graphics) {
        final CharacterColor back;
        final CharacterColor fore;
        final CellDescriptor borderC;
        if (focused) {
            back = background;
            fore = foreground;
            borderC = borderColor;
        } else {
            back = unfocusedBackground;
            fore = unfocusedForeground;
            borderC = unfocusedBorderColor;
        }
        graphics.fillBackground(back);
        graphics.drawBorder(border.changeCell(borderC));

        graphics.setBackgroundColor(back);
        graphics.setForegroundColor(fore);

        final Point startPoint = Point.at(padding.left + border.getLeftThickness(), border.getTopThickness());
        if (clip > 0) {
            graphics.putChar(startPoint.decX(), '…');

        }
        final String toWrite = text.substring(clip, clip + Math.max(Math.min(text.length() - clip, width), 0));
        graphics.printString(startPoint, toWrite);

        if (text.length() - clip >= width) {
            graphics.putChar(startPoint.shiftX(width), '…');
        }
        graphics.changeAttributeAt(startPoint.shiftX(cursor), 1,
                                   CellDescriptor.of(back, fore, CharacterAttribute.REVERSE));
    }

    @Override
    public Occupation getPreferredWidth() {
        return Occupation.fixed(width +
                                border.getLeftThickness() +
                                border.getRightThickness() +
                                padding.left +
                                padding.right);
    }

    @Override
    public Occupation getPreferredHeight() {
        return Occupation.fixed(1 + border.getBottomThickness() + border.getTopThickness());
    }

    @Override
    public Position getPosition() {
        return Position.RELATIVE;
    }

    public boolean isFocused() {
        return focused;
    }
}
