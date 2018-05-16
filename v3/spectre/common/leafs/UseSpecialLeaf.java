package com.sudo.v3.spectre.common.leafs;

import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceWindows;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;
import com.sudo.v3.base.SudoBot;
import com.sudo.v3.spectre.statics.UpdateUI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UseSpecialLeaf extends LeafTask {

    private SudoBot bot;
    private Pattern pattern = Pattern.compile("Special Attack: (.*?)%");

    public UseSpecialLeaf(SudoBot bot){
        this.bot = bot;
    }

    @Override
    public void execute() {
        // Get interface that hold the special attack percentage value
        InterfaceComponent specialAttack = Interfaces.newQuery().containers(593).textContains("Special Attack:").results().first();

        if(specialAttack != null && specialAttack.isVisible()){
            String spText = specialAttack.getText();
            // Grab Matches from our current value
            Matcher matcher = pattern.matcher(spText);

            // If any matches exist
            if (matcher.find()) {
                int percentage = Integer.valueOf(matcher.group(1));

                // If we do not have enough to do a special attack boost
                if(percentage < 100 && !InterfaceWindows.getInventory().isOpen()){
                    UpdateUI.currentTask("Special recharging. Reopening Inventory", bot);
                    if(InterfaceWindows.getInventory().open())
                        bot.checkSpecialTimer.reset();
                }else{
                    UpdateUI.currentTask("Attempting to use Special Ability", bot);
                    if(specialAttack.click())
                        Execution.delay(1000, 2000);
                }
            }
        }else{
            // Special attack couldn't be read, open up the interface
            InterfaceComponent combatOptions = Interfaces.newQuery().containers(164).actions("Combat Options").results().first();

            if(combatOptions != null){
                UpdateUI.currentTask("Checking Special Percentage", bot);
                if(combatOptions.click())
                    Execution.delay(800, 2200);
            }
        }
    }
}
