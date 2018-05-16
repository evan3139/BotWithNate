package com.sudo.v3.spectre.common.leafs;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.script.framework.tree.LeafTask;
import com.sudo.v3.base.SudoBot;
import com.sudo.v3.spectre.statics.UpdateUI;

/**
 * Created by joeyw on 5/12/2017.
 */
public class DeselectInventoryItemLeaf extends LeafTask {
    private SudoBot bot;
    private SpriteItem inventoryItem;

    public DeselectInventoryItemLeaf(SudoBot bot){
        this.bot = bot;
    }

    @Override
    public void execute() {
        if ((inventoryItem = Inventory.getSelectedItem()) != null){
            UpdateUI.currentTask("Deselecting inventory item", bot);
            inventoryItem.click();
        }
    }
}
