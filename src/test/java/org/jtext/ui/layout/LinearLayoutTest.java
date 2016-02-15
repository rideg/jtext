package org.jtext.ui.layout;

import org.hamcrest.core.Is;
import org.jtext.testsupport.DummyWidget;
import org.jtext.ui.attribute.HorizontalAlign;
import org.jtext.ui.attribute.Margin;
import org.jtext.ui.attribute.VerticalAlign;
import org.jtext.ui.graphics.Dimension;
import org.jtext.ui.graphics.Position;
import org.jtext.ui.graphics.Rectangle;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static org.jtext.ui.graphics.Occupation.*;

public class LinearLayoutTest {

    private static final DummyWidget FIXED_WIDGET = new DummyWidget(fixed(6), fixed(6), fixed(3),
                                                                    fixed(3), fixed(6), fixed(6),
                                                                    Position.RELATIVE, true);


    private static final DummyWidget FIXED_WIDGET_MARGIN = new DummyWidget(fixed(6), fixed(6), fixed(3),
                                                                           fixed(3), fixed(6), fixed(6),
                                                                           Margin.of(2, 4, 1, 5),
                                                                           Position.RELATIVE);

    private static final DummyWidget FIXED_WIDGET_2 = new DummyWidget(fixed(4), fixed(4), fixed(2),
                                                                      fixed(2), fixed(4), fixed(4),
                                                                      Position.RELATIVE, true);

    private static final DummyWidget FILLING_WIDGET = new DummyWidget(fill(), fill(), fixed(5),
                                                                      fixed(5), fill(), fill(),
                                                                      Position.RELATIVE, true);

    private static final DummyWidget FILLING_WIDGET_2 = new DummyWidget(fill(), fill(), fixed(5),
                                                                        fixed(5), fill(), fill(),
                                                                        Position.RELATIVE, true);

    private static final DummyWidget HIDDEN_WIDGET = new DummyWidget(fill(), fill(), fixed(5),
                                                                     fixed(5), fill(), fill(),
                                                                     Position.RELATIVE, false);

    private static final DummyWidget FILLING_WIDGET_WITH_MAX_SIZE = new DummyWidget(fill(), fill(), fixed(5),
                                                                                    fixed(5), fixed(30), fixed(20),
                                                                                    Position.RELATIVE, true);

    private static final DummyWidget PROPORTIONAL_WIDGET = new DummyWidget(percent(30), percent(50), fixed(5),
                                                                           fixed(5), percent(30), percent(50),
                                                                           Position.RELATIVE, true);


    private static final DummyWidget FIXED_SMALL_WIDGET_MARGIN = new DummyWidget(fixed(1), fixed(1), fixed(1),
                                                                           fixed(1), fixed(1), fixed(1),
                                                                           Margin.of(0, 3, 0, 1),
                                                                           Position.RELATIVE);


    private static final DummyWidget FIXED_SMALL_WIDGET_MARGIN_MIN_SIZE = new DummyWidget(fixed(2), fixed(1), fixed(1),
                                                                                          fixed(1), fixed(1), fixed(1),
                                                                                          Margin.of(0, 3, 0, 1),
                                                                                          Position.RELATIVE);

    private LinearLayout layout;

    @Test
    public void shouldAlignLeftOneWidget() throws Exception {
        // given
        layout = Layouts.horizontal(HorizontalAlign.LEFT);
        layout.addWidget(FIXED_WIDGET);

        // when
        layout.setDimension(Dimension.of(50, 6));

        // then
        final Rectangle area = layout.getAreaFor(FIXED_WIDGET);
        Assert.assertThat(area, Is.is(Rectangle.of(0, 0, 6, 6)));
    }

    @Test
    public void shouldAlignRightOneWidget() throws Exception {
        // given
        layout = Layouts.horizontal(HorizontalAlign.RIGHT);
        layout.addWidget(FIXED_WIDGET);

        // when
        layout.setDimension(Dimension.of(50, 6));

        // then
        final Rectangle area = layout.getAreaFor(FIXED_WIDGET);

        Assert.assertThat(area, Is.is(Rectangle.of(44, 0, 6, 6)));
    }

    @Test
    public void shouldCenterOneWidget() throws Exception {
        // given
        layout = Layouts.horizontal(HorizontalAlign.CENTER);
        layout.addWidget(FIXED_WIDGET);

        // when
        layout.setDimension(Dimension.of(50, 6));

        // then
        final Rectangle area = layout.getAreaFor(FIXED_WIDGET);
        Assert.assertThat(area, Is.is(Rectangle.of(22, 0, 6, 6)));
    }


    @Test
    public void shouldAlignLeftTwoWidgets() throws Exception {
        // given
        layout = Layouts.horizontal(HorizontalAlign.LEFT);
        layout.addWidget(FIXED_WIDGET);
        layout.addWidget(FIXED_WIDGET_2);

        // when
        layout.setDimension(Dimension.of(50, 10));

        // then
        final Rectangle area1 = layout.getAreaFor(FIXED_WIDGET);
        final Rectangle area2 = layout.getAreaFor(FIXED_WIDGET_2);

        Assert.assertThat(area1, Is.is(Rectangle.of(0, 0, 6, 6)));
        Assert.assertThat(area2, Is.is(Rectangle.of(6, 0, 4, 4)));
    }

    @Test
    public void shouldAlignRightTwoWidgets() throws Exception {
        // given
        layout = Layouts.horizontal(HorizontalAlign.RIGHT);
        layout.addWidget(FIXED_WIDGET);
        layout.addWidget(FIXED_WIDGET_2);

        // when
        layout.setDimension(Dimension.of(50, 10));

        // then
        final Rectangle area1 = layout.getAreaFor(FIXED_WIDGET);
        final Rectangle area2 = layout.getAreaFor(FIXED_WIDGET_2);

        Assert.assertThat(area1, Is.is(Rectangle.of(40, 0, 6, 6)));
        Assert.assertThat(area2, Is.is(Rectangle.of(46, 0, 4, 4)));
    }

    @Test
    public void shouldCenterTwoWidgets() throws Exception {
        // given
        layout = Layouts.horizontal(HorizontalAlign.CENTER);
        layout.addWidget(FIXED_WIDGET);
        layout.addWidget(FIXED_WIDGET_2);

        // when
        layout.setDimension(Dimension.of(50, 10));

        // then
        final Rectangle area1 = layout.getAreaFor(FIXED_WIDGET);
        final Rectangle area2 = layout.getAreaFor(FIXED_WIDGET_2);

        Assert.assertThat(area1, Is.is(Rectangle.of(20, 0, 6, 6)));
        Assert.assertThat(area2, Is.is(Rectangle.of(26, 0, 4, 4)));
    }

    @Ignore
    @Test
    public void shouldConsiderMinWidth() throws Exception {

        // given
        layout = Layouts.horizontal(HorizontalAlign.LEFT);
        layout.addWidget(FIXED_WIDGET);

        // when
        layout.setDimension(Dimension.of(4, 6));

        // then
        final Rectangle area = layout.getAreaFor(FIXED_WIDGET);
        Assert.assertThat(area, Is.is(Rectangle.of(0, 0, 4, 6)));
    }

    @Ignore
    @Test
    public void shouldUseMinWidthIfAvailableSpaceIsLess() throws Exception {

        // given
        layout = Layouts.horizontal(HorizontalAlign.LEFT);
        layout.addWidget(FIXED_WIDGET);

        // when
        layout.setDimension(Dimension.of(2, 6));

        // then
        final Rectangle area = layout.getAreaFor(FIXED_WIDGET);
        Assert.assertThat(area, Is.is(Rectangle.of(0, 0, 3, 6)));
    }

    @Test
    public void shouldAlignVerticallyAndConsideringMarginToo() throws Exception {
        // given
        layout = Layouts.horizontal(HorizontalAlign.LEFT, VerticalAlign.BOTTOM);
        layout.addWidget(FIXED_WIDGET_MARGIN);
        layout.addWidget(FIXED_WIDGET_2);

        // when
        layout.setDimension(Dimension.of(50, 25));

        // then
        final Rectangle area1 = layout.getAreaFor(FIXED_WIDGET_MARGIN);
        final Rectangle area2 = layout.getAreaFor(FIXED_WIDGET_2);
        final Margin margin = FIXED_WIDGET_MARGIN.getMargin();
        Assert.assertThat(area1, Is.is(Rectangle.of(margin.left, 19 - margin.bottom, 6, 6)));
        Assert.assertThat(area2, Is.is(Rectangle.of(6 + margin.left + margin.right, 21, 4, 4)));
    }

