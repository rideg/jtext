package org.jtext.ui.graphics;

import java.util.Set;

public class Container implements Widget {

    private Set<Widget> widgets;
    private LayoutManager layoutManager;

    public Container(LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void paint(Graphics graphics) {

    }

    @Override
    public OccupationType getPreferredWidth() {
        return layoutManager.getPreferredWidth();
    }

    @Override
    public OccupationType getPreferredHeight() {
        return layoutManager.getPreferredHeight();
    }

    @Override
    public Position getPosition() {
        return null;
    }

    @Override
    public void setArea(Rectangle area) {
        layoutManager.setArea(area);
    }
}
