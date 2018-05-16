package com.SilenceP44.bots.WillowBot.Branches;

import com.SilenceP44.bots.WillowBot.SilenceWillow;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

/**
 * NOTES:
 * Check if inventory is full
 */

public class IsInventoryFull extends BranchTask {

    private SilenceWillow bot;
    public IsInventoryFull(SilenceWillow bot) {
        this.bot = bot;
    }


    @Override
    public boolean validate() {
        return Inventory.getEmptySlots() == 0;
    }

    @Override
    public TreeTask failureTask() {
        return bot.cutwillow;
    }

    @Override
    public TreeTask successTask() {
        return bot.isbankopen;
    }
}
