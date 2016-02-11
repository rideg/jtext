package org.jtext;

import org.jtext.curses.CursesDriver;
import org.jtext.curses.LibraryLoader;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        LibraryLoader.load();
        CursesDriver driver = new CursesDriver();
        driver.init();

    }
//        int screenWidth = driver.getScreenWidth();
//        int screenHeight = driver.getScreenHeight();
//        Dimension dim = Dimension.of(30, 15);
//
//        Point p = Point.at((screenWidth - dim.width) / 2, (screenHeight - dim.height) / 2);
//
//
//        final CursesWindow top = new CursesWindow(driver, new WindowState(Rectangle.of(p, dim), 5));
//
//        int twoThird = 2 * screenWidth / 3;
//        int sTwoThird = 2 * screenHeight / 3;
//        final CursesWindow upperLeft =
//                new CursesWindow(driver, new WindowState(Rectangle.of(0, 0, twoThird, sTwoThird), 1));
//        final CursesWindow bottomLeft = new CursesWindow(driver,
//                                                         new WindowState(Rectangle.of(
//                                                                 0,
//                                                                 upperLeft.getArea().height,
//                                                                 upperLeft.getArea().width,
//                                                                 screenHeight - upperLeft.getArea().height
//                                                                                     ), 2));
//        final CursesWindow right = new CursesWindow(driver,
//                                                    new WindowState(Rectangle.of(
//                                                            upperLeft.getArea().width,
//                                                            0,
//                                                            screenWidth - upperLeft.getArea().width,
//                                                            screenHeight
//                                                                                ), 3, true));
//
//
//        final ZIndexRefreshStrategy manager = new ZIndexRefreshStrategy(driver);
//
//        manager.addWindow(top);
//        manager.addWindow(upperLeft);
//        manager.addWindow(bottomLeft);
//        manager.addWindow(right);
//
//
//        manager.refresh();
//
//        while (true) {
//            ReadKey ch = driver.getCh();
//            if (ch.getValue() == 'q') {
//                break;
//            }
//            if (ch.key() == ControlKey.ERR) {
//                Thread.sleep(1);
//                continue;
//            }
//            if (ch.key() == ControlKey.LEFT) {
//                p = p.decX();
//                top.move(p);
//            }
//            if (ch.key() == ControlKey.RIGHT) {
//                p = p.incX();
//                top.move(p);
//            }
//            if (ch.key() == ControlKey.UP) {
//                p = p.decY();
//                top.move(p);
//            }
//            if (ch.key() == ControlKey.DOWN) {
//                p = p.incY();
//                top.move(p);
//            }
//            if (ch.key() == ControlKey.SHIFT_RIGHT) {
//                dim = dim.incWidth();
//                top.resize(dim);
//            }
//            if (ch.key() == ControlKey.SHIFT_LEFT) {
//                dim = dim.decWidth();
//                top.resize(dim);
//            }
//            drawWindows(upperLeft, bottomLeft, right);
//            drawWindow(top, ch);
//            manager.refresh();
//        }
//
//        driver.shutdown();
//
//    }
//
//    private static void drawWindows(CursesWindow upperLeft, CursesWindow bottomLeft, CursesWindow right) {
//        upperLeft.fillBackground(CellDescriptor.builder().bg(CharacterColor.GREEN).create());
//        bottomLeft.fillBackground(CellDescriptor.builder().bg(CharacterColor.MAGENTA).create());
//        right.fillBackground(CellDescriptor.builder().bg(CharacterColor.YELLOW).fg(CharacterColor.BLACK).create());
//
//        right.setColor(CharacterColor.WHITE, CharacterColor.BLACK);
//        right.onAttribute(CharacterAttribute.BOLD);
//        right.drawVerticalLineAt(Point.at(0, 0), '│', right.getArea().height);
//        right.putCharAt(Point.at(0, upperLeft.getArea().height), '┤');
//
//        bottomLeft.setColor(CharacterColor.WHITE, CharacterColor.BLACK);
//        bottomLeft.onAttribute(CharacterAttribute.BOLD);
//        bottomLeft.drawHorizontalLineAt(Point.at(0, 0), '─', bottomLeft.getArea().width);
//    }
//
//
//    private static void drawWindow(CursesWindow top, ReadKey ch) {
//        top.clear();
//
//        final CellDescriptor prototype = CellDescriptor.builder()
//                                                 .bg(CharacterColor.BLACK)
//                                                 .fg(CharacterColor.WHITE)
//                                                 .attr(CharacterAttribute.BOLD)
//                                                 .create();
//
//        top.fillBackground(CellDescriptor.builder().bg(CharacterColor.BLUE).create());
//
//        top.drawBox(Border.DOUBLE.changeCell(prototype));
//
//        top.setForegroundColor(CharacterColor.GREEN);
//        top.onAttribute(CharacterAttribute.UNDERLINE);
//        top.printStringAt(Point.at(2, 2), ch.key().name());
//    }


}
