package com.bravebots.Bravecutting.VarrockTree;

import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.calculations.Distance;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

//import path.to.your.IsAxeEquipped
//import path.to.your.WalkToTreeArea


/**
 * NOTES:
 * 
 */
public class InTreeArea extends BranchTask {

    private IsAxeEquipped isaxeequipped = new IsAxeEquipped();
    private WalkToTreeArea walktotreearea = new WalkToTreeArea();

    Area TreeArea = new Area.Rectangular(new Coordinate(3170,3419,0),  new Coordinate(3160,3403,0));

    @Override
    public boolean validate() {
        return Players.getLocal() != null && Distance.between(Players.getLocal(), TreeArea) <= 20;
    }

    @Override
    public TreeTask failureTask() {
        return walktotreearea;
    }

    @Override
    public TreeTask successTask() {
        return isaxeequipped;
    }
}
