package org.jtext.ui.theme;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.jtext.ui.graphics.Widget;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Path;

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
        try (final InputStreamReader reader = new InputStreamReader(stream, Charset.forName("UTF-8"))) {
            return new ThemeProvider(Json.parse(reader).asObject());
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read default theme!");
        }
    }

    public JsonObject getWidgetConfig(final Class<? extends Widget> type) {
        return root.get("widgets").asObject().get(type.getCanonicalName()).asObject();
    }

    public JsonObject getBorders() {
        return root.get("borders").asObject();
    }

    public JsonObject getColors() {
        return root.get("colors").asObject();
    }
}
