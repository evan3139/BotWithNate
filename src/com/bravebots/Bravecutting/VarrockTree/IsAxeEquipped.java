package com.bravebots.Bravecutting.VarrockTree;

import com.runemate.game.api.hybrid.local.hud.interfaces.Equipment;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

//import path.to.your.IsInventoryFull
//import path.to.your.StopBot

/**
 * NOTES:
 * 
 */
public class IsAxeEquipped extends BranchTask {

    private IsInventoryFull isinventoryfull = new IsInventoryFull();
    private StopBot stopbot = new StopBot();
    String axe = "Bronze axe";

    @Override
    public boolean validate() {
        return Inventory.containsAnyOf(axe) || Equipment.containsAnyOf(axe);
    }

    @Override
    public TreeTask failureTask() {
        return stopbot;
    }

    @Override
    public TreeTask successTask() {
        return isinventoryfull;
    }
}
