package com.squidl.bots.HosidiusThieving.tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;

/**
 * Created by Dank on 9/10/2017.
 */
public class Eatfruit extends Task {

    private SpriteItem strangeFruit;

    @Override
    public boolean validate() {
        strangeFruit = Inventory.newQuery().names("Strange fruit").results().first();
        return Traversal.getRunEnergy() < 70 && strangeFruit != null;
    }

    @Override
    public void execute() {
        if (strangeFruit.interact("Eat")) {
            Execution.delayWhile(() -> strangeFruit.isValid(), 3000, 5000);
        }
    }
}
