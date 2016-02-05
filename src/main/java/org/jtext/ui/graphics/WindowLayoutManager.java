package org.jtext.ui.graphics;

import org.jtext.curses.Driver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WindowLayoutManager {

    public static final Comparator<CursesWindow> Z_INDEX_COMPARATOR = (a, b) -> Integer.compare(a.getZIndex(), b.getZIndex());
    public static final int CAPACITY = 500;
    private final Driver driver;
    private final List<CursesWindow> windows;

    public WindowLayoutManager(Driver driver) {
        this.driver = driver;
        this.windows = new ArrayList<>(CAPACITY);
    }

    public void addWindow(final CursesWindow window) {
        windows.add(window);
        Collections.sort(windows, Z_INDEX_COMPARATOR);
    }

    public void removeWindow(final CursesWindow window) {
        windows.remove(window.getId());
    }

    public void refresh() {
        windows.stream()
                .filter(CursesWindow::isVisible)
                .forEach(w -> driver.refresh(w.getId()));
        driver.doUpdate();
    }

}
