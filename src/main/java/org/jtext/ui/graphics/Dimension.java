package org.jtext.ui.graphics;

@SuppressWarnings("checkstyle:visibilitymodifier")
public final class Dimension {

    public final int width;
    public final int height;

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
    public String toString() {
        return "Dimension{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}

