package org.jtext.ui.graphics;

public class Occupation {

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

    public static class Fixed extends Occupation {
        private final int size;


        private Fixed(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }
    }

    public static class Proportional extends Occupation {
        private final int percentage;

        private Proportional(int percentage) {
            this.percentage = percentage;
        }

        public int getPercentage() {
            return percentage;
        }
    }

    public static class Fill extends Occupation {
        private Fill() {

        }
    }
}
