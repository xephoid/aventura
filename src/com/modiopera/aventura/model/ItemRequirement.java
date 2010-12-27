package com.modiopera.aventura.model;

public class ItemRequirement extends Requirement {

	private Item item;
	
	public ItemRequirement(Item item) {
		this.item = item;
	}
	
	@Override
	public boolean met(PlayerDataMap player) {
		return player.hasItem(this.item);
	}

	public Item getItem() {
		return item;
	}
}
