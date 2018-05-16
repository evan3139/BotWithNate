package com.sudo.v3.spectre.bots.exampleflaxpicker.branches;

import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;
import com.sudo.v3.spectre.bots.exampleflaxpicker.ExampleFlaxPicker;
import com.sudo.v3.spectre.bots.exampleflaxpicker.leafs.EmptyLeaf;
import com.sudo.v3.spectre.statics.UpdateUI;

/**
 * Created by SudoPro on 12/28/2016.
 *
 * This class serves as the initial root in your tree (The first decision made)
 * Here, we wait for the user to finish with the GUI, at that point we start the decision making process of the bot.
 */
public class Root extends BranchTask {
    private ExampleFlaxPicker bot;

    public Root(ExampleFlaxPicker bot) {
        this.bot = bot;
    }

    @Override
    public TreeTask successTask() {
        // While user is still working on GUI

        // If it has been longer than 2 minutes
        if (bot.stopWatch.getRuntime() > 120000)
            bot.stop();

        // Return an empty task, we don't want to do anything while the user is still configuring the GUI
        return new EmptyLeaf();
    }

    @Override
    public boolean validate() {
        UpdateUI.debug("Root -> Waiting on GUI...  " + bot.guiWait);
        return bot.guiWait;
    }

    @Override
    public TreeTask failureTask() {
        /*
        When we are no longer waiting on the start-up GUI, we come here.

        This is essentially the equivalent to onLoop in LoopingBot.
        Any values you want to set in each 'loop', set there here before the return statement.
        */

        // In every Loop get the player
        bot.player = Players.getLocal();

        // In every Loop update the GUI thread with new/current values
        bot.updateInfo();

        // Now what we've updated the GUI and our loop-reliant values, start the decision tree.
        // First, we need to check if our inventory is full
        return bot.isInventoryFullBranch;
    }
}
