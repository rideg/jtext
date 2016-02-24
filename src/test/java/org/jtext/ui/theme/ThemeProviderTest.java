package org.jtext.ui.theme;

import com.eclipsesource.json.JsonObject;
import org.hamcrest.core.Is;
import org.jtext.ui.widget.TextField;
import org.junit.Assert;
import org.junit.Test;


public class ThemeProviderTest {

    @Test
    public void shouldTextFieldWidgetTheme() throws Exception {
        ThemeProvider themeProvider = ThemeProvider.loadDefault();
        JsonObject widgetConfig = themeProvider.getWidgetConfig(TextField.class);
        Assert.assertThat(widgetConfig.get("focused.border").asString(), Is.is("single black white"));
    }
}