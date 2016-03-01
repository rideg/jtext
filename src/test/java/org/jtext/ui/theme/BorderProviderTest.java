package org.jtext.ui.theme;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.assertj.core.api.Assertions;
import org.jtext.curses.ColorName;
import org.jtext.ui.attribute.Border;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.doReturn;

public class BorderProviderTest {

    public static final JsonObject BORDERS = Json.parse("{" +
            "    \"single\": {" +
            "      \"topLeft\": \"┌\"," +
            "      \"top\": \"─\"," +
            "      \"topRight\": \"┐\"," +
            "      \"right\": \"│\"," +
            "      \"bottomRight\": \"┘\"," +
            "      \"bottom\": \"─\"," +
            "      \"bottomLeft\": \"└\"," +
            "      \"left\": \"│\"" +
            "    }," +
            "    \"double\": {" +
            "      \"topLeft\": \"╔\"," +
            "      \"top\": \"═\"," +
            "      \"topRight\": \"╗\"," +
            "      \"right\": \"║\"," +
            "      \"bottomRight\": \"╝\"," +
            "      \"bottom\": \"═\"," +
            "      \"bottomLeft\": \"╚\"," +
            "      \"left\": \"║\"" +
            "    }," +
            "    \"single.curved\": {" +
            "      \"topLeft\": \"╭\"," +
            "      \"top\": \"─\"," +
            "      \"topRight\": \"╮\"," +
            "      \"right\": \"│\"," +
            "      \"bottomRight\": \"╯\"," +
            "      \"bottom\": \"─\"," +
            "      \"bottomLeft\": \"╰\"," +
            "      \"left\": \"│\"" +
            "    }" +
            "  }").asObject();
    public static final ColorName BACKGROUND = ColorName.CONFETTI;
    public static final ColorName FOREGROUND = ColorName.AQUAMARINE;


    private ColorManager colorManager;
    private BorderProvider provider;


    @Before
    public void setUp() throws Exception {
        colorManager = Mockito.mock(ColorManager.class);
        provider = new BorderProvider(colorManager, BORDERS);
    }

    @Test
    public void shouldReturnFoundBorderWithAppropriateColoring() throws Exception {
        // given
        doReturn(BACKGROUND).when(colorManager).getColor("confetti");
        doReturn(FOREGROUND).when(colorManager).getColor("aquamarine");

        // when
        final Border border = provider.getBorder("single confetti aquamarine");

        // then
        Assertions.assertThat(border.getDescriptor())
                .isNotNull()
                .hasFieldOrPropertyWithValue("background", BACKGROUND)
                .hasFieldOrPropertyWithValue("foreground", FOREGROUND);

        Assertions.assertThat(border.topLeft).isPresent().hasValue('┌');
        Assertions.assertThat(border.top).isPresent().hasValue('─');
        Assertions.assertThat(border.topRight).isPresent().hasValue('┐');
        Assertions.assertThat(border.right).isPresent().hasValue('│');
        Assertions.assertThat(border.bottomRight).isPresent().hasValue('┘');
        Assertions.assertThat(border.bottom).isPresent().hasValue('─');
        Assertions.assertThat(border.bottomLeft).isPresent().hasValue('└');
        Assertions.assertThat(border.left).isPresent().hasValue('│');

    }

}