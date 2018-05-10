package com.SilenceP44.bots.WillowBot.Leafs;

import com.SilenceP44.bots.WillowBot.SilenceWillow;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;

/**
 * NOTES:
 * Cut willows
 */

public class CutWillow extends LeafTask {

    private SilenceWillow bot;
    public CutWillow(SilenceWillow bot) {
        this.bot = bot;
    }

    @Override
    public void execute() {
        if (Bank.isOpen()) {
            if (Bank.close()) {
                bot.logText("Closing bank");
                Execution.delayWhile(Bank::isOpen);
            } else {
                bot.logText("Failed to close bank");
            }
        }
        if (!bot.getWillowsArea().contains(bot.getPlayer())) {
            RegionPath toWillow = RegionPath.buildTo(bot.getWillowsArea().getRandomCoordinate());
            if (toWillow != null) {
                if (toWillow.step(true)) {
                    bot.logText("Walking to WillowArea");
                    Execution.delayUntil(() ->  (bot.getPlayer() != null && bot.getPlayer().getAnimationId() == -1) || GameObjects.newQuery().within(bot.getWillowsArea()).names(bot.getTreeName()).results().nearest().isVisible(), 100, 5000);
                } else {
                    bot.logText("Failed to step");
                }
            } else {
                bot.logText("toWillow is null");
            }
        }

        if (!Bank.isOpen()) {
            GameObject cutableWillow = GameObjects.newQuery().within(bot.getWillowsArea()).names(bot.getTreeName()).results().nearest();
            if (cutableWillow != null) {
                bot.setWoodCuttingXPBefore(Skill.WOODCUTTING.getExperience());
                if (!cutableWillow.isVisible()) {
                    if (Camera.turnTo(cutableWillow)) {
                        bot.logText("Turning camera to willow");
                        Execution.delayUntil(cutableWillow::isVisible, 100, 5000);
                    } else {
                        bot.logText("Failed to turn camera");
                    }
                }

                if (cutableWillow.interact("Chop down")) {
                    // bot.logText("Cutting Willow");
                    Execution.delayWhile(() -> GameObjects.newQuery().on(cutableWillow.getPosition()).names("Tree Stump").results().isEmpty() && !Inventory.isFull() && (ChatDialog.getContinue() == null), 300, 60000);
                } else {
                    bot.logText("No chop down option found.");
                }
            } else {
                bot.logText("No cutable Willows found.");
            }
        } else {
            bot.logText("Bank is still open?");
        }
    }
}
