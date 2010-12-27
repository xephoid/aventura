package com.modiopera.aventura.model;

import com.modiopera.aventura.model.enums.StatEnum;

public class StatRequirement extends Requirement {
	private StatEnum stat;
	private int amount;
	
	public StatRequirement(StatEnum stat, int amount) {
		this.stat = stat;
		this.amount = amount;
	}
	
	public boolean met(PlayerDataMap player) {
		return player.getValue(this.stat) >= this.amount;
	}
}
