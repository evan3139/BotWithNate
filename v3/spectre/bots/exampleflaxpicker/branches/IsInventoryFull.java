package com.sudo.v3.spectre.bots.exampleflaxpicker.branches;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;
import com.sudo.v3.spectre.bots.exampleflaxpicker.ExampleFlaxPicker;
import com.sudo.v3.spectre.bots.exampleflaxpicker.enums.BankingType;

/**
 * Created by SudoPro on 12/28/2016.
 *
 * We're extending BranchTask because this class decides if its 'validate' is true/false, and continues appropriately.
 * No action is actually being executing here. (Ex. We won't interact with a GameObject)
 *
 * The order of execution is validate() -> success/failure tasks
 */
public class IsInventoryFull extends BranchTask {
    // Lazy instantiate our bot class
    private ExampleFlaxPicker bot;

    // Take in the bot in the constructor so we can reference the bot class
    public IsInventoryFull(ExampleFlaxPicker bot) {
        this.bot = bot;
    }

    @Override
    public TreeTask successTask() {
        // If the Inventory is full, this code will execute

        // We will be banking our items, so set our BankingType to depositing
        bot.bankingType = BankingType.depositing;

        return bot.isBankOpenBranch;
    }

    @Override
    public boolean validate() {
        System.out.println("Branch IsInventoryFull: " + Inventory.isFull());
        return Inventory.isFull();
    }

    @Override
    public TreeTask failureTask() {
        // If the Inventory is NOT full, this code will execute

        // We will not be banking our items, so in case we're in the banking area, set BankingType to leaving
        bot.bankingType = BankingType.leaving;

        return bot.isBankOpenBranch;
    }
}