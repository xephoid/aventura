package com.modiopera.aventura.controller.actions;

import com.modiopera.aventura.model.Item;

public class GiveItemToPlayerAction extends Action {

    private Item item;
    private boolean given = false;
    
    @Override
    public boolean act() {
        if (!given) {
            given = true;
            playerDataMap.addItem(item);
            return true;
        }
        return false;
    }
    
    public void setItem(Item item) {
        this.item = item;
    }
    
    @Override
    public String toString() {
        return "Reviced item: " + item.getName();
    }
}
