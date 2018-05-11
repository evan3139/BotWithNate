package please.add.your.pkg;

import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

import path.to.your.WalktoTreeArea
import path.to.your.depositunwanted

/**
 * NOTES:
 * 
 */
public class Havenearlemptyinv extends BranchTask {

    private WalktoTreeArea walktotreearea = new WalktoTreeArea();
    private depositunwanted depositunwanted = new depositunwanted();

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
        return walktotreearea;
    }
}
