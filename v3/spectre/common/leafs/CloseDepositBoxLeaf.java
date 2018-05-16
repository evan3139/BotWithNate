package com.sudo.v3.spectre.common.leafs;

import com.runemate.game.api.hybrid.local.hud.interfaces.DepositBox;
import com.runemate.game.api.script.framework.tree.LeafTask;
import com.sudo.v3.base.SudoBot;

/**
 * Created by Proxify on 6/11/2017.
 */
public class CloseDepositBoxLeaf extends LeafTask {
    private SudoBot bot;

    public CloseDepositBoxLeaf(SudoBot bot){
        this.bot = bot;
    }

    @Override
    public void execute() {
        if(DepositBox.isOpen())
            DepositBox.close();
    }
}
