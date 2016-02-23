package org.jtext.ui.theme;

import org.jtext.ui.attribute.Border;

import java.util.HashMap;
import java.util.Map;

public class BorderProvider {

    private final ColorProvider colorProvider;
    private final Map<String, Border> nameToBorder;

    public BorderProvider(final ColorProvider colorProvider) {
        this.colorProvider = colorProvider;
        nameToBorder = new HashMap<>();
    }

    public Border getBorder(final String string) {
        return null;
    }
}
