package org.jtext.ui.widget;

import org.jtext.curses.CharacterColor;

import java.util.Optional;

public interface WidgetWithBackground {

    Optional<CharacterColor> backgroundColor();

}
