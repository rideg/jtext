package org.jtext.ui.widget;

import org.hamcrest.core.Is;
import org.jtext.curses.ColorName;
import org.jtext.curses.ControlKey;
import org.jtext.curses.ReadKey;
import org.jtext.ui.attribute.Border;
import org.jtext.ui.attribute.Padding;
import org.jtext.ui.event.KeyPressedEvent;
import org.jtext.ui.graphics.Graphics;
import org.jtext.ui.model.TextModel;
import org.jtext.ui.theme.Theme;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TextFieldTest {

    private TextField field;
    private TextModel model;
    private Theme theme;

    @Before
    public void setUp() throws Exception {
        theme = mock(Theme.class);

        model = new TextModel();
        field = new TextField(model, 10);

        field.setTheme(theme);
    }

    @Test
    public void deleteShouldNotThrowException() throws Exception {
        typeStringIn(field, "This is a text, which is typed in");

        for (int i = 0; i < 10; i++) {
            field.onEvent(new KeyPressedEvent(new ReadKey(ControlKey.BACKSPACE, 13)));
        }

        field.onEvent(new KeyPressedEvent(new ReadKey(ControlKey.BACKSPACE, 13)));
    }


    @Test
    public void shouldSetCursorPositionIfTheUnderlyingModelChanges() throws Exception {

        when(theme.getBorder(anyString())).thenReturn(Border.no());
        when(theme.getColor(anyString())).thenReturn(ColorName.BLACK);
        when(theme.getPadding(anyString())).thenReturn(Padding.no());

        typeStringIn(field, "This is a text, which is typed in");
        model.setText("test");

        field.draw(mock(Graphics.class));

        Assert.assertThat(field.getCursor(), Is.is(model.length()));
    }

    private void typeStringIn(final TextField textField, final String text) {
        for (int i = 0; i < text.length(); i++) {
            textField.onEvent(new KeyPressedEvent(new ReadKey(ControlKey.NORMAL, text.charAt(i))));
        }
    }
}
