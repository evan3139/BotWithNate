package com.bravebots.bravethieving.lumbridge_man;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.Health;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.web.WebPath;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;

/**
 * NOTES:
 * 
 */
public class Steal extends LeafTask {

    private SpriteItem Salmon;
    Area Lumby = new Area.Rectangular(new Coordinate(3213,3237,0), new Coordinate(3224,3250,0));
    Area BankArea = new Area.Rectangular(new Coordinate(3206,3223,2), new Coordinate(3210,3214,2));


    @Override
    public void execute() {
        System.out.println("I made it here!");

        Salmon = Inventory.newQuery().names("Salmon").results().first();
        GameObject man = GameObjects.newQuery().names("Man").actions("Pickpocket").results().nearest();
        if(Inventory.contains("Salmon")){
            System.out.println("Did make it here");
            if(Health.getCurrent() <= 4)
            {
                if(Salmon.interact("Eat"))
                {
                    Execution.delayWhile(() -> Health.getCurrent() <=4, 3000, 5000);
                }
            }
            if(man != null && man.isVisible())
            {
                Camera.turnTo(man);
                System.out.println("Here?!");
                if(man.interact("Pickpocket"))
                {
                    System.out.println("Will I make it here?");
                    Execution.delayWhile(() -> man.isValid(),3000,4000);
                }
            }

        }
        else
        {
            WebPath ToBank = Traversal.getDefaultWeb().getPathBuilder().buildTo(BankArea);
            WebPath ToStairs = Traversal.getDefaultWeb().getPathBuilder().buildTo(new Coordinate(3204,3207,0));
            System.out.println("Whattabout here");
            if(ToStairs != null)
            {
                System.out.println("Heeere?");
                ToStairs.step(Path.TraversalOption.MANAGE_DISTANCE_BETWEEN_STEPS, Path.TraversalOption.MANAGE_RUN);
                Execution.delay(400,4000,1800);


            }
        }

    }
}
