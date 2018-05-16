package com.bravebots.Bravecutting.VarrockTree;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

//import path.to.your.IsBankOpen
//import path.to.your.CutTree

/**
 * NOTES:
 * 
 */
public class IsInventoryFull extends BranchTask {

    private IsBankOpen isbankopen = new IsBankOpen();
    private CutTree cuttree = new CutTree();

    @Override
    public boolean validate() {
        return Inventory.getEmptySlots() == 0;
    }

    @Override
    public TreeTask failureTask() {
        return cuttree;
    }

    @Override
    public TreeTask successTask() {
        return isbankopen;
    }
}
