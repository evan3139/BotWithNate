package com.bravebots.Bravecutting.VarrockTree;

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;

/**
 * NOTES:
 * 
 */
public class DepositUnwanted extends LeafTask {

    @Override
    public void execute() {
        if(Bank.depositAllExcept("Bronze axe")){
            Execution.delayUntil(() -> Inventory.containsOnly("Bronze axe") , 100, 3000);
        }
        if(Bank.open()){
            if(Bank.close())
            {
                Execution.delayWhile(Bank::isOpen, 150, 3000);
            }
        }

    }
}