    @Test
    public void shouldConsiderMinHeight() throws Exception {
        // given
        layout = Layouts.horizontal(HorizontalAlign.CENTER);
        layout.addWidget(FIXED_WIDGET);

        // when
        layout.setDimension(Dimension.of(50, 3));

        // then
        final Rectangle area = layout.getAreaFor(FIXED_WIDGET);
        Assert.assertThat(area, Is.is(Rectangle.of(22, 0, 6, 3)));
    }

    @Test
    public void shouldFillAreForFillingWidget() throws Exception {
        // given
        layout = Layouts.horizontal(HorizontalAlign.LEFT);
        layout.addWidget(FILLING_WIDGET);

        // when
        layout.setDimension(Dimension.of(50, 10));

        // then
        final Rectangle area = layout.getAreaFor(FILLING_WIDGET);
        Assert.assertThat(area, Is.is(Rectangle.of(0, 0, 50, 10)));
    }

    @Test
    public void shouldGiveTheRequestedPercentageForWidget() throws Exception {

        // given
        layout = Layouts.horizontal(HorizontalAlign.LEFT);
        layout.addWidget(PROPORTIONAL_WIDGET);

        // when
        layout.setDimension(Dimension.of(50, 10));

        // then
        final Rectangle area = layout.getAreaFor(PROPORTIONAL_WIDGET);
        Assert.assertThat(area, Is.is(Rectangle.of(0, 0, 15, 5)));

    }

    @Test
    public void shouldConsiderMargin() throws Exception {
        // given
        layout = Layouts.horizontal(HorizontalAlign.LEFT);
        layout.addWidget(FIXED_WIDGET_MARGIN);
        layout.addWidget(FIXED_WIDGET_2);

        // when
        layout.setDimension(Dimension.of(50, 10));

        // then
        final Rectangle area1 = layout.getAreaFor(FIXED_WIDGET_MARGIN);
        final Rectangle area2 = layout.getAreaFor(FIXED_WIDGET_2);
        final Margin margin = FIXED_WIDGET_MARGIN.getMargin();
        Assert.assertThat(area1, Is.is(Rectangle.of(margin.left, margin.top, 6, 6)));
        Assert.assertThat(area2, Is.is(Rectangle.of(6 + margin.left + margin.right, 0, 4, 4)));
    }

    @Test
    public void shouldConsiderMarginProperlyWhenCentering() throws Exception {
        // given
        layout = Layouts.horizontal(HorizontalAlign.CENTER);
        layout.addWidget(FIXED_SMALL_WIDGET_MARGIN);

        // when
        layout.setDimension(Dimension.of(7, 1));

        // then
        final Rectangle area = layout.getAreaFor(FIXED_SMALL_WIDGET_MARGIN);
        Assert.assertThat(area, Is.is(Rectangle.of(3, 0, 1, 1)));
    }

    @Ignore
    @Test
    public void shouldConsiderGiveLessSpaceForWidgetWithMarginToFulfillMarginRequirements() throws Exception {
        // given
        layout = Layouts.horizontal(HorizontalAlign.RIGHT);
        layout.addWidget(FIXED_SMALL_WIDGET_MARGIN_MIN_SIZE);

        // when
        layout.setDimension(Dimension.of(5, 1));

        // then
        final Rectangle area = layout.getAreaFor(FIXED_SMALL_WIDGET_MARGIN_MIN_SIZE);
        Assert.assertThat(area, Is.is(Rectangle.of(0, 0, 1, 1)));
    }

