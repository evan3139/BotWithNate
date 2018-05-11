package please.add.your.pkg;

import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

import path.to.your.WalktoTreeArea
import path.to.your.makemestop

/**
 * NOTES:
 * 
 */
public class failsafehaveaxeequipped extends BranchTask {

    private WalktoTreeArea walktotreearea = new WalktoTreeArea();
    private makemestop makemestop = new makemestop();

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public TreeTask failureTask() {
        return makemestop;
    }

    @Override
    public TreeTask successTask() {
        return walktotreearea;
    }
}
