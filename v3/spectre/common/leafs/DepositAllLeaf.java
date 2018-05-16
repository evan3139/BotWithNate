package com.sudo.v3.spectre.common.leafs;

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.script.framework.tree.LeafTask;
import com.sudo.v3.base.SudoBot;
import com.sudo.v3.spectre.statics.UpdateUI;

/**
 * Created by Proxify on 9/18/2017.
 */
public class DepositAllLeaf extends LeafTask {
    private SudoBot bot;

    public DepositAllLeaf(SudoBot bot){
        this.bot = bot;
    }

    @Override
    public void execute() {
        UpdateUI.currentTask("Depositing Inventory", bot);
        Bank.depositInventory();
    }
}
