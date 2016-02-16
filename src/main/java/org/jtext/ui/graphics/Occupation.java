package org.jtext.ui.graphics;

import java.math.BigDecimal;

public abstract class Occupation {

    private Occupation() {

    }

    public static boolean isFilling(final Occupation occupation) {
        return occupation.getClass() == Fill.class;
    }

    public static boolean isFixed(final Occupation occupation) {
        return occupation.getClass() == Fixed.class;
    }

    public static boolean isProportional(final Occupation occupation) {
        return occupation.getClass() == Proportional.class;
    }

    public static Fixed fixed(final int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be a positive integer!");
        }
        return new Fixed(size);
    }

    public static Proportional percent(final int percentage) {
        if (percentage <= 0 || percentage > 100) {
            throw new IllegalArgumentException("Portion must be between 0 and 100");
        }
        return new Proportional(percentage);
    }

    public static Fill fill() {
        return new Fill();
    }

    public abstract int toReal(final int available);

    public static class Fixed extends Occupation {
        private final int size;


        private Fixed(int size) {
            this.size = size;
        }


        @Override
        public int toReal(final int available) {
            return size;
        }
    }

    public static class Proportional extends Occupation {
        public static final BigDecimal DIVISOR = new BigDecimal(100);
        private final BigDecimal percentage;

        private Proportional(int percentage) {
            this.percentage = new BigDecimal(percentage);
        }

        @Override
        public int toReal(final int available) {
            return new BigDecimal(available).multiply(percentage).divide(DIVISOR, BigDecimal.ROUND_FLOOR).intValue();
        }
    }

    public static class Fill extends Occupation {
        private Fill() {

        }

        @Override
        public int toReal(final int available) {
            return available;
        }
    }
}
