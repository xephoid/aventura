package com.modiopera.aventura.model.dungeon;

import com.modiopera.aventura.model.Item;

public class DungeonItem extends DungeonObject {

	private Item actualItem;
	
	public DungeonItem(Item item) {
		this.actualItem = item;
	}
	
	@Override
	public String getCurrentImageURL() {
		return "";
	}

	@Override
	public void visited(DungeonObject obj) {
		if (obj.isPlayer()) {
			// TODO: give player the item...
		}
	}

}
