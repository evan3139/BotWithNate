package com.bravebots.bravethieving.lumbridge_man;

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
public class WalkToLumbridge extends LeafTask {

    Area Lumby = new Area.Rectangular(new Coordinate(3213,3237,0), new Coordinate(3224,3250,0));

    @Override
    public void execute() {
        if(!Lumby.contains(Players.getLocal()))
        {
            WebPath ToLumby = Traversal.getDefaultWeb().getPathBuilder().buildTo(Lumby);
            if (ToLumby != null){
                ToLumby.step(Path.TraversalOption.MANAGE_RUN, Path.TraversalOption.MANAGE_DISTANCE_BETWEEN_STEPS);
                Execution.delay(400,2000,900);
            }
        }

    }
}
