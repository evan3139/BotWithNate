package com.sudo.v3.spectre.bots.exampleflaxpicker.branches;

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;
import com.sudo.v3.spectre.bots.exampleflaxpicker.ExampleFlaxPicker;
import com.sudo.v3.spectre.bots.exampleflaxpicker.enums.BankingType;
import com.sudo.v3.spectre.bots.exampleflaxpicker.enums.TraversalLocation;
import com.sudo.v3.spectre.bots.exampleflaxpicker.leafs.DepositAllLeaf;
import com.sudo.v3.spectre.bots.exampleflaxpicker.leafs.EmptyLeaf;
import com.sudo.v3.spectre.bots.exampleflaxpicker.enums.Locatables;
import com.sudo.v3.spectre.bots.exampleflaxpicker.leafs.TraversalLeaf;

/**
 * Created by SudoPro on 12/28/2016.
 *
 * In this class, we will be representing two different tree branches in one class.
 * The reason we're doing this is to reduce clutter and prevent confusing class names.
 *
 * We'll accomplish this by using the BankingType enumeration.
 */
public class IsBankOpen extends BranchTask {
    private ExampleFlaxPicker bot;


    public IsBankOpen(ExampleFlaxPicker bot) {
        this.bot = bot;
    }

    @Override
    public TreeTask successTask() {
        // If the bank window IS open, we handle our next action appropriately.
        // In this case, our inventory IS NOT full (handled in IsInventoryFull branch) and our bank window IS open.
        //    Since we're not needing to deposit anything, we can freely leave the bank and travel to the flax area
        if (bot.bankingType == BankingType.leaving) {
            bot.traversalLocation = TraversalLocation.flaxArea;
            return bot.traversalLeaf;
        }

        // In this case, our inventory IS full (handled in IsInventoryFull branch) and our bank window IS open.
        //    We would now deposit our inventory.
        else if (bot.bankingType == BankingType.depositing)
            return bot.depositAllLeaf;
        else
            return bot.emptyLeaf;
    }

    @Override
    public boolean validate() {
        // Determine whether the bank window is open or not
        System.out.println("Branch IsBankOpen(" + bot.bankingType + "): " + Bank.isOpen());
        return Bank.isOpen();
    }

    @Override
    public TreeTask failureTask() {
        // If the bank window IS NOT open, we handle our next action appropriately.
        // In this case, our inventory IS NOT full (handled in IsInventoryFull branch) and our bank window is not open,
        //    therefore we must see if flax is around.
        if (bot.bankingType == BankingType.leaving) {
            bot.itemToLocate = Locatables.flax;
            return bot.isNearbyBranch;
        }

        // In this case, our inventory IS full (handled in IsInventoryFullBranch) and our bank window is not open,
        //     therefore we must see if the bank is around us so we can deposit.
        else if (bot.bankingType == BankingType.depositing) {
            bot.itemToLocate = Locatables.bank;
            return bot.isNearbyBranch;
        } else
            return bot.emptyLeaf;
    }
}