package com.sudo.v3.spectre.common;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.hud.InteractablePoint;
import com.runemate.game.api.hybrid.local.hud.Menu;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceWindows;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.queries.results.SpriteItemQueryResults;
import com.runemate.game.api.hybrid.util.Regex;
import com.runemate.game.api.script.Execution;

import java.util.HashMap;

import static java.awt.event.KeyEvent.VK_SHIFT;

/**
 * Created by SudoPro on 7/10/2016.
 */
public class SudoInventory {

    public static boolean drop(String name) {
        SpriteItem item = Inventory.newQuery().names(name).actions("Drop").results().first();
        if (item != null) {
            if (!Menu.isOpen()) {
                if (item.hover()) {
                    if (Menu.open()) {
                        Execution.delayUntil(Menu::isOpen, 400, 600);
                        if (Menu.contains("Drop")) {
                            if (Menu.getItem("Drop").hover())
                                if(Menu.getItem("Drop").click())
                                    return true;
                                else return false;
                            else return false;
                        } else {
                            Menu.close();
                            return false;
                        }
                    } else return false;
                }
            } else {
                if (Menu.contains("Drop")) {
                    if (Menu.getItem("Drop").click()) {
                        return true;
                    } else return false;
                } else {
                    Menu.close();
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean drop(SpriteItem item) {
        if (item != null && item.getDefinition() != null && item.getDefinition().getName() != null) {
            if (Inventory.contains(item.getDefinition().getName())) {
                if (!Menu.isOpen()) {
                    if (Mouse.move(item)) {
                        if (Menu.open()) {
                            Execution.delayUntil(Menu::isOpen, 400, 600);
                            if (Menu.contains("Drop")) {
                                if (Menu.getItem("Drop").hover())
                                    if(Mouse.click(Mouse.Button.LEFT))
                                        return true;
                                    else return false;
                                else return false;
                            } else {
                                Menu.close();
                                return false;
                            }
                        } else return false;
                    } else return false;
                } else {
                    if (Menu.contains("Drop")) {
                        if (Menu.getItem("Drop").click()) {
                            return true;
                        } else return false;
                    } else {
                        Menu.close();
                        return false;
                    }
                }
            } else
                return false;
        } else
            return false;
    }

    public static boolean dropAll(String... name) {
        if (Inventory.containsAnyOf(name)) {
            SpriteItemQueryResults results = Inventory.newQuery().names(name).results();

            for (int i = 0; i < results.size(); ) {
                if (drop(results.get(i)))
                    i++;
            }
            return true;
        } else
            return false;
    }

    public static boolean shiftDropAll(String... name) {
        if (Inventory.containsAnyOf(name)) {

            if(!InterfaceWindows.getInventory().isOpen())
                InterfaceWindows.getInventory().open();

            // Timer to prevent infinite loop
            SudoTimer timer = new SudoTimer(10000, 15000);
            timer.start();

            SpriteItemQueryResults results = Inventory.newQuery().names(name).results();

            while(results.size() > 0 && !timer.hasExpired()) {
                results.forEach(i -> {
                    shiftDrop(i);
                });

                results = Inventory.newQuery().names(name).results();
            }

            Keyboard.releaseKey(VK_SHIFT);
            return true;
        } else
            return false;
    }

    public static boolean shiftDropAllContaining(String partial) {

        SpriteItemQueryResults results = Inventory.newQuery().names(Regex.getPatternForContainsString(partial)).results();

        if (results.size() > 0) {

            if(!InterfaceWindows.getInventory().isOpen())
                InterfaceWindows.getInventory().open();

            // Timer to prevent infinite loop
            SudoTimer timer = new SudoTimer(10000, 15000);
            timer.start();

            while(results.size() > 0 && !timer.hasExpired()) {
                results.forEach(i -> {
                    shiftDrop(i);
                });

                results = Inventory.newQuery().names(Regex.getPatternForContainsString(partial)).results();
            }

            Keyboard.releaseKey(VK_SHIFT);
            return !timer.hasExpired();
        } else
            return false;
    }

    private static boolean shiftDrop(SpriteItem item) {
        if (!Keyboard.isPressed(VK_SHIFT)) {
            Environment.getBot().getLogger().info("Pressing shift");
            Keyboard.pressKey(VK_SHIFT);
        }

        if (item != null && item.getBounds() != null) {
            InteractablePoint point = new InteractablePoint((int) ((int) (Math.random() * item.getBounds().getWidth() / 2) + (item.getBounds().getCenterPoint().getX() - (item.getBounds().getWidth() / 4))),
                    (int) ((int) (Math.random() * (item.getBounds().getHeight() / 2)) + (item.getBounds().getCenterPoint().getY() - (item.getBounds().getHeight() / 4))));

            Mouse.move(point);
            Mouse.click(Mouse.Button.LEFT);
            Execution.delay(75, 150, 80);
            return true;
        } else {
            Environment.getBot().getLogger().warn("Current item or it's bounds were null");
            return false;
        }
    }

//    public static void shiftDropAll2(String... names) {
//        ArrayList<InteractableRectangle> alreadyClicked = new ArrayList<>(), reattempted = new ArrayList<>();
//        InteractableRectangle currentBounds;
//
//        if (!Keyboard.isPressed(VK_SHIFT)) {
//            Environment.getBot().getLogger().info("Pressing shift");
//            Keyboard.pressKey(VK_SHIFT);
//        }
//
//        // Timer to prevent infinite loop
//        SudoTimer timer = new SudoTimer(7500, 10000);
//        timer.start();
//
//        List<SpriteItem> itemList = Inventory.newQuery().names(names).results().asList();
//
//        if (itemList != null) {
//            while(itemList.size() > 0 && !timer.hasExpired()) {
//                for (SpriteItem item : itemList) {
//                    if (item != null && item.isValid() && (currentBounds = item.getBounds()) != null) {
//                        if (alreadyClicked.contains(currentBounds)) {
//                            Environment.getBot().getLogger().info("We have already clicked this item");
//                            if (!reattempted.contains(currentBounds)) {
//                                reattempted.add(currentBounds);
//                                if (reattempted.size() >= 26) {
//                                    alreadyClicked.clear();
//                                    reattempted.clear();
//                                }
//                            }
//                        } else {
//                            alreadyClicked.add(currentBounds);
//                            Mouse.move(item);
//                            Mouse.click(Mouse.Button.LEFT);
//                            //Execution.delay(45, 140, 70);
//                            break;
//                        }
//                    } else {
//                        Environment.getBot().getLogger().warn("Current item or it's bounds were null");
//                    }
//                }
//
//                itemList = Inventory.newQuery().names(names).results().asList();
//            }
//        }
//    }
}