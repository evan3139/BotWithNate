package com.sudo.v3.spectre.bots.exampleflaxpicker.leafs;

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.script.framework.tree.LeafTask;
import com.sudo.v3.spectre.bots.exampleflaxpicker.ExampleFlaxPicker;

/**
 * Created by SudoPro on 12/28/2016.
 */
public class DepositAllLeaf extends LeafTask {
    private ExampleFlaxPicker bot;

    public DepositAllLeaf(ExampleFlaxPicker bot) {
        this.bot = bot;
    }

    @Override
    public void execute() {
        bot.currentTaskString = "Depositing Inventory";
        Bank.depositInventory();
    }
}