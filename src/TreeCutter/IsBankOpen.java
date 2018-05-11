package please.add.your.pkg;

import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

import path.to.your.HaveAxe
import path.to.your.HaveequippedAxe

/**
 * NOTES:
 * Checks if bank is open or not
 */
public class IsBankOpen extends BranchTask {

    private HaveAxe haveaxe = new HaveAxe();
    private HaveequippedAxe haveequippedaxe = new HaveequippedAxe();

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public TreeTask failureTask() {
        return haveequippedaxe;
    }

    @Override
    public TreeTask successTask() {
        return haveaxe;
    }
}
