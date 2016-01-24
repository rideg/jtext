package org.jtext.curses;

public enum ControlKey {

    // C0
    NULL,
    START_HEADING,
    START_OF_TEXT,
    END_OF_TEXT,
    END_OF_TRANSMISSION,
    ENQUIRY,
    ACKNOWLEDGE,
    BELL,
    CTRL_BACKSPACE,
    HORIZONTAL_TAB,
    NEW_LINE,
    VERTICAL_TAB,
    FROM_FEED,
    CARRIAGE_RETURN,
    SHIFT_OUT,
    SHIFT_IN,
    DATA_LINK_ESCAPE,
    DEVICE_CTRL_ONE,
    DEVICE_CTRL_TWO,
    DEVICE_CTRL_THREE,
    DEVICE_CTRL_FOUR,
    NEGATIVE_ACK,
    SYNC_IDLE,
    EOF_TRANSMISSION_BLOCK,
    CANCEL,
    EOF_MEDIUM,
    SUBSTITUTE,
    ESCAPE,
    FILE_SEPARATOR,
    GROUP_SEPARATOR,
    RECORD_SEPARATOR,
    UNIT_SEPARATOR,
    SPACE,

    FUNCTION_KEY, // up to 64

    // OTHER
    BACKSPACE,

    DELETE,
    INSERT,
    HOME,
    END,
    NEXT_PAGE,
    PREVIOUS_PAGE,
    UP,
    DOWN,
    LEFT,
    RIGHT,

    SHIFT_DELETE,
    SHIFT_HOME,
    SHIFT_NEXT_PAGE,
    SHIFT_PREVIOUS_PAGE,
    SHIFT_UP,
    SHIFT_DOWN,
    SHIFT_LEFT,
    SHIFT_RIGHT,
    SHIFT_TAB,

    CTRL_UP,
    CTRL_DOWN,
    CTRL_LEFT,
    CTRL_RIGHT,
    CTRL_HOME,
    CTRL_DELETE,
    CTRL_END,
    CTRL_NEXT_PAGE,
    CTRL_PREVIOUS_PAGE,

    ALT_INSERT,
    ALT_HOME,
    ALT_DELETE,
    ALT_END,
    ALT_NEXT_PAGE,
    ALT_PREVIOUS_PAGE,

    ALT_UP,
    ALT_DOWN,
    ALT_LEFT,
    ALT_RIGHT,

    CTRL_ALT_UP,
    CTRL_ALT_DOWN,
    CTRL_ALT_LEFT,
    CTRL_ALT_RIGHT,

    SHIFT_CTRL_UP,
    SHIFT_CTRL_DOWN,
    SHIFT_CTRL_RIGHT,
    SHIFT_CTRL_LEFT,
    SHIFT_CTRL_HOME,
    SHIFT_CTRL_END,
    SHIFT_CTRL_DELETE,

    SHIFT_ALT_UP,
    SHIFT_ALT_DOWN,
    SHIFT_ALT_LEFT,
    SHIFT_ALT_RIGHT,

    SHIFT_ALT_HOME,
    SHIFT_ALT_END,
    SHIFT_ALT_DELETE,
    SHIFT_ALT_NEXT_PAGE,
    SHIFT_ALT_PREVIOUS_PAGE,

    // OTHER
    RESIZE,
    ERR,
    UNKNOWN

}