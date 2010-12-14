package com.modiopera.aventura.controller.actions;

import com.modiopera.aventura.controller.PlayerDataMap;
import com.modiopera.aventura.model.StatEnum;

public class ModifyStatAction extends Action {

	private StatEnum stat;
	private Integer value;
	
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
		PlayerDataMap.getInstance().setValue(stat, this.value);
	}
	
	public void setStat(StatEnum stat) {
		this.stat = stat;
	}

	public void setAmount(Integer amount) {
		this.value = amount;
	}
}