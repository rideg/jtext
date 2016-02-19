package org.jtext.ui.widget;

import org.jtext.curses.CellDescriptor;
import org.jtext.curses.CharacterColor;
import org.jtext.curses.ControlKey;
import org.jtext.curses.ReadKey;
import org.jtext.ui.attribute.Border;
import org.jtext.ui.attribute.Padding;
import org.jtext.ui.event.GainFocusEvent;
import org.jtext.ui.event.KeyPressedEvent;
import org.junit.Test;

public class TextFieldTest {


    @Test
    public void shouldDeletionShouldNotThrowException() throws Exception {

        TextField textField =
                new TextField(10, Border.single(), Padding.horizontal(1), CharacterColor.BLUE, CharacterColor.WHITE,
                              CharacterColor.CYAN, CharacterColor.BLACK,
                              CellDescriptor.of(CharacterColor.RED, CharacterColor.GREEN),
                              CellDescriptor.of(CharacterColor.RED, CharacterColor.GREEN));

        textField.onEvent(new GainFocusEvent());
        typeStringIn(textField, "This is a text, which is typed in");

        for (int i = 0; i < 10; i++) {
            textField.onEvent(new KeyPressedEvent(new ReadKey(ControlKey.BACKSPACE, 13)));
        }

        textField.onEvent(new KeyPressedEvent(new ReadKey(ControlKey.BACKSPACE, 13)));

    }

    private void typeStringIn(final TextField textField, final String text) {
        for (int i = 0; i < text.length(); i++) {
            textField.onEvent(new KeyPressedEvent(new ReadKey(ControlKey.NORMAL, text.charAt(i))));
        }
    }
}
