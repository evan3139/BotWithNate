package com.sudo.v3.spectre.bots.exampleflaxpicker.leafs;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;
import com.sudo.v3.spectre.bots.exampleflaxpicker.ExampleFlaxPicker;

/**
 * Created by SudoPro on 12/28/2016.
 */
public class PickFlaxLeaf extends LeafTask {
    private ExampleFlaxPicker bot;

    public PickFlaxLeaf(ExampleFlaxPicker bot) {
        this.bot = bot;
    }

    @Override
    public void execute() {
        // Get the nearest game object with the name Flax and action Pick and interact with found object with the Pick action
        GameObject flax = GameObjects.newQuery().names("Flax").actions("Pick").within(bot.flaxArea).results().nearest();

        // If the flax GO is not null
        if (flax != null) {
            bot.currentTaskString = "Attempting to pick flax";

            // If the flax is not visible
            if (!flax.isVisible())
                Camera.concurrentlyTurnTo(flax);

            // Delay for a random time between 100 and 500 milliseconds. This prevent spam clicking
            if (flax.interact("Pick"))
                Execution.delay(100, 500);

        }
    }
}