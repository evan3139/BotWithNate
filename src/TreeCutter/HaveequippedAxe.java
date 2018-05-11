package please.add.your.pkg;

import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

import path.to.your.Havenearlemptyinv
import path.to.your.HaveAxeInInventory

/**
 * NOTES:
 * Withraw an Axe
 */
public class HaveequippedAxe extends BranchTask {

    private Havenearlemptyinv havenearlemptyinv = new Havenearlemptyinv();
    private HaveAxeInInventory haveaxeininventory = new HaveAxeInInventory();

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public TreeTask failureTask() {
        return haveaxeininventory;
    }

    @Override
    public TreeTask successTask() {
        return havenearlemptyinv;
    }
}
