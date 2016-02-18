package org.jtext.ui.graphics;

@SuppressWarnings("checkstyle:visibilitymodifier")
public final class Dimension {

    public final int width;
    public final int height;


    private Dimension(int width, int height) {
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

    @Override
    public String toString() {
        return "Dimension{" +
               "width=" + width +
               ", height=" + height +
               '}';
    }


}

