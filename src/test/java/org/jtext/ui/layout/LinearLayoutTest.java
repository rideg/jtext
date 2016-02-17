package org.jtext.ui.layout;

import org.jtext.testsupport.DummyWidget;
import org.jtext.ui.attribute.HorizontalAlign;
import org.jtext.ui.attribute.Margin;
import org.jtext.ui.attribute.VerticalAlign;
import org.jtext.ui.graphics.*;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.jtext.ui.graphics.Occupation.*;
import static org.junit.Assert.assertThat;

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
        assertThat(area, is(Rectangle.of(0, 0, 6, 6)));
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

        assertThat(area, is(Rectangle.of(44, 0, 6, 6)));
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
        assertThat(area, is(Rectangle.of(22, 0, 6, 6)));
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

        assertThat(area1, is(Rectangle.of(0, 0, 6, 6)));
        assertThat(area2, is(Rectangle.of(6, 0, 4, 4)));
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

        assertThat(area1, is(Rectangle.of(40, 0, 6, 6)));
        assertThat(area2, is(Rectangle.of(46, 0, 4, 4)));
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

        assertThat(area1, is(Rectangle.of(20, 0, 6, 6)));
        assertThat(area2, is(Rectangle.of(26, 0, 4, 4)));
    }

    @Test
    public void shouldConsiderMinWidth() throws Exception {

        // given
        layout = Layouts.horizontal(HorizontalAlign.LEFT);
        layout.addWidget(FIXED_WIDGET);

        // when
        layout.setDimension(Dimension.of(4, 6));

        // then
        final Rectangle area = layout.getAreaFor(FIXED_WIDGET);
        assertThat(area, is(Rectangle.of(0, 0, 4, 6)));
    }

    @Test
    public void shouldUseMinWidthIfLessSpaceIsAvailable() throws Exception {

        // given
        layout = Layouts.horizontal(HorizontalAlign.LEFT);
        layout.addWidget(FIXED_WIDGET);

        // when
        layout.setDimension(Dimension.of(2, 6));

        // then
        final Rectangle area = layout.getAreaFor(FIXED_WIDGET);
        assertThat(area, is(Rectangle.of(0, 0, 3, 6)));
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
        assertThat(area1, is(Rectangle.of(margin.left, 19 - margin.bottom, 6, 6)));
        assertThat(area2, is(Rectangle.of(6 + margin.left + margin.right, 21, 4, 4)));
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
        assertThat(area, is(Rectangle.of(22, 0, 6, 3)));
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
        assertThat(area, is(Rectangle.of(0, 0, 50, 10)));
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
        assertThat(area, is(Rectangle.of(0, 0, 15, 5)));

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
        assertThat(area1, is(Rectangle.of(margin.left, margin.top, 6, 6)));
        assertThat(area2, is(Rectangle.of(6 + margin.left + margin.right, 0, 4, 4)));
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
        assertThat(area, is(Rectangle.of(3, 0, 1, 1)));
    }

    @Test
    public void shouldConsiderGiveLessSpaceForWidgetWithMarginToFulfillMarginRequirements() throws Exception {
        // given
        layout = Layouts.horizontal(HorizontalAlign.RIGHT);
        layout.addWidget(FIXED_SMALL_WIDGET_MARGIN_MIN_SIZE);

        // when
        layout.setDimension(Dimension.of(5, 1));

        // then
        final Rectangle area = layout.getAreaFor(FIXED_SMALL_WIDGET_MARGIN_MIN_SIZE);
        assertThat(area, is(Rectangle.of(1, 0, 1, 1)));
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
        assertThat(area, is(Rectangle.of(0, 0, 30, 20)));
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

        assertThat(area1, is(Rectangle.of(0, 0, 6, 6)));
        assertThat(area2, is(Rectangle.of(6, 0, 44, 25)));
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

        assertThat(area1, is(Rectangle.of(0, 0, 15, 12)));
        assertThat(area2, is(Rectangle.of(15, 0, 35, 24)));
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

        assertThat(area1, is(Rectangle.of(0, 0, 15, 12)));
        assertThat(area2, is(Rectangle.of(15, 0, 6, 6)));
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

        assertThat(area1, is(Rectangle.of(0, 0, 25, 10)));
        assertThat(area2, is(Rectangle.of(25, 0, 25, 10)));
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

        assertThat(area1, is(Rectangle.of(20, 0, 6, 6)));
        assertThat(area2, is(Rectangle.of(26, 0, 4, 4)));
    }

    @Test
    public void shouldSqueezeMultipleWidgets() throws Exception {

        // given
        layout = Layouts.horizontal(HorizontalAlign.LEFT);

        final Widget widget1 = fixedWidget(4, 2);
        final Widget widget2 = fixedWidget(3, 2);
        final Widget widget3 = fixedWidget(5, 1);

        // when
        layout.addWidget(widget1);
        layout.addWidget(widget2);
        layout.addWidget(widget3);

        // then
        layout.setDimension(Dimension.of(9, 1));

        assertThat(layout.getAreaFor(widget1), is(Rectangle.of(0, 0, 3, 1)));
        assertThat(layout.getAreaFor(widget2), is(Rectangle.of(3, 0, 3, 1)));
        assertThat(layout.getAreaFor(widget3), is(Rectangle.of(6, 0, 3, 1)));

        // and
        layout.setDimension(Dimension.of(6, 1));

        assertThat(layout.getAreaFor(widget1), is(Rectangle.of(0, 0, 2, 1)));
        assertThat(layout.getAreaFor(widget2), is(Rectangle.of(2, 0, 2, 1)));
        assertThat(layout.getAreaFor(widget3), is(Rectangle.of(4, 0, 2, 1)));

        // and
        layout.setDimension(Dimension.of(4, 1));

        assertThat(layout.getAreaFor(widget1), is(Rectangle.of(0, 0, 2, 1)));
        assertThat(layout.getAreaFor(widget2), is(Rectangle.of(2, 0, 2, 1)));
        assertThat(layout.getAreaFor(widget3), is(Rectangle.of(4, 0, 1, 1)));
    }

    @Test
    public void shouldSqueezeMultipleWidgetsVertically() throws Exception {

        // given
        layout = Layouts.vertical(HorizontalAlign.CENTER, VerticalAlign.CENTER);

        final Widget widget1 = fixedVerticalWidget(4, 2);
        final Widget widget2 = fixedVerticalWidget(3, 2);
        final Widget widget3 = fixedVerticalWidget(5, 1);

        // when
        layout.addWidget(widget1);
        layout.addWidget(widget2);
        layout.addWidget(widget3);

        // then
        layout.setDimension(Dimension.of(9, 16));

        assertThat(layout.getAreaFor(widget1), is(Rectangle.of(2, 2, 5, 4)));
        assertThat(layout.getAreaFor(widget2), is(Rectangle.of(2, 6, 5, 3)));
        assertThat(layout.getAreaFor(widget3), is(Rectangle.of(2, 9, 5, 5)));

        // and
        layout.setDimension(Dimension.of(9, 9));

        assertThat(layout.getAreaFor(widget1), is(Rectangle.of(2, 0, 5, 3)));
        assertThat(layout.getAreaFor(widget2), is(Rectangle.of(2, 3, 5, 3)));
        assertThat(layout.getAreaFor(widget3), is(Rectangle.of(2, 6, 5, 3)));

        // and
        layout.setDimension(Dimension.of(5, 4));

        assertThat(layout.getAreaFor(widget1), is(Rectangle.of(0, 0, 5, 2)));
        assertThat(layout.getAreaFor(widget2), is(Rectangle.of(0, 2, 5, 2)));
        assertThat(layout.getAreaFor(widget3), is(Rectangle.of(0, 4, 5, 1)));
    }

    @Test
    public void shouldGiveAllAvailableVerticalSpaceForOneFillingWidget() throws Exception {

        // given
        layout = Layouts.vertical();

        final Widget widget = new DummyWidget(Occupation.percent(30),
                                              Occupation.fill(),
                                              Occupation.percent(30),
                                              Occupation.fixed(3),
                                              Occupation.percent(30),
                                              Occupation.fill(),
                                              Margin.no(),
                                              Position.RELATIVE);

        layout.addWidget(widget);

        // when
        layout.setDimension(Dimension.of(80, 25));

        // then
        assertThat(layout.getAreaFor(widget), is(Rectangle.of(0, 0, 24, 25)));
    }

    @Test
    public void shouldGiveAllAvailableHorizontallySpaceForOneFillingWidget() throws Exception {

        // given
        layout = Layouts.horizontal();

        final Widget widget = new DummyWidget(Occupation.fill(),
                                              Occupation.percent(30),
                                              Occupation.fixed(3),
                                              Occupation.percent(30),
                                              Occupation.fill(),
                                              Occupation.percent(30),
                                              Margin.no(),
                                              Position.RELATIVE);

        layout.addWidget(widget);

        // when
        layout.setDimension(Dimension.of(25, 80));

        // then
        assertThat(layout.getAreaFor(widget), is(Rectangle.of(0, 0, 25, 24)));
    }

    private Widget fixedWidget(final int preferredWidth, final int minWidth) {
        return new DummyWidget(fixed(preferredWidth), fixed(1), fixed(minWidth),
                               fixed(1), fixed(preferredWidth), fixed(1), Position.RELATIVE, true);
    }

    private Widget fixedVerticalWidget(final int preferredHeight, final int minHeight) {
        return new DummyWidget(fixed(5), fixed(preferredHeight),
                               fixed(5), fixed(minHeight),
                               fixed(5), fixed(preferredHeight),
                               Position.RELATIVE, true);
    }

}