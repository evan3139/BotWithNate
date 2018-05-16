package com.SilenceP44.bots.WillowBot.Leafs;

import com.SilenceP44.bots.WillowBot.SilenceWillow;
import com.runemate.game.api.script.framework.tree.LeafTask;

/**
 * NOTES:
 * Stops the bot
 */

public class StopBot extends LeafTask {

    private SilenceWillow bot;
    public StopBot(SilenceWillow bot) {
        this.bot = bot;
    }

    @Override
    public void execute() {
        bot.stop("Bot stopped: Unknown reason");
    }
}
