package please.add.your.pkg;

import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

import path.to.your.CloseBank
import path.to.your.DepositUnwanted

/**
 * NOTES:
 * checks if inv is mostly empty
 */
public class NearlyEmptyInventory extends BranchTask {

    private CloseBank closebank = new CloseBank();
    private DepositUnwanted depositunwanted = new DepositUnwanted();

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public TreeTask failureTask() {
        return depositunwanted;
    }

    @Override
    public TreeTask successTask() {
        return closebank;
    }
}
