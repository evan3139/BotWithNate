package com.SilenceP44.bots.WillowBot.Leafs;

import com.SilenceP44.bots.WillowBot.SilenceWillow;
import com.runemate.game.api.hybrid.RuneScape;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;

/**
 * NOTES:
 * Goes to willow area
 */

public class GotoWillowArea extends LeafTask {

    private SilenceWillow bot;
    public GotoWillowArea(SilenceWillow bot) {
        this.bot = bot;
    }

    @Override
    public void execute() {
        if (SilenceWillow.getLumbridgeArea().contains(bot.getPlayer())) {
            bot.logText("In lumbrige Stopping bot");
            RuneScape.logout();
            Execution.delayWhile(RuneScape::isLoggedIn, 10,5000);
            bot.stop("In  lumbrige stopping bot");
        }
        RegionPath toWillow = RegionPath.buildTo(bot.getWillowsArea().getRandomCoordinate());
        if (toWillow != null) {
            bot.logText("Walking to Willow");
            toWillow.step(true);
            Execution.delay(200,5000,3000);
        } else {
            bot.logText("Path is null");
        }
    }
}