    @Test
    public void shouldConsiderMaxWidthAndHeight() throws Exception {
        // given
        layout = Layouts.horizontal(HorizontalAlign.LEFT);
        layout.addWidget(FILLING_WIDGET_WITH_MAX_SIZE);

        // when
        layout.setDimension(Dimension.of(50, 25));

        // then
        final Rectangle area = layout.getAreaFor(FILLING_WIDGET_WITH_MAX_SIZE);
        Assert.assertThat(area, Is.is(Rectangle.of(0, 0, 30, 20)));
    }

    @Test
    public void shouldAlignFillingAndFixedWidgets() throws Exception {
        // given
        layout = Layouts.horizontal(HorizontalAlign.LEFT);
        layout.addWidget(FIXED_WIDGET);
        layout.addWidget(FILLING_WIDGET);

        // when
        layout.setDimension(Dimension.of(50, 25));

        // then
        final Rectangle area1 = layout.getAreaFor(FIXED_WIDGET);
        final Rectangle area2 = layout.getAreaFor(FILLING_WIDGET);

        Assert.assertThat(area1, Is.is(Rectangle.of(0, 0, 6, 6)));
        Assert.assertThat(area2, Is.is(Rectangle.of(6, 0, 44, 25)));
    }

    @Test
    public void shouldAlignFillingAndProportionalWidgets() throws Exception {
        // given
        layout = Layouts.horizontal(HorizontalAlign.LEFT);
        layout.addWidget(PROPORTIONAL_WIDGET);
        layout.addWidget(FILLING_WIDGET);

        // when
        layout.setDimension(Dimension.of(50, 24));

        // then
        final Rectangle area1 = layout.getAreaFor(PROPORTIONAL_WIDGET);
        final Rectangle area2 = layout.getAreaFor(FILLING_WIDGET);

        Assert.assertThat(area1, Is.is(Rectangle.of(0, 0, 15, 12)));
        Assert.assertThat(area2, Is.is(Rectangle.of(15, 0, 35, 24)));
    }

    @Test
    public void shouldAlignFillingFixedAndProportionalWidgets() throws Exception {
        // given
        layout = Layouts.horizontal(HorizontalAlign.LEFT);
        layout.addWidget(PROPORTIONAL_WIDGET);
        layout.addWidget(FIXED_WIDGET);

        // when
        layout.setDimension(Dimension.of(50, 24));

        // then
        final Rectangle area1 = layout.getAreaFor(PROPORTIONAL_WIDGET);
        final Rectangle area2 = layout.getAreaFor(FIXED_WIDGET);

        Assert.assertThat(area1, Is.is(Rectangle.of(0, 0, 15, 12)));
        Assert.assertThat(area2, Is.is(Rectangle.of(15, 0, 6, 6)));
    }

    @Test
    public void shouldAlignTwiFillingWidgets() throws Exception {
        // given
        layout = Layouts.horizontal(HorizontalAlign.LEFT);
        layout.addWidget(FILLING_WIDGET);
        layout.addWidget(FILLING_WIDGET_2);

        // when
        layout.setDimension(Dimension.of(50, 10));

        // then
        final Rectangle area1 = layout.getAreaFor(FILLING_WIDGET);
        final Rectangle area2 = layout.getAreaFor(FILLING_WIDGET_2);

        Assert.assertThat(area1, Is.is(Rectangle.of(0, 0, 25, 10)));
        Assert.assertThat(area2, Is.is(Rectangle.of(25, 0, 25, 10)));
    }

    @Test
    public void shouldSkipInvisibleWidgets() throws Exception {
        // given
        layout = Layouts.horizontal(HorizontalAlign.CENTER);
        layout.addWidget(FIXED_WIDGET);
        layout.addWidget(HIDDEN_WIDGET);
        layout.addWidget(FIXED_WIDGET_2);

        // when
        layout.setDimension(Dimension.of(50, 10));

        // then
        final Rectangle area1 = layout.getAreaFor(FIXED_WIDGET);
        final Rectangle area2 = layout.getAreaFor(FIXED_WIDGET_2);

        Assert.assertThat(area1, Is.is(Rectangle.of(20, 0, 6, 6)));
        Assert.assertThat(area2, Is.is(Rectangle.of(26, 0, 4, 4)));
    }

}