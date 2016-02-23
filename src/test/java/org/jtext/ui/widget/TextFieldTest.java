package org.jtext.ui.widget;

import org.jtext.curses.ControlKey;
import org.jtext.curses.ReadKey;
import org.jtext.ui.event.KeyPressedEvent;
import org.junit.Before;
import org.junit.Test;

public class TextFieldTest {


    private TextField field;

    @Before
    public void setUp() throws Exception {
        field = new TextField(10);
    }

    @Test
    public void shouldDeletionShouldNotThrowException() throws Exception {
        typeStringIn(field, "This is a text, which is typed in");

        for (int i = 0; i < 10; i++) {
            field.onEvent(new KeyPressedEvent(new ReadKey(ControlKey.BACKSPACE, 13)));
        }

        field.onEvent(new KeyPressedEvent(new ReadKey(ControlKey.BACKSPACE, 13)));
    }

    private void typeStringIn(final TextField textField, final String text) {
        for (int i = 0; i < text.length(); i++) {
            textField.onEvent(new KeyPressedEvent(new ReadKey(ControlKey.NORMAL, text.charAt(i))));
        }
    }
}
