package please.add.your.pkg;

import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

import path.to.your.ChopTree
import path.to.your.InBankArea

/**
 * NOTES:
 * Checks to see if we are in the area we need to be to chop down Oak Trees
 */
public class InOakArea extends BranchTask {

    private ChopTree choptree = new ChopTree();
    private InBankArea inbankarea = new InBankArea();

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public TreeTask failureTask() {
        return inbankarea;
    }

    @Override
    public TreeTask successTask() {
        return choptree;
    }
}
