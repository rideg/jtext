package org.jtext.ui.util;

public final class Util {

    private Util() {

    }

    public static int divHalfUp(final int dividend, final int divisor) {
        return (dividend + (divisor >> 1)) / divisor;
    }

}
