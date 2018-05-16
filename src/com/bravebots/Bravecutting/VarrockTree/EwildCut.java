package com.bravebots.Bravecutting.VarrockTree;

import com.runemate.game.api.script.framework.tree.TreeBot;
import com.runemate.game.api.script.framework.tree.TreeTask;

public class EwildCut extends TreeBot {




    @Override
    public TreeTask createRootTask() {
        return new InTreeArea();
    }
}
