package com.sudo.v3.spectre.common.leafs;

import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;
import com.sudo.v3.base.SudoBot;
import com.sudo.v3.spectre.statics.UpdateUI;

/**
 * Created by SudoPro on 12/4/2016.
 */
public class EmptyLeaf extends LeafTask
{
    private SudoBot bot;

    public EmptyLeaf(SudoBot bot){
        this.bot = bot;
    }

    @Override
    public void execute()
    {
        if(bot.player != null)
        UpdateUI.debug(bot.player.getName() + " -> Reached empty leaf");
        Execution.delay(750);
    }
}
