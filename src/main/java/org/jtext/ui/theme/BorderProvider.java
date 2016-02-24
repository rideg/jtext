package org.jtext.ui.theme;

import com.eclipsesource.json.JsonObject;
import org.jtext.curses.CellDescriptor;
import org.jtext.ui.attribute.Border;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.jtext.curses.CellDescriptor.empty;

public class BorderProvider {

    private final ColorManager colorManager;
    private final Map<String, Border> nameToBorder;

    public BorderProvider(final ColorManager colorManager, final JsonObject borders) {
        this.colorManager = colorManager;
        nameToBorder = new HashMap<>();
        parse(borders);
    }

    private void parse(final JsonObject borders) {
        for (JsonObject.Member member : borders) {
            nameToBorder.put(member.getName(), parseBorder(member.getValue().asObject()));
        }
    }

    private Border parseBorder(final JsonObject borderJson) {
        final Optional<CellDescriptor> topLeft = parseElement(borderJson, "topLeft");
        final Optional<CellDescriptor> top = parseElement(borderJson, "top");
        final Optional<CellDescriptor> topRight = parseElement(borderJson, "topRight");
        final Optional<CellDescriptor> right = parseElement(borderJson, "right");
        final Optional<CellDescriptor> bottomRight = parseElement(borderJson, "bottomRight");
        final Optional<CellDescriptor> bottom = parseElement(borderJson, "bottom");
        final Optional<CellDescriptor> bottomLeft = parseElement(borderJson, "bottomLeft");
        final Optional<CellDescriptor> left = parseElement(borderJson, "left");

        return new Border(topLeft, top, topRight, right, bottomRight, bottom, bottomLeft, left);
    }

    private Optional<CellDescriptor> parseElement(final JsonObject borderJson, final String name) {
        return ofNullable(borderJson.getString(name, null)).map(s -> empty().withCh(s.charAt(0)));
    }

    public Border getBorder(final String name) {
        final String[] split = name.trim().split(" +");
        final Border borderTemplate = nameToBorder.get(split[0]);

        if (borderTemplate != null) {
            return borderTemplate.changeCell(
                    CellDescriptor.of(colorManager.getColor(split[1]), colorManager.getColor(split[2])));
        }
        return Border.no();
    }
}
