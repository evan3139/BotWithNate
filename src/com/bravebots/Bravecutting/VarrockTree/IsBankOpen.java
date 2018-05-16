package com.bravebots.Bravecutting.VarrockTree;

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

//import path.to.your.DepositUnwanted
//import path.to.your.OpenBank

/**
 * NOTES:
 * 
 */
public class IsBankOpen extends BranchTask {

    private DepositUnwanted depositunwanted = new DepositUnwanted();
    private OpenBank openbank = new OpenBank();

    @Override
    public boolean validate() {
        return Bank.isOpen();
    }

    @Override
    public TreeTask failureTask() {
        return openbank;
    }

    @Override
    public TreeTask successTask() {
        return depositunwanted;
    }
}
