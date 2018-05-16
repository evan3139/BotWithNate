package com.sudo.v3.spectre.common.leafs;

import com.runemate.game.api.hybrid.entities.LocatableEntity;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.region.Banks;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;
import com.sudo.v3.base.SudoBot;
import com.sudo.v3.spectre.common.SudoCamera;
import com.sudo.v3.spectre.statics.UpdateUI;

/**
 * Created by SudoPro on 11/23/2016.
 */
public class OpenBankLeaf extends LeafTask {
    private SudoBot bot;
    private LocatableEntity locatableEntity;
    private boolean genericBank = false, forceGeneric = false;

    public OpenBankLeaf(SudoBot bot) {
        this.bot = bot;
    }

    @Override
    public void execute() {

        if (forceGeneric) {
            locatableEntity = Banks.newQuery().results().nearest();
            genericBank = true;
        } else {
            locatableEntity = GameObjects.newQuery().actions("Bank").results().nearest();

            if (locatableEntity == null) {
                locatableEntity = Npcs.newQuery().actions("Bank").results().nearest();
            } else if (locatableEntity == null) {
                locatableEntity = Banks.newQuery().results().nearest();
                genericBank = true;
            }
        }

        if (locatableEntity != null) {
            UpdateUI.debug("Bank Entity: " + locatableEntity.toString());
            UpdateUI.currentTask("Opening bank", bot);
            if (locatableEntity.getVisibility() < .8 || bot.player.distanceTo(locatableEntity) > 6) {
                UpdateUI.debug(bot.player.getName() + " -> bankLoc: " + locatableEntity);
                SudoCamera.ConcurrentlyTurnToWithYaw(locatableEntity);
            }

            if (genericBank) {
                UpdateUI.debug("genericBank");
                if (Bank.open())
                    Execution.delayUntil(Bank::isOpen, 1000, 5000);
            } else if (locatableEntity.interact("Bank"))
                Execution.delayUntil(Bank::isOpen, 1000, 5000);
        } else
            UpdateUI.debug("Bank is null");
    }

    public void setGenericBank(boolean value) {
        forceGeneric = value;
    }
}