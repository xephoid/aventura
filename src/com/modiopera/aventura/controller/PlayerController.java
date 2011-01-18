package com.modiopera.aventura.controller;

import com.modiopera.aventura.controller.event.EventEnum;
import com.modiopera.aventura.model.GameObject;
import com.modiopera.aventura.model.Item;
import com.modiopera.aventura.model.PlayerDataMap;
import com.modiopera.aventura.model.enums.StatEnum;

/**
 * All things player data related.
 * 
 * @author xephoid
 *
 */

public class PlayerController extends BaseController {
	private GameObject activeObject;

	public GameObject getCurrentObject() {
		return this.activeObject;
	}
	
	public PlayerDataMap getPlayerData() {
		return playerData;
	}
	
	public void giveItem(Item item) {
		activeObject = item;
		fireEvent(EventEnum.AQUIRE_ITEM);
		this.playerData.addItem(item);
		view.aquireItem(item);
	}
	
	public void increaseStat(StatEnum stat, int amt) {
		// TODO: fire events n shit
		this.playerData.increaseValue(stat, amt);
	}

	@Override
	public Class<? extends GameObject> getChildType() {
		return null;
	}
}