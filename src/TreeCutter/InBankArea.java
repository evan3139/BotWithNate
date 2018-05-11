package please.add.your.pkg;

import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

import path.to.your.WalktoTrees
import path.to.your.StopBot

/**
 * NOTES:
 * Are we in the Varrock Bank Near the Trees
 */
public class InBankArea extends BranchTask {

    private WalktoTrees walktotrees = new WalktoTrees();
    private StopBot stopbot = new StopBot();

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public TreeTask failureTask() {
        return stopbot;
    }

    @Override
    public TreeTask successTask() {
        return walktotrees;
    }
}
