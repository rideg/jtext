package org.jtext.ui.graphics;

import org.jtext.curses.Driver;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class ZIndexRefreshStrategy {

    public static final Comparator<CursesWindow> Z_INDEX_COMPARATOR =
            (a, b) -> Integer.compare(a.getZIndex(), b.getZIndex());
    private final Driver driver;
    private final Set<CursesWindow> windows;

    public ZIndexRefreshStrategy(Driver driver) {
        this.driver = driver;
        this.windows = new TreeSet<>(Z_INDEX_COMPARATOR);
    }

    public void addWindow(final CursesWindow window) {
        windows.add(window);
    }

    public void removeWindow(final CursesWindow window) {
        windows.remove(window);
    }

    public void refresh() {
        driver.refresh(Driver.SCREEN_WINDOW_ID);
        windows.stream()
                .filter(CursesWindow::isVisible)
                .forEach(w -> driver.refresh(w.getId()));
        driver.doUpdate();
    }

}
