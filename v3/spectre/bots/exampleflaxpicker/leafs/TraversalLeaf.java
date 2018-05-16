package com.sudo.v3.spectre.bots.exampleflaxpicker.leafs;

import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;
import com.sudo.v3.spectre.bots.exampleflaxpicker.ExampleFlaxPicker;
import com.sudo.v3.spectre.bots.exampleflaxpicker.enums.TraversalLocation;

/**
 * Created by SudoPro on 12/28/2016.
 */
public class TraversalLeaf extends LeafTask {
    private ExampleFlaxPicker bot;
    private Area area;

    public TraversalLeaf(ExampleFlaxPicker bot) {
        this.bot = bot;
    }

    @Override
    public void execute() {
        if (bot.traversalLocation == TraversalLocation.bankArea)
            area = bot.bankArea;
        else if (bot.traversalLocation == TraversalLocation.flaxArea)
            area = bot.flaxArea;

        final BresenhamPath bp = BresenhamPath.buildTo(area.getRandomCoordinate());

        if (bp != null) {
            if (bp.step(true)) {
                bot.currentTaskString = "Traversing to " + bot.traversalLocation;
                Execution.delayWhile(bot.player::isMoving, 1000, 2500);
            }
        }
    }
}