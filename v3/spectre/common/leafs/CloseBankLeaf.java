package com.sudo.v3.spectre.common.leafs;

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.DepositBox;
import com.runemate.game.api.script.framework.tree.LeafTask;
import com.sudo.v3.base.SudoBot;
import com.sudo.v3.spectre.statics.UpdateUI;

/**
 * Created by SudoPro on 12/23/2016.
 */
public class CloseBankLeaf extends LeafTask
{
    private SudoBot bot;

    public CloseBankLeaf(SudoBot bot)
    {
        this.bot = bot;
    }

    @Override
    public void execute()
    {
        UpdateUI.currentTask("Closing bank", bot);
        Bank.close();
    }
}
