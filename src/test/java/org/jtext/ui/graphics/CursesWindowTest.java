package org.jtext.ui.graphics;

import org.jtext.curses.Driver;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CursesWindowTest {


    public static final int WINDOW_ID = 141234;
    public static final Rectangle AREA = Rectangle.of(0, 0, 10, 10);
    public static final int Z_INDEX = 10;
    public static final boolean VISIBLE = true;
    private Driver driver;
    private CursesWindow window;


    @Before
    public void setUp() throws Exception {
        driver = mock(Driver.class);
        window = new CursesWindow(driver, WINDOW_ID, AREA, Z_INDEX, VISIBLE);
    }

    @Test
    public void shouldSetDirtyFlagInCaseOfDrawing() throws Exception {
        // when
        window.drawHorizontalLine('=', 10);

        // then
        verify(driver).drawHorizontalLine(WINDOW_ID, '=', 10);
        assertThat(window.isDirty(), is(true));
    }

}