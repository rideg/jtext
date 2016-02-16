package org.jtext;

import org.jtext.curses.*;
import org.jtext.event.EventBus;
import org.jtext.keyboard.KeyboardHandler;
import org.jtext.ui.attribute.Border;
import org.jtext.ui.attribute.HorizontalAlign;
import org.jtext.ui.attribute.Padding;
import org.jtext.ui.attribute.VerticalAlign;
import org.jtext.ui.graphics.Container;
import org.jtext.ui.graphics.Occupation;
import org.jtext.ui.graphics.Scene;
import org.jtext.ui.layout.Layouts;
import org.jtext.ui.widget.Label;
import org.jtext.ui.widget.Panel;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        LibraryLoader.load();
        CursesDriver driver = new CursesDriver();
        driver.init();
        final EventBus eventBus = new EventBus();
        final KeyboardHandler keyboardHandler = new KeyboardHandler(driver, newSingleThreadExecutor(), eventBus);
        final Scene scene = new Scene(driver, eventBus);

        final Container mainContainer = new Container(Layouts.vertical(VerticalAlign.TOP));

        final Panel top = new Panel(
                Layouts.horizontal(HorizontalAlign.LEFT),
                Occupation.fill(),
                Occupation.fixed(1),
                Border.no(),
                Padding.horizontal(1));

        final Container center = new Container(Layouts.horizontal(VerticalAlign.TOP));

        final Panel left = new Panel(Layouts.vertical(HorizontalAlign.LEFT),
                Occupation.percent(30),
                Occupation.fill(),
                Border.single(),
                Padding.full(1),
                CharacterColor.BLUE);

        final Label labelLeft = new Label(CellDescriptor.builder()
                .fg(CharacterColor.WHITE)
                .bg(CharacterColor.BLUE)
                .attr(CharacterAttribute.BOLD)
                .create(),
                "Left Panel!");

        left.add(labelLeft);

        final Panel right = new Panel(
                Layouts.vertical(HorizontalAlign.RIGHT),
                Occupation.fill(),
                Occupation.fill(),
                Border.no(),
                Padding.full(1),
                CharacterColor.CYAN
        );

        final Label labelRight = new Label(
                CellDescriptor.builder()
                        .fg(CharacterColor.WHITE)
                        .bg(CharacterColor.CYAN)
                        .attr(CharacterAttribute.BOLD)
                        .create(),
                "Right Panel!");

        left.add(labelRight);

        center.add(left);
        center.add(right);

        final Panel bottom = new Panel(
                Layouts.vertical(HorizontalAlign.LEFT),
                Occupation.fill(),
                Occupation.fixed(1)
        );


        mainContainer.add(top);
        mainContainer.add(center);
        mainContainer.add(bottom);

        scene.add(mainContainer);

        scene.start();
        keyboardHandler.start();

        TimeUnit.SECONDS.sleep(5);

        driver.shutdown();
    }

}
