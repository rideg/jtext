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

import static java.lang.Math.max;
import static java.lang.Math.min;

public class TextField extends Widget {

    private final KeyEventProcessor keyEventProcessor;
    private boolean focused;
    private StringBuilder text;
    private int width;
    private int cursor;
    private int clip;
    private Selection selection;
    private Border border;
    private Padding padding;
    private CharacterColor backgroundColor;
    private CharacterColor foregroundColor;
    private CharacterColor unfocusedBackground;
    private CharacterColor unfocusedForeground;
    private CellDescriptor borderColor;
    private CellDescriptor unfocusedBorderColor;

    public TextField(final int width, final Border border, final Padding padding, final CharacterColor backgroundColor,
                     final CharacterColor foregroundColor, final CharacterColor unfocusedBackground,
                     final CharacterColor unfocusedForeground, final CellDescriptor borderColor,
                     final CellDescriptor unfocusedBorderColor) {
        text = new StringBuilder();
        this.border = border;
        this.padding = padding;
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
        this.unfocusedBackground = unfocusedBackground;
        this.unfocusedForeground = unfocusedForeground;
        this.width = width;
        this.borderColor = borderColor;
        this.unfocusedBorderColor = unfocusedBorderColor;
        this.keyEventProcessor = new KeyEventProcessor(true);
        this.selection = Selection.NONE;

        registerKeyHandlers();

        addHandler(GainFocusEvent.class, this::onGainedFocus);
        addHandler(LostFocusEvent.class, this::onLostFocus);
        addHandler(KeyPressedEvent.class, keyEventProcessor::handle);

    }

    public String getText() {
        return text.toString();
    }

    String getVisibleText() {
        return text.substring(clip, clip + max(min(text.length() - clip, width), 0));
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
        keyEventProcessor.register(ControlKey.SHIFT_LEFT, this::leftSelect);
        keyEventProcessor.register(ControlKey.SHIFT_RIGHT, this::rightSelect);
    }

    public void leftSelect() {
        if (cursor > 0) {
            if (selection != Selection.NONE) {
                selection = selection.decrement();
            } else {
                selection = Selection.of(Math.min(cursor, text.length() - 1), cursor - 1);
            }
            moveCursor(cursor - 1);
        }
    }

    public void rightSelect() {
        if (cursor < text.length()) {
            if (cursor < text.length() - 1) {
                if (selection != Selection.NONE) {
                    selection = selection.increment();
                } else {
                    selection = Selection.of(cursor, cursor + 1);
                }
            }
            moveCursor(cursor + 1);
        }
    }

    private void typeIn(final ReadKey e) {
        if (selectionExists()) {
            deleteSelection();
        }
        text.insert(cursor, (char) e.value);
        moveCursor(cursor + 1);
    }

    private boolean selectionExists() {
        return selection != Selection.NONE;
    }

    private void deleteSelection() {
        moveCursor(selection.getStart());
        text.delete(selection.getStart(), selection.getEnd() + 1);
        selection = Selection.NONE;
    }

    private void jumpEnd() {
        moveCursor(text.length());
    }

    private void jumpHome() {
        moveCursor(0);
    }

    private void moveCursorRight() {
        if (selectionExists()) {
            if (selection.isRightToLeft()) {
                moveCursor(selection.getEnd());
            }
            selection = Selection.NONE;
        } else {
            moveCursor(cursor + 1);
        }
    }

    private void moveCursorLeft() {
        if (selectionExists()) {
            if (selection.isLeftToRight()) {
                moveCursor(selection.getStart());
            }
            selection = Selection.NONE;
        } else {
            moveCursor(cursor - 1);
        }
    }

    private void deletePreviousChar() {
        if (selectionExists()) {
            deleteSelection();
        } else {
            if (text.length() > 0 && cursor > 0) {
                text.deleteCharAt(cursor - 1);
                moveCursor(cursor - 1);
            }
        }
    }

    private void deleteChar() {
        if (selectionExists()) {
            deleteSelection();
        } else {
            if (text.length() > 0 && cursor < text.length()) {
                text.deleteCharAt(cursor);
            }
        }
    }

    private void moveCursor(int position) {
        if (position >= 0 && position <= text.length()) {
            cursor = position;
            if (position < clip) {
                clip = position;
            } else if (position >= clip + width) {
                clip = position - width + 1;
            }
        }
    }

    @Override
    public void draw(final Graphics graphics) {
        final CharacterColor background = getBackgroundColor();
        final CharacterColor foreground = getForegroundColor();
        final CellDescriptor border = getBorderColor();
        graphics.fillBackground(background);
        graphics.drawBorder(this.border.changeCell(border));

        graphics.setBackgroundColor(background);
        graphics.setForegroundColor(foreground);

        final Point startPoint = Point.at(padding.left + this.border.getLeftThickness(), this.border.getTopThickness());
        if (clip > 0) {
            graphics.putChar(startPoint.decX(), '…');
        }
        graphics.setAttribute(CharacterAttribute.ITALIC);
        graphics.printString(startPoint, getVisibleText());
        graphics.removeAttribute(CharacterAttribute.ITALIC);

        if (text.length() - clip >= width) {
            graphics.putChar(startPoint.shiftX(width), '…');
        }

        if (selection != Selection.NONE) {
            graphics.changeAttributeAt(startPoint.shiftX(max(selection.getStart() - clip, 0)),
                    min(selection.length() + 1 - max(clip - selection.getStart(), 0), width),
                    CellDescriptor.of(foreground, background));
        } else {
            graphics.changeAttributeAt(startPoint.shiftX(cursor - clip), 1,
                    CellDescriptor.of(foreground, background));
        }
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

    public CharacterColor getBackgroundColor() {
        return focused ? backgroundColor : unfocusedBackground;
    }

    public CharacterColor getForegroundColor() {
        return focused ? foregroundColor : unfocusedForeground;
    }

    public CellDescriptor getBorderColor() {
        return focused ? borderColor : unfocusedBorderColor;
    }
}
