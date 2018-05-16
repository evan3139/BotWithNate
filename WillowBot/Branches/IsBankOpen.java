package com.SilenceP44.bots.WillowBot.Branches;

import com.SilenceP44.bots.WillowBot.SilenceWillow;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

/**
 * NOTES:
 * Checks if bank is open
 */

public class IsBankOpen extends BranchTask {

    private SilenceWillow bot;
    public IsBankOpen(SilenceWillow bot) {
        this.bot = bot;
    }

    @Override
    public boolean validate() {
        return Bank.isOpen();
    }

    @Override
    public TreeTask failureTask() {
        return bot.openbank;
    }

    @Override
    public TreeTask successTask() {
        return bot.depositwillowlogs;
    }
}
