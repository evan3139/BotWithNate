package com.ewildbots.cutting.varrock_oak;

import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

import path.to.your.IsBankOpen
import path.to.your.failsafehaveaxeequipped

/**
 * NOTES:
 * Are we in the Varrock Bank Region
 */
public class AreweInVarrockBank extends BranchTask {

    private IsBankOpen isbankopen = new IsBankOpen();
    private failsafehaveaxeequipped failsafehaveaxeequipped = new failsafehaveaxeequipped();

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public TreeTask failureTask() {
        return failsafehaveaxeequipped;
    }

    @Override
    public TreeTask successTask() {
        return isbankopen;
    }
}
