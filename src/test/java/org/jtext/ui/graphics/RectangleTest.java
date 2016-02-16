package org.jtext.ui.graphics;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RectangleTest {


    @Test
    public void shouldResize() throws Exception {
        assertThat(Rectangle.of(0,0,5,5).resize(Dimension.of(10, 10)), is(Rectangle.of(0, 0, 10, 10)));
    }

    @Test
    public void shouldCalculateEdges() throws Exception {

        // given
        final Rectangle rectangle = Rectangle.of(23, 75, 19, 10);

        // when
        assertThat(rectangle.topLeft(), is(Point.at(23, 75)));
        assertThat(rectangle.topRight(), is(Point.at(41, 75)));
        assertThat(rectangle.bottomLeft(), is(Point.at(23, 84)));
        assertThat(rectangle.bottomRight(), is(Point.at(41, 84)));
    }

}