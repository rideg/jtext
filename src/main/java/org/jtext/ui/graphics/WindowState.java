package org.jtext.ui.graphics;

public class WindowState {

    private Rectangle area;
    private boolean visible;
    private int zIndex;


    public WindowState(final Rectangle area, final int zIndex, final boolean visible) {
        this.zIndex = zIndex;
        this.visible = visible;
        this.area = area;
    }

    public Rectangle getArea() {
        return area;
    }

    public int getZIndex() {
        return zIndex;
    }

    public boolean isVisible() {
        return visible;
    }


    public void move(final Point point) {
        area = area.move(point);
    }

    public void resize(final int width, final int height) {
        area = area.resize(width, height);
    }

    public void hide() {
        visible = false;
    }

    public void show() {
        visible = true;
    }

    public void setZIndex(int newIndex) {
        this.zIndex = newIndex;
    }

    public WindowState copy() {
        return new WindowState(area.copy(), zIndex, visible);
    }
}
