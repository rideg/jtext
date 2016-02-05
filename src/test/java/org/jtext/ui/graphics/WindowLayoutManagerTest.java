package org.jtext.ui.graphics;

import org.jtext.curses.Driver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

public class WindowLayoutManagerTest {


    private Driver driver;
    private WindowLayoutManager manager;

    @Before
    public void setUp() throws Exception {
        driver = Mockito.mock(Driver.class);
        manager = new WindowLayoutManager(driver);
    }


    @Test
    public void shouldUpdateWindowsInZIndexOrder() throws Exception {
        // given
        final CursesWindow bottom = new CursesWindow(driver, 1, new WindowState(Rectangle.of(0, 0, 50, 50), 1, true));
        final CursesWindow middle = new CursesWindow(driver, 2, new WindowState(Rectangle.of(25, 25, 10, 10), 2, true));
        final CursesWindow top = new CursesWindow(driver, 3, new WindowState(Rectangle.of(20, 20, 10, 10), 2, true));


        manager.addWindow(bottom);
        manager.addWindow(middle);
        manager.addWindow(top);

        // when
        manager.refresh();

        // then
        InOrder order = Mockito.inOrder(driver);
        order.verify(driver).refresh(bottom.getId());
        order.verify(driver).refresh(middle.getId());
        order.verify(driver).refresh(top.getId());
        order.verify(driver).doUpdate();
    }

    @Test
    public void shouldNotUpdateTopWindowIfThatIsInvisible() throws Exception {
        // given
        final CursesWindow bottom = new CursesWindow(driver, 1, new WindowState(Rectangle.of(0, 0, 50, 50), 1, true));
        final CursesWindow top = new CursesWindow(driver, 2, new WindowState(Rectangle.of(20, 20, 10, 10), 2, false));

        manager.addWindow(bottom);
        manager.addWindow(top);

        // when
        manager.refresh();

        // then
        InOrder order = Mockito.inOrder(driver);
        order.verify(driver).refresh(bottom.getId());
        order.verify(driver).doUpdate();
        Mockito.verify(driver, Mockito.times(0)).refresh(top.getId());
    }


}