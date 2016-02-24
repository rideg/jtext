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
import java.util.HashMap;
import java.util.Map;

public final class ThemeProvider {

    private final JsonObject root;
    private JsonObject borders;

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
        final Map<String, RgbValue> colorsMapping = new HashMap<>();
        JsonObject colors = root.get("colors").asObject();
        for (JsonObject.Member member : colors) {
            if (!"config".equals(member.getName())) {
                colorsMapping.put(member.getName(), convert(member.getValue().asString()));
            }
        }
        return colorsMapping;
    }

    private RgbValue convert(final String value) {
        return RgbValue.of(Integer.valueOf(value.substring(1, 3), 16), Integer.valueOf(value.substring(3, 5), 16),
                           Integer.valueOf(value.substring(5, 7), 16));
    }

    public String getColorValue(final String colorName) {
        return root.get("colors").asObject().getString(colorName, null);
    }

    public JsonObject getBorders() {
        return root.get("borders").asObject();
    }
}
