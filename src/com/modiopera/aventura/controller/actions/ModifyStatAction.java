package com.modiopera.aventura.controller.actions;

import com.modiopera.aventura.model.PlayerDataMap;
import com.modiopera.aventura.model.enums.StatEnum;

public class ModifyStatAction extends Action {

	private StatEnum stat;
	private Integer value;
	private PlayerDataMap playerData;
	
	@Override
	public boolean act() {
		if (this.stat == null) {
			return false;
		}
		if (this.value == null) {
			return false;
		}
		this.playerData.setValue(stat, this.value);
		return true;
	}
	
	public void setStat(StatEnum stat) {
		this.stat = stat;
	}

	public void setAmount(Integer amount) {
		this.value = amount;
	}

	public void setPlayerData(PlayerDataMap playerData) {
		this.playerData = playerData;
	}

	public PlayerDataMap getPlayerData() {
		return playerData;
	}
}