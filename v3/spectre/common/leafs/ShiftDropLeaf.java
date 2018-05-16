package com.sudo.v3.spectre.common.leafs;

import com.runemate.game.api.script.framework.tree.LeafTask;
import com.sudo.v3.base.SudoBot;
import com.sudo.v3.spectre.common.SudoInventory;
import com.sudo.v3.spectre.statics.UpdateUI;

/**
 * Created by Proxify on 6/24/2017.
 */
public class ShiftDropLeaf extends LeafTask {
    private SudoBot bot;

    public ShiftDropLeaf(SudoBot bot){
        this.bot = bot;
    }

    @Override
    public void execute() {
        UpdateUI.currentTask("Attempting to shift drop.", bot);
        SudoInventory.shiftDropAll(bot.itemsToDrop);
    }
}
