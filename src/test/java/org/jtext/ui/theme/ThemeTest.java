package org.jtext.ui.theme;

import com.eclipsesource.json.JsonObject;
import org.hamcrest.core.Is;
import org.jtext.ui.widget.TextField;
import org.junit.Assert;
import org.junit.Test;


public class ThemeTest {


    @Test
    public void shouldTextFieldWidgetTheme() throws Exception {
        Theme theme = Theme.loadDefault();
        JsonObject widgetConfig = theme.getWidgetConfig(TextField.class);
        Assert.assertThat(widgetConfig.get("border").asString(), Is.is("single black white"));
    }
}