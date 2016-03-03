package org.jtext.ui.graphics;

import java.util.Objects;

public final class Dimension {

    private final int width;
    private final int height;

    private Dimension(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Dimension cannot be negative!");
        }
        this.width = width;
        this.height = height;
    }

    public static Dimension of(final int width, final int height) {
        return new Dimension(width, height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Dimension incWidth() {
        return of(width + 1, height);
    }

    public Dimension decWidth() {
        return of(width - 1, height);
    }

    public Dimension incHeight() {
        return of(width, height + 1);
    }

    public Dimension decHeight() {
        return of(width, height - 1);
    }

    public Dimension flip() {
        return Dimension.of(height, width);
    }


    public Dimension shrink(int horizontal, int vertical) {
        return Dimension.of(Math.max(width - horizontal, 0), Math.max(height - vertical, 0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dimension)) {
            return false;
        }
        final Dimension dimension = (Dimension) o;
        return width == dimension.width &&
                height == dimension.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }

    @Override
    public String toString() {
        return "Dimension{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}

