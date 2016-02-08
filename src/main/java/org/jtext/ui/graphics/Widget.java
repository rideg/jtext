package org.jtext.ui.graphics;

public interface Widget {

    void paint(Graphics graphics);

    OccupationType getPreferredWidth();

    OccupationType getPreferredHeight();

    void setArea(Rectangle area);
}
