package org.jtext.ui.graphics;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.jtext.ui.graphics.Line.horizontal;
import static org.junit.Assert.assertThat;

public class RectangleTest {


    public static final Line EMPTY_VERTICAL = Line.vertical(0, 0, 0);

    @Test
    public void shouldResize() throws Exception {
        assertThat(Rectangle.of(0, 0, 5, 5).resize(Dimension.of(10, 10)), is(Rectangle.of(0, 0, 10, 10)));
    }

    @Test
    public void shouldCalculateEdges() throws Exception {

        // given
        final Rectangle rectangle = Rectangle.of(23, 75, 19, 10);

        // then
        assertThat(rectangle.topLeft(), is(Point.at(23, 75)));
        assertThat(rectangle.topRight(), is(Point.at(41, 75)));
        assertThat(rectangle.bottomLeft(), is(Point.at(23, 84)));
        assertThat(rectangle.bottomRight(), is(Point.at(41, 84)));
    }


    @Test
    public void shouldDetermineIfAPointIsInside() throws Exception {
        // given
        final Rectangle rectangle = Rectangle.of(5, 5, 10, 10);

        // then
        assertThat(rectangle.has(Point.at(2, 2)), is(false));
        assertThat(rectangle.has(Point.at(5, 5)), is(true));
        assertThat(rectangle.has(Point.at(14, 14)), is(true));
        assertThat(rectangle.has(Point.at(15, 15)), is(false));
        assertThat(rectangle.has(Point.at(7, 20)), is(false));
        assertThat(rectangle.has(Point.at(20, 7)), is(false));
    }

    @Test
    public void shouldDetermineIfRelativePointIsInside() throws Exception {
        // given
        final Rectangle rectangle = Rectangle.of(5, 5, 10, 10);

        // then
        assertThat(rectangle.hasRelative(Point.at(-1, -1)), is(false));
        assertThat(rectangle.hasRelative(Point.at(0, 0)), is(true));
        assertThat(rectangle.hasRelative(Point.at(9, 9)), is(true));
        assertThat(rectangle.hasRelative(Point.at(15, 15)), is(false));
        assertThat(rectangle.hasRelative(Point.at(3, 20)), is(false));
        assertThat(rectangle.hasRelative(Point.at(20, 3)), is(false));

    }

    @Test
    public void shouldCropRelativeLineToInner() throws Exception {

        final Rectangle rectangle = Rectangle.of(0, 0, 10, 10);

        // then
        assertThat(rectangle.cropRelative(horizontal(0, 0, 5)), is(horizontal(0, 0, 5)));
        assertThat(rectangle.cropRelative(horizontal(-1, 0, 5)), is(horizontal(0, 0, 4)));
        assertThat(rectangle.cropRelative(horizontal(-1, -1, 5)), is(horizontal(0, 0, 0)));
        assertThat(rectangle.cropRelative(horizontal(-3, 5, 5)), is(horizontal(0, 5, 2)));
        assertThat(rectangle.cropRelative(horizontal(-3, 5, 20)), is(horizontal(0, 5, 10)));
        assertThat(rectangle.cropRelative(horizontal(2, 5, 20)), is(horizontal(2, 5, 8)));
        assertThat(rectangle.cropRelative(horizontal(12, 5, 20)), is(horizontal(0, 0, 0)));

    }
}
