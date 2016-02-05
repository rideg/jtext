package org.jtext;

import org.jtext.curses.*;
import org.jtext.ui.attribute.Border;
import org.jtext.ui.graphics.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        LibraryLoader.load();
        CursesDriver driver = new CursesDriver();
        driver.init();


        int screenWidth = driver.getScreenWidth();
        int screenHeight = driver.getScreenHeight();
        int width = 30;
        int height = 15;


        Point p = Point.at((screenWidth - width) / 2, (screenHeight - height) / 2);


        final CursesWindow top = new CursesWindow(driver, new WindowState(Rectangle.of(p, width, height), 5));
        int twoThird = 2 * screenWidth / 3;

        final CursesWindow upperLeft = new CursesWindow(driver, new WindowState(Rectangle.of(0, 0, twoThird, screenHeight - 30), 1));
        final CursesWindow bottomLeft = new CursesWindow(driver, new WindowState(Rectangle.of(0, upperLeft.getArea().height - 1, upperLeft.getArea().width, 10), 1));
//        final CursesWindow right = new CursesWindow(driver, new WindowState(Rectangle.of(upperLeft.getArea().width, 0, screenWidth - upperLeft.getArea().width, screenHeight), 1, true));


        final WindowLayoutManager manager = new WindowLayoutManager(driver);

        manager.addWindow(top);
        manager.addWindow(upperLeft);
        manager.addWindow(bottomLeft);
//        manager.addWindow(right);


        upperLeft.setBackground(CellDescriptor.builder().bg(CharacterColor.GREEN).create());
        bottomLeft.setBackground(CellDescriptor.builder().bg(CharacterColor.MAGENTA).create());
//        right.setBackground(CellDescriptor.builder().bg(CharacterColor.YELLOW).fg(CharacterColor.WHITE).create());
//        right.drawBox(Border.SINGLE.vertical, ' ', ' ', ' ', ' ', ' ', Border.SINGLE.vertical, Border.SINGLE.vertical);

        manager.refresh();

        while (true) {
            ReadKey ch = driver.getCh();
            if (ch.getValue() == 'q') {
                break;
            }
            if (ch.key() == ControlKey.ERR) {
                Thread.sleep(1);
                continue;
            }
            if (ch.key() == ControlKey.LEFT) {
                p = p.decX();
                top.move(p.decX());
            }
            if (ch.key() == ControlKey.RIGHT) {
                p = p.incX();
                top.move(p);
            }
            if (ch.key() == ControlKey.SHIFT_RIGHT) {
                width++;
                top.resize(width, height);
            }
            if (ch.key() == ControlKey.SHIFT_LEFT) {
                width--;
                top.resize(width, height);
            }
            drawWindow(top, ch);
            manager.refresh();
        }

        driver.shutdown();

    }

    private static void drawWindow(CursesWindow top, ReadKey ch) {
        top.clear();

        final CellDescriptor prototype = CellDescriptor.builder()
                .bg(CharacterColor.BLACK)
                .fg(CharacterColor.WHITE)
                .attr(CharacterAttribute.BOLD)
                .create();

        top.setBackground(CellDescriptor.builder().bg(CharacterColor.BLUE).create());

        top.drawBox(prototype.ch(Border.SINGLE.topLeft),
                prototype.ch(Border.SINGLE.horizontal),
                prototype.ch(Border.SINGLE.topRight),
                prototype.ch(Border.SINGLE.vertical),
                prototype.ch(Border.SINGLE.bottomRight),
                prototype.ch(Border.SINGLE.horizontal),
                prototype.ch(Border.SINGLE.bottomLeft),
                prototype.ch(Border.SINGLE.vertical));

        top.setForegroundColor(CharacterColor.GREEN);
        top.onAttribute(CharacterAttribute.UNDERLINE);
        top.printStringAt(Point.at(2, 2), ch.key().name());
    }


}
