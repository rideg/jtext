package org.jtext.ui.attribute;

public enum Direction {
    HORIZONTAL,
    VERTICAL;


    public Direction opposite() {
        if (this == HORIZONTAL) {
            return VERTICAL;
        } else {
            return HORIZONTAL;
        }
    }
}
