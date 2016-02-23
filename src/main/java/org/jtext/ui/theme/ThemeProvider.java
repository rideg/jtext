package org.jtext.ui.theme;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.jtext.curses.RgbValue;
import org.jtext.ui.graphics.Widget;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Map;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Spliterators.spliterator;

public final class ThemeProvider {

    private final JsonObject root;

    private ThemeProvider(final JsonObject root) {
        this.root = root;
    }

    public static ThemeProvider loadDefault() {
        return load(ThemeProvider.class.getResourceAsStream("/org/jtext/theme/default.theme.json"));
    }

    public static ThemeProvider load(final Path themeFile) {
        try (final FileInputStream stream = new FileInputStream(themeFile.toFile())) {
            return load(stream);
        } catch (IOException exception) {
            throw new IllegalStateException("Cannot load theme from: " + themeFile.toString());
        }
    }

    private static ThemeProvider load(final InputStream stream) {
        try (final InputStreamReader reader = new InputStreamReader(stream)) {
            return new ThemeProvider(Json.parse(reader).asObject());
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read default theme!");
        }
    }

    public JsonObject getWidgetConfig(final Class<? extends Widget> type) {
        return root.get("widgets").asObject().get(type.getCanonicalName()).asObject();
    }

    public Map<String, RgbValue> getDefinedColors() {
        JsonObject colors = root.get("colors").asObject();
        return StreamSupport.stream(spliterator(colors.iterator(), colors.size(),
                                                Spliterator.DISTINCT |
                                                Spliterator.IMMUTABLE |
                                                Spliterator.SIZED |
                                                Spliterator.SUBSIZED), false)
                       .filter(m -> !m.getName().equals("config"))
                       .filter(m -> m.getValue().asString().length() == 0)
                       .filter(m -> m.getValue().asString().charAt(0) != '#')
                       .collect(Collectors.toMap(JsonObject.Member::getName, m -> convert(m.getValue().asString())));
    }

    private RgbValue convert(final String value) {
        return RgbValue.of(Integer.valueOf(value.substring(1, 3), 16), Integer.valueOf(value.substring(3, 5), 16),
                           Integer.valueOf(value.substring(5, 7), 16));
    }

    public String getColorValue(final String colorName) {
        return root.get("colors").asObject().getString(colorName, null);
    }
}
