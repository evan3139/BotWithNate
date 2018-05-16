package com.sudo.v3.spectre.bots.exampleflaxpicker.branches;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;
import com.sudo.v3.spectre.bots.exampleflaxpicker.ExampleFlaxPicker;
import com.sudo.v3.spectre.bots.exampleflaxpicker.enums.Locatables;
import com.sudo.v3.spectre.bots.exampleflaxpicker.enums.TraversalLocation;
import com.sudo.v3.spectre.bots.exampleflaxpicker.leafs.*;

/**
 * Created by SudoPro on 12/28/2016.
 * In this class, we will be representing two different tree branches in one class.
 * The reason we're doing this is to reduce clutter and prevent confusing class names.
 *
 * We'll accomplish this by using the Locatables enumeration.
 */
public class IsNearby extends BranchTask {
    private ExampleFlaxPicker bot;
    private GameObject obj;

    public IsNearby(ExampleFlaxPicker bot) {
        this.bot = bot;
    }

    @Override
    public TreeTask successTask() {
        // In the case the locatable is not null
        if (bot.itemToLocate == Locatables.bank)
            return bot.openBankLeaf;
        else if (bot.itemToLocate == Locatables.flax)
            return bot.pickFlaxLeaf;
        else
            return bot.emptyLeaf;
    }

    @Override
    public boolean validate() {
        // Based on the locatable type passed in to the constructor, we look for a Bank booth or Flax
        if (bot.itemToLocate == Locatables.bank)
            obj = GameObjects.newQuery().actions("Bank").within(bot.bankArea).results().nearest();
        else if (bot.itemToLocate == Locatables.flax)
            obj = GameObjects.newQuery().names("Flax").actions("Pick").within(bot.flaxArea).results().nearest();

        // Determine whether or not the Bank booth or flax is null
        System.out.println("Branch IsNearby(" + bot.itemToLocate + "): " + (obj != null && bot.player != null && obj.distanceTo(bot.player) < 12));
        return obj != null && bot.player != null && obj.distanceTo(bot.player) < 12;
    }

    @Override
    public TreeTask failureTask() {
        // In the case the locatable IS null
        if (bot.itemToLocate == Locatables.bank) {
            // Set the bot's traversal location to the bank area
            bot.traversalLocation = TraversalLocation.bankArea;
            return bot.traversalLeaf;
        } else if (bot.itemToLocate == Locatables.flax) {
            // Set the bot's traversal location to the flax area
            bot.traversalLocation = TraversalLocation.flaxArea;
            return bot.traversalLeaf;
        } else
            return bot.emptyLeaf;
    }
}