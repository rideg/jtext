package org.jtext.ui.graphics;

import org.jtext.curses.Driver;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CursesWindowTest {


    public static final Rectangle AREA = Rectangle.of(0, 0, 10, 10);
    public static final int Z_INDEX = 10;
    public static final boolean VISIBLE = true;
    private Driver driver;
    private CursesWindow window;


    @Before
    public void setUp() throws Exception {
        driver = mock(Driver.class);
        window = new CursesWindow(driver, new WindowState(AREA, Z_INDEX, VISIBLE));
    }

    @Test
    public void shouldUpdateStateWhenMoved() throws Exception {
        // when
        window.move(Point.at(10, 15));

        // then
        verify(driver).moveWindow(window.getId(), 10, 15);
        assertThat(window.getArea().topLeft(), is(Point.at(10, 15)));
    }

}