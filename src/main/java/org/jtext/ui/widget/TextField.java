package org.jtext.ui.widget;

import org.jtext.curses.CellDescriptor;
import org.jtext.curses.ColorName;
import org.jtext.curses.ControlKey;
import org.jtext.curses.Point;
import org.jtext.curses.ReadKey;
import org.jtext.ui.attribute.Border;
import org.jtext.ui.attribute.Padding;
import org.jtext.ui.event.GainFocusEvent;
import org.jtext.ui.event.KeyPressedEvent;
import org.jtext.ui.event.LostFocusEvent;
import org.jtext.ui.graphics.Graphics;
import org.jtext.ui.graphics.Occupation;
import org.jtext.ui.graphics.Position;
import org.jtext.ui.graphics.Widget;
import org.jtext.ui.model.DocumentModel;
import org.jtext.ui.model.TextModel;
import org.jtext.ui.util.KeyEventProcessor;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class TextField extends Widget {

    private final KeyEventProcessor keyEventProcessor;
    private final DocumentModel model;
    private boolean focused;
    private int width;
    private int cursor;
    private int clip;
    private Selection selection;

    public TextField(final int width) {
        this(new TextModel(), width);
    }

    public TextField(final DocumentModel model, final int width) {
        this.model = model;
        this.width = width;
        this.keyEventProcessor = new KeyEventProcessor(true);
        this.selection = Selection.NONE;
        registerKeyHandlers();

        addHandler(GainFocusEvent.class, this::onGainedFocus);
        addHandler(LostFocusEvent.class, this::onLostFocus);
        addHandler(KeyPressedEvent.class, this::onKeyPressed);

    }

    private String getVisibleText() {
        return model.getChars(clip, clip + max(min(model.length() - clip, width), 0));
    }

    private void onLostFocus(final LostFocusEvent event) {
        focused = false;
        event.stopPropagating();
        requestRepaint();
    }

    private void onGainedFocus(final GainFocusEvent event) {
        focused = true;
        event.stopPropagating();
        requestRepaint();
    }

    private void onKeyPressed(final KeyPressedEvent event) {
        if (keyEventProcessor.handle(event)) {
            requestRepaint();
        }
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
                selection = Selection.of(Math.min(cursor, model.length() - 1), cursor - 1);
            }
            moveCursor(cursor - 1);
        }
    }

    public void rightSelect() {
        if (cursor < model.length()) {
            if (cursor < model.length() - 1) {
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
        model.insertCharAt(cursor, (char) e.value);
        moveCursor(cursor + 1);
    }

    private boolean selectionExists() {
        return selection != Selection.NONE;
    }

    private void deleteSelection() {
        moveCursor(selection.getStart());
        model.deleteRegion(selection.getStart(), selection.getEnd() + 1);
        selection = Selection.NONE;
    }

    private void jumpEnd() {
        moveCursor(model.length());
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
            if (model.length() > 0 && cursor > 0) {
                model.deleteCharAt(cursor - 1);
                moveCursor(cursor - 1);
            }
        }
    }

    private void deleteChar() {
        if (selectionExists()) {
            deleteSelection();
        } else {
            if (model.length() > 0 && cursor < model.length()) {
                model.deleteCharAt(cursor);
                if (model.length() < clip + width && clip > 0) {
                    clip--;
                }
            }
        }
    }

    private void moveCursor(int position) {
        if (position >= 0 && position <= model.length()) {
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
        final Border border = getBorder();
        final ColorName background = getBackgroundColor();
        final ColorName foreground = getForegroundColor();

        graphics.fillBackground(background);
        graphics.drawBorder(border);
        graphics.setBackgroundColor(background);
        graphics.setForegroundColor(foreground);

        final Padding padding = getTheme().getPadding("padding");

        final Point startPoint = Point.at(padding.left + border.getLeftThickness(), border.getTopThickness());
        if (clip > 0) {
            graphics.putChar(startPoint.decX(), '…');
        }

        graphics.printString(startPoint, getVisibleText());

        if (model.length() - clip > width) {
            graphics.putChar(startPoint.shiftX(width), '…');
        }

        final CellDescriptor highlightColor = getHighlightDescriptor();

        if (selection != Selection.NONE) {
            graphics.changeAttributeAt(startPoint.shiftX(max(selection.getStart() - clip, 0)),
                    min(selection.length() + 1 - max(clip - selection.getStart(), 0), width),
                    highlightColor);
        } else {
            graphics.changeAttributeAt(startPoint.shiftX(cursor - clip), 1, highlightColor);
        }
    }

    private CellDescriptor getHighlightDescriptor() {
        return CellDescriptor.of(getTheme().getColor(getPrefix() + ".highlight.background"),
                getTheme().getColor(getPrefix() + ".highlight.foreground"));
    }

    private Border getBorder() {
        return getTheme().getBorder(getPrefix() + ".border");
    }

    @Override
    public Occupation getPreferredWidth() {
        final Border border = getBorder();
        final Padding padding = getTheme().getPadding("padding");
        return Occupation.fixed(width +
                border.getLeftThickness() +
                border.getRightThickness() +
                padding.left +
                padding.right);
    }

    @Override
    public Occupation getPreferredHeight() {
        final Border border = getTheme().getBorder(getPrefix() + ".border");
        return Occupation.fixed(1 + border.getBottomThickness() + border.getTopThickness());
    }

    @Override
    public Position getPosition() {
        return Position.RELATIVE;
    }

    public boolean isFocused() {
        return focused;
    }

    public String getPrefix() {
        return isFocused() ? "focused" : "unfocused";
    }

    public ColorName getBackgroundColor() {
        return getTheme().getColor(getPrefix() + ".background");
    }

    public ColorName getForegroundColor() {
        return getTheme().getColor(getPrefix() + ".foreground");
    }
}
