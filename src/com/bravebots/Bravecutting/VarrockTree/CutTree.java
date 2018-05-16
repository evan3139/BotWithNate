package com.bravebots.Bravecutting.VarrockTree;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.web.Web;
import com.runemate.game.api.hybrid.location.navigation.web.WebPath;
import com.runemate.game.api.hybrid.location.navigation.web.WebPathBuilder;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;

/**
 * NOTES:
 * 
 */
public class CutTree extends LeafTask {

    Area TreeArea = new Area.Rectangular(new Coordinate(3170,3419,0),  new Coordinate(3160,3403,0));

    @Override
    public void execute() {
        if(Bank.isOpen())
        {
            if(Bank.close())
            {
                Execution.delayWhile(Bank::isOpen);
            }

        }
        if (!TreeArea.contains(Players.getLocal()))
        {
            WebPath ToTree = Traversal.getDefaultWeb().getPathBuilder().buildTo(TreeArea);
            if(ToTree != null)
            {
                if(ToTree.step(true))
                {
                    ToTree.step(Path.TraversalOption.MANAGE_RUN, Path.TraversalOption.MANAGE_DISTANCE_BETWEEN_STEPS);
                    Execution.delay(200,2000,1200);
                }

            }
        }
        if(!Bank.isOpen())
        {
            GameObject CutableTree = GameObjects.newQuery().within(TreeArea).names("Tree").results().nearest();
            if(CutableTree != null)
            {
                if(!CutableTree.isVisible())
                {
                    Camera.turnTo(CutableTree);
                }
                if(CutableTree.interact("Chop down"))
                {
                    Execution.delayWhile(() -> GameObjects.newQuery().on(CutableTree.getPosition()).names("Tree Stump").results().isEmpty() && !Inventory.isFull() && (ChatDialog.getContinue() == null), 3000, 50000);
                }
            }
        }
    }
}
