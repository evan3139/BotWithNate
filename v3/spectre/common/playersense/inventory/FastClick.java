package com.sudo.v3.spectre.common.playersense.inventory;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.queries.results.SpriteItemQueryResults;
import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.osrs.local.hud.interfaces.ControlPanelTab;
import com.runemate.game.api.osrs.local.hud.interfaces.OptionsTab;
import com.runemate.game.api.script.Execution;
import com.sudo.v3.base.SudoBot;
import com.sudo.v3.spectre.common.playersense.ApexPlayerSense;
import com.sudo.v3.spectre.common.playersense.DelaysPsensed;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.awt.event.KeyEvent.VK_SHIFT;

public class FastClick {

    private SudoBot bot;

    private int slotsToClick = -1;
    private ArrayList<Integer> clickedInventorySlots = new ArrayList<>();
    private StopWatch timeSinceLastCall;
    private PatternType clickPattern = PatternType.getPlayerSensedPattern();
    private double chanceOfRandomPattern = ApexPlayerSense.Key.CHANCE_OF_RANDOM_PATTERN.getAsDouble();
    private int removalAttempts = 0;


    public FastClick(SudoBot bot) {
        this.bot = bot; //to change loop delay
        timeSinceLastCall = new StopWatch(); //to keep track of the time elapsed since we last clicked something
        timeSinceLastCall.start();
    }

    public final void clickItems(SpriteItemQueryResults items, boolean pressShift){
        if(!ControlPanelTab.INVENTORY.open()){ //open our inventory before we start doing anything
            //this returns true and nothing else if its already open
            Environment.getLogger().warn("Failed to open inventory for fast clicking");
            return;
        }
        if(pressShift) { //if we are pressing shift to drop, press it before dropping, or release it if we are done dropping
            if(!OptionsTab.setShiftDropping(true)){
                Environment.getLogger().warn("Failed to enable shift click actions");
                return;
            }
            if (items.isEmpty() && Keyboard.isPressed(VK_SHIFT)) {
                Environment.getLogger().warn("Releasing shift key");
                Keyboard.releaseKey(VK_SHIFT);
                DelaysPsensed.delay(0, 80, 35);
                return;
            } else if (!Keyboard.isPressed(VK_SHIFT)) {
                Environment.getLogger().warn("Pressing shift key");
                Keyboard.pressKey(VK_SHIFT);
                DelaysPsensed.delay(0, 80, 35);
            }
        }
        if(timeSinceLastCall.getRuntime(TimeUnit.MILLISECONDS) > 500){ //if it has been a long time since we last fast clicked items
            slotsToClick = items.size(); //cache the number of items we originally set out to drop
            clickedInventorySlots.clear(); //we are starting a new cycle, so we get rid of already clicked spots
            if(Math.random() < chanceOfRandomPattern){ //generate a new trialPattern to drop - we will use the same (or very similar) patterns a playersensed % of the time, and a random trialPattern the other times
                clickPattern = PatternType.getRandomPattern();
            } else {
                clickPattern = PatternType.getPlayerSensedPattern();
            }
            Environment.getLogger().debug("Click trialPattern generated: "+clickPattern);
        }
        bot.setLoopDelay(0); //set the bots loop delay to 0 - dropping speed is handled only by this method
        List<SpriteItem> sortedItems = PatternType.patternizeItemList(items.asList(), clickPattern); //sort the items according to dropping trialPattern
        if(sortedItems != null && !sortedItems.isEmpty()){ //if we were able to sort the items, continue
            sortedItems.removeIf(spriteItem -> clickedInventorySlots.contains(spriteItem.getIndex())); //get rid of items we already clicked
            if(!sortedItems.isEmpty()) { //if we have items left to click
                SpriteItem toClick = sortedItems.get(0); //get the first one and click it
                if(toClick != null){
                    if(Mouse.move(toClick)){
                        DelaysPsensed.delay(0, 55, 25);//the delay after hovering an item - when recording myself, I noticed I hesitate slightly before clicking
                        if(!toClick.contains(Mouse.getPosition())){
                            Environment.getLogger().warn("Mouse.move() returned a false positive!");
                        }
                        if(Mouse.click(Mouse.Button.LEFT)){
                            clickedInventorySlots.add(toClick.getIndex()); //keep track of what we already clicked
                            Environment.getLogger().debug("We have clicked "+clickedInventorySlots.toString());
                            timeSinceLastCall.reset(); //reset the timer since we last clicked something
                            if(clickedInventorySlots.size() == slotsToClick){ //if we have clicked all the items originally provided, wait until the intended changes have occured
//                                if(containsEssencePouch(sortedItems.toString())){
//                                    if(pressShift){
//                                        if(DelaysPsensed.delayUntil(()->!EssencePouch.canEmptyAPouch(), 1000, 2000)){
//                                            Environment.getLogger().debug("Finished emptying pouches");
//                                        }
//                                    } else {
//                                        if(DelaysPsensed.delayUntil(()->!EssencePouch.canFillAPouch(), 1000, 2000)){
//                                            Environment.getLogger().debug("Finished filling pouches");
//                                        }
//                                    }
//                                } else {
                                    if(DelaysPsensed.delayUntil(() -> !toClick.isValid(), 1000, 2000)){
                                        Environment.getLogger().debug("Finished fast clicking");
                                    }
                                //}
                            } else {
                                DelaysPsensed.delay(0, 15, 5); //the delay after clicking an item - when recording myself, I noticed i move the mouse almost instantly after clicking
                            }
                        } else {
                            Environment.getLogger().warn("Somehow failed to click mouse...");
                        }
                    } else {
                        Environment.getLogger().debug("Failed to move mouse to "+toClick);
                    }
                } else {
                    Environment.getLogger().warn("First sorted sprite was null");
                }
            } else {
                Environment.getLogger().warn("No items were left after removal");
                removalAttempts++; //if we have tried to drop items we have already dropped multiple times, we probably missed something.  clear out the already clicked zones.
                if(!items.isEmpty() && removalAttempts >= 3){
                    Environment.getLogger().warn("We missed the following items: "+items.toString());
                    clickedInventorySlots.clear();
                }
                Execution.delay(250);
            }
        } else {
            Environment.getLogger().warn("Could not get a sorted list of items from "+items);
        }
    }

//    private boolean containsEssencePouch(String input){
//        return input != null && (input.contains(EssencePouch.SMALL.getName()) || input.contains(EssencePouch.MEDIUM.getName()) || input.contains(EssencePouch.LARGE.getName()) || input.contains(EssencePouch.GIANT.getName()));
//    }

}