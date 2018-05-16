package com.squidl.bots.HosidiusThieving.tasks;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;

public class WalkToStalls extends Task {

    private Player me;
    private GameObject stalls;

    private static final Area area = new Area.Rectangular(new Coordinate(1765, 3497, 0), new Coordinate(1781, 3481, 0));
    private static Coordinate stallc = new Coordinate(1776,3494, 0);

    @Override
    public boolean validate() {
        me = Players.getLocal();
        return me != null && area.contains(me);
    }

    @Override
    public void execute() {
        stalls = GameObjects.newQuery().on(stallc).names("Fruit Stall").actions("Steal-from").results().nearest();
        if (stalls != null && stalls.isVisible()) {
            System.out.println("Walking to Stalls");
            if (stalls.interact("Steal-from")) {
                Execution.delayWhile(() -> stalls.isValid(), 3000, 4000);
            }
        } else {
            final BresenhamPath path = BresenhamPath.buildTo(new Coordinate(1773, 3496, 0));
            if (me.distanceTo(stalls) < 200) {
                if (path != null) {
                    System.out.println("Traversing to Stalls");
                    path.step();
                }
            }
        }
    }
}