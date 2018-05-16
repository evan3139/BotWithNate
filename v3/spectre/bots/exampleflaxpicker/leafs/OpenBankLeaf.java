package com.sudo.v3.spectre.bots.exampleflaxpicker.leafs;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.entities.details.Interactable;
import com.runemate.game.api.hybrid.entities.details.Locatable;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;
import com.sudo.v3.spectre.bots.exampleflaxpicker.ExampleFlaxPicker;
import com.sudo.v3.spectre.statics.UpdateUI;

/**
 * Created by SudoPro on 11/23/2016.
 */
public class OpenBankLeaf extends LeafTask {
    private ExampleFlaxPicker bot;
    private GameObject obj;
    private Npc npc;
    private Interactable bank;
    private Locatable bankLoc;

    public OpenBankLeaf(ExampleFlaxPicker bot) {
        this.bot = bot;
        obj = GameObjects.newQuery().actions("Bank").results().nearest();
        if (obj != null) {
            bankLoc = obj.getPosition();
            bank = obj;
        } else {
            npc = Npcs.newQuery().actions("Bank").results().nearest();
            if (npc != null) {
                bankLoc = npc.getPosition();
                bank = npc;
            }
        }
    }

    @Override
    public void execute() {
        bot.currentTaskString = "Opening Bank";
        if (bank != null) {
            if (!bank.isVisible() || Players.getLocal().distanceTo(bankLoc) > 6) {
                UpdateUI.debug(Players.getLocal().getName() + " -> bankLoc: " + bankLoc);
                Camera.turnTo(bankLoc);
            }
            if (Bank.open())
                Execution.delayUntil(() -> Bank.isOpen(), 1000, 2000);
        }
    }
}