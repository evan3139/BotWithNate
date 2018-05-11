package please.add.your.pkg;

import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

import path.to.your.WalkToVarrockBank
import path.to.your.InOakArea

/**
 * NOTES:
 * Check if inventory is full
 */
public class IsInventoryFull extends BranchTask {

    private WalkToVarrockBank walktovarrockbank = new WalkToVarrockBank();
    private InOakArea inoakarea = new InOakArea();

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public TreeTask failureTask() {
        return inoakarea;
    }

    @Override
    public TreeTask successTask() {
        return walktovarrockbank;
    }
}
