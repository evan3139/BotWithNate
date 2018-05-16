package com.SilenceP44.bots.WillowBot.Leafs;

import      com.SilenceP44.bots.WillowBot.SilenceWillow;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;

/**
 * NOTES:
 * Opens the bank
 */

public class OpenBank extends LeafTask {

    private SilenceWillow bot;
    public OpenBank(SilenceWillow bot) {
        this.bot = bot;
    }

    @Override
    public void execute() {
        if (!bot.getBankArea().contains(bot.getPlayer())) {
            RegionPath toBankArea = RegionPath.buildTo(bot.getBankArea().getRandomCoordinate());
            if (toBankArea != null) {
                bot.logText("Walking to bank");
                toBankArea.step(true);
                Execution.delayUntil(() -> (bot.getPlayer() != null && !bot.getPlayer().isMoving()), 100, 2000);
            } else {
                bot.logText("Path is null");
            }
        }

        GameObject bankChest = GameObjects.newQuery().on(bot.getBankCoordinate()).names("Bank booth").actions("Bank").results().first();
        if (bankChest != null) {
            if (bankChest.isVisible()) {
                if (bankChest.interact("Bank")) {
                    bot.logText("Opening Bank");
                    Execution.delayUntil(Bank::isOpen, () -> (bot.getPlayer()) != null && bot.getPlayer().isMoving(), 100, 2000);
                }
            } else {
                Camera.turnTo(bankChest);
            }
        } else {
            bot.logText("No bank booth found.");
        }
    }
}
