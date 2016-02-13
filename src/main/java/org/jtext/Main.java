package org.jtext;

import org.jtext.curses.*;
import org.jtext.event.EventBus;
import org.jtext.keyboard.KeyboardHandler;
import org.jtext.ui.attribute.Align;
import org.jtext.ui.attribute.Border;
import org.jtext.ui.attribute.Padding;
import org.jtext.ui.graphics.Container;
import org.jtext.ui.graphics.OccupationType;
import org.jtext.ui.graphics.Scene;
import org.jtext.ui.layout.Layouts;
import org.jtext.ui.widget.Label;
import org.jtext.ui.widget.Panel;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        LibraryLoader.load();
        CursesDriver driver = new CursesDriver();
        driver.init();
        final EventBus eventBus = new EventBus();
        final KeyboardHandler keyboardHandler = new KeyboardHandler(driver, newSingleThreadExecutor(), eventBus);
        final Scene scene = new Scene(driver, eventBus);

        final Container mainContainer = new Container(Layouts.vertical(Align.TOP));

        final Panel top = new Panel(
                Layouts.horizontal(Align.LEFT),
                OccupationType.fill(),
                OccupationType.fixed(1));

        final Container center = new Container(Layouts.horizontal(Align.TOP));

        final Panel left = new Panel(
                Layouts.vertical(Align.LEFT),
                OccupationType.proportional(30),
                OccupationType.fill(),
                Border.SINGLE,
                Padding.of(1),
                CharacterColor.BLUE
        );

        final Label labelLeft = new Label(
                CellDescriptor.builder()
                        .fg(CharacterColor.WHITE)
                        .bg(CharacterColor.BLUE)
                        .attr(CharacterAttribute.BOLD)
                        .create(),
                "Left Panel!");

        left.add(labelLeft);

        final Panel right = new Panel(
                Layouts.vertical(Align.RIGHT),
                OccupationType.fill(),
                OccupationType.fill(),
                Border.NO_BORDER,
                Padding.of(1),
                CharacterColor.CYAN
        );

        final Label labelRigth = new Label(
                CellDescriptor.builder()
                        .fg(CharacterColor.WHITE)
                        .bg(CharacterColor.CYAN)
                        .attr(CharacterAttribute.BOLD)
                        .create(),
                "Right Panel!");

        left.add(labelRigth);

        center.add(left);
        center.add(right);

        final Panel bottom = new Panel(
                Layouts.vertical(Align.LEFT),
                OccupationType.fill(),
                OccupationType.fixed(1)
        );


        mainContainer.add(top);
        mainContainer.add(center);
        mainContainer.add(bottom);

        scene.add(mainContainer);

        scene.start();
        keyboardHandler.start();

    }

}
