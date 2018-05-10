package com.SilenceP44.bots.WillowBot.Branches;

import com.SilenceP44.bots.WillowBot.SilenceWillow;
import com.runemate.game.api.hybrid.local.hud.interfaces.Equipment;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

/**
 * NOTES:
 * Checks if we have an axe (equipped)
 */
public class IsAxeEquipped extends BranchTask {

    private SilenceWillow bot;
    public IsAxeEquipped(SilenceWillow bot) {
        this.bot = bot;
    }

    @Override
    public boolean validate() {
        return Equipment.containsAnyOf(bot.getAxePattern()) || Inventory.contains(bot.getAxePattern());
    }

    @Override
    public TreeTask failureTask() {
        return bot.stopbot;
    }

    @Override
    public TreeTask successTask() {
        return bot.isinventoryfull;
    }
}
