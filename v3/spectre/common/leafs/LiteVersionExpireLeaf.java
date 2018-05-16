package com.sudo.v3.spectre.common.leafs;

import com.runemate.game.api.script.framework.tree.LeafTask;
import com.sudo.v3.base.SudoBot;
import com.sudo.v3.spectre.statics.UpdateUI;

/**
 * Created by Joey 2.0 on 3/21/2017.
 */
public class LiteVersionExpireLeaf extends LeafTask {
    private SudoBot bot;

    public LiteVersionExpireLeaf(SudoBot bot){
        this.bot = bot;
    }

    @Override
    public void execute() {
        UpdateUI.currentTask("Trial timer has expired. Stopping Bot.", bot);
        bot.stop();
    }
}
