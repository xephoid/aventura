package com.modiopera.aventura.controller.actions;

import com.modiopera.aventura.controller.PlayerDataMap;
import com.modiopera.aventura.model.enums.StatEnum;

public class ModifyStatAction extends Action {

	private StatEnum stat;
	private Integer value;
	private PlayerDataMap playerData;
	
	@Override
	public void act() {
		if (this.stat == null) {
			// TODO: log or exception?
			return;
		}
		if (this.value == null) {
			// TODO
			return;
		}
		this.playerData.setValue(stat, this.value);
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