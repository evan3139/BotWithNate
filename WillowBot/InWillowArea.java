package com.SilenceP44.bots.WillowBot;

import com.runemate.game.api.hybrid.util.calculations.Distance;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

/**
 * NOTES:
 * Checks if we are in the Draynor willow area
 */

public class InWillowArea extends BranchTask {

    private SilenceWillow bot;
    InWillowArea(SilenceWillow bot) {
        this.bot = bot;
    }

    @Override
    public boolean validate() {
        Execution.delay(100,5000, 150);
        return bot.getPlayer() != null && Distance.between(bot.getInWillowArea(), bot.getPlayer()) < 20;
    }

    @Override
    public TreeTask failureTask() {
        return bot.gotowillowarea;
    }

    @Override
    public TreeTask successTask() {
        return bot.isaxeequipped;
    }
}
