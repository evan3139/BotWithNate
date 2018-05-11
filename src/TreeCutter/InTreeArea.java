package please.add.your.pkg;

import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

import path.to.your.IsInventoryFull
import path.to.your.AreweInVarrockBank

/**
 * NOTES:
 * checks if we are in general region of trees to chop
 */
public class InTreeArea extends BranchTask {

    private IsInventoryFull isinventoryfull = new IsInventoryFull();
    private AreweInVarrockBank areweinvarrockbank = new AreweInVarrockBank();

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public TreeTask failureTask() {
        return areweinvarrockbank;
    }

    @Override
    public TreeTask successTask() {
        return isinventoryfull;
    }
}
