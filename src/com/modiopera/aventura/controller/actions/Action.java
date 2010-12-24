package com.modiopera.aventura.controller.actions;

import com.modiopera.aventura.model.PlayerDataMap;

public abstract class Action {
    protected PlayerDataMap playerDataMap;
    
	public abstract boolean act();
	
	public void setPlayerData(PlayerDataMap playerData) {
	    this.playerDataMap = playerData;
	}
}
