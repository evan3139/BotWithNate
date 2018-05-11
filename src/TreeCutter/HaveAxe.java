package please.add.your.pkg;

import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

import path.to.your.NearlyEmptyInventory
import path.to.your.STOPBOT

/**
 * NOTES:
 * Checks if we have an axe
 */
public class HaveAxe extends BranchTask {

    private NearlyEmptyInventory nearlyemptyinventory = new NearlyEmptyInventory();
    private STOPBOT stopbot = new STOPBOT();

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
        return nearlyemptyinventory;
    }
}
