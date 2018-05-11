package please.add.your.pkg;

import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

import path.to.your.EquipAxe
import path.to.your.OPENBANK

/**
 * NOTES:
 * 
 */
public class HaveAxeInInventory extends BranchTask {

    private EquipAxe equipaxe = new EquipAxe();
    private OPENBANK openbank = new OPENBANK();

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public TreeTask failureTask() {
        return openbank;
    }

    @Override
    public TreeTask successTask() {
        return equipaxe;
    }
}
