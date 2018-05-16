package com.bravebots.Bravecutting.VarrockTree;

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.web.WebPath;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;

/**
 * NOTES:
 * 
 */
public class WalkToTreeArea extends LeafTask {

    Area TreeArea = new Area.Rectangular(new Coordinate(3170,3419,0),  new Coordinate(3160,3403,0));

    @Override
    public void execute() {
        if (Bank.isOpen())
        {
            if(Bank.close())
                Execution.delayUntil(Bank::isOpen);
        }
        if(!TreeArea.contains(Players.getLocal()))
        {
            WebPath ToTree = Traversal.getDefaultWeb().getPathBuilder().buildTo(TreeArea);
            ToTree.step(Path.TraversalOption.MANAGE_RUN, Path.TraversalOption.MANAGE_DISTANCE_BETWEEN_STEPS);
        }

    }
}
