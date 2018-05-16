package com.bravebots.Bravecutting.VarrockTree;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.hybrid.location.navigation.web.WebPath;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;

/**
 * NOTES:
 * 
 */
public class OpenBank extends LeafTask {

    Area BankArea = new Area.Rectangular(new Coordinate(3180,3432,0), new Coordinate(  3190, 3448, 0));

    @Override
    public void execute() {
        if (!BankArea.contains(Players.getLocal())) {
            WebPath toBankArea = Traversal.getDefaultWeb().getPathBuilder().buildTo(BankArea.getRandomCoordinate());
            if (toBankArea != null) {
                toBankArea.step(true);
                Execution.delayUntil(() -> (Players.getLocal() != null && !Players.getLocal().isMoving()), 100, 2000);
            }
        }

        GameObject bankChest = GameObjects.newQuery().within(BankArea).names("Bank booth").actions("Bank").results().first();
        if (bankChest != null) {
            if (bankChest.isVisible()) {
                if (bankChest.interact("Bank")) {
                    Execution.delayUntil(Bank::isOpen, () -> (Players.getLocal()) != null && Players.getLocal().isMoving(), 100, 2000);
                }
            } else {
                Camera.turnTo(bankChest);
            }
        }
    }
}
