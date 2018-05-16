package com.squidl.bots.HosidiusThieving.tasks;

import com.runemate.game.api.hybrid.entities.definitions.ItemDefinition;
import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.script.framework.task.Task;

import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Created by Dank on 9/10/2017.
 */
public class Dropfruit extends Task {
    @Override
    public boolean validate() {
        return Inventory.isFull();
    }

    @Override
    public void execute() {
        if (!Keyboard.isPressed(KeyEvent.VK_SHIFT)) {
            Keyboard.pressKey(KeyEvent.VK_SHIFT);
        }
        int strangeFruitQuantity = Inventory.getQuantity("Strange fruit");
        List<SpriteItem> listOfItems = Inventory.getItems().asList();
        if (strangeFruitQuantity >= 4) {
            listOfItems.forEach(a -> {
                ItemDefinition def = a.getDefinition();
                if (def != null && (Inventory.getQuantity("Strange fruit") > 4 || !def.getName().equals("Strange fruit")))
                    a.click();
            });
            if (Keyboard.isPressed(KeyEvent.VK_SHIFT)) {
                Keyboard.releaseKey(KeyEvent.VK_SHIFT);
            }
        } else {
            listOfItems.forEach(a -> {
                ItemDefinition def = a.getDefinition();
                if (def != null && !def.getName().equals("Strange fruit")) a.click();
            });
            if (Keyboard.isPressed(KeyEvent.VK_SHIFT)) {
                Keyboard.releaseKey(KeyEvent.VK_SHIFT);
            }
        }

    }
}
