package org.jtext.ui.theme;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.assertj.core.api.Assertions;
import org.hamcrest.core.Is;
import org.jtext.curses.CellDescriptor;
import org.jtext.curses.Color;
import org.jtext.curses.RgbValue;
import org.jtext.ui.attribute.Border;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

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


    public static final Color BACKGROUND = new Color(1, "color1", RgbValue.of(34, 55, 125));
    public static final Color FOREGROUND = new Color(2, "color2", RgbValue.of(221, 44, 199));
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
        doReturn(BACKGROUND).when(colorManager).getColor(BACKGROUND.getName());
        doReturn(FOREGROUND).when(colorManager).getColor(FOREGROUND.getName());

        // when
        final Border border = provider.getBorder("single color1 color2");

        // then
        final CellDescriptor template = CellDescriptor.of(BACKGROUND, FOREGROUND);

        assertElement(border.topLeft, template.withCh('┌'));
        assertElement(border.top, template.withCh('─'));
        assertElement(border.topRight, template.withCh('┐'));
        assertElement(border.right, template.withCh('│'));
        assertElement(border.bottomRight, template.withCh('┘'));
        assertElement(border.bottom, template.withCh('─'));
        assertElement(border.bottomLeft, template.withCh('└'));
        assertElement(border.left, template.withCh('│'));

    }

    private void assertElement(final Optional<CellDescriptor> actualOptional,
                               CellDescriptor expected) {

        Assertions.assertThat(actualOptional)
                .isPresent()
                .hasValueSatisfying(actual -> {
                    Assert.assertThat(actual.background.isPresent(), Is.is(expected.background.isPresent()));
                    Assert.assertThat(actual.foreground.isPresent(), Is.is(expected.foreground.isPresent()));
                    Assert.assertThat(actual.character.isPresent(), Is.is(expected.character.isPresent()));

                    Assertions.assertThat(actual.background).hasValue(expected.background.get());
                    Assertions.assertThat(actual.foreground).hasValue(expected.foreground.get());
                    Assertions.assertThat(actual.character).hasValue(expected.character.get());
                    Assertions.assertThat(actual.attributes).containsExactly(expected.attributes);
                });
    }
}