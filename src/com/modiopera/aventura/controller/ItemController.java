package com.modiopera.aventura.controller;

import java.util.Map;

import com.modiopera.aventura.model.GameObject;
import com.modiopera.aventura.model.Item;

public class ItemController extends BaseController {
	
	private Item currentItem;
	private Map<String, Item> itemMap;

	@Override
	protected GameObject getCurrentObject() {
		return this.currentItem;
	}

	@Override
	public Class<? extends GameObject> getChildType() {
		return Item.class;
	}

	public void setItemMap(Map<String, Item> itemMap) {
		this.itemMap = itemMap;
	}
	
	public void aquireItem(Item item) {
		playerData.addItem(item);
		this.currentItem = item;
		view.aquireItem(item);
		fireEvent(EventEnum.AQUIRE_ITEM);
	}
	
	public void useItem(Item item) {
		if (item.isConsumable()) {
			item.getAction().act();
			playerData.removeItem(item);
			this.currentItem = item;
			eventHandler.createEvent(EventEnum.USE_ITEM, item);
		}
	}
	
	public Item getItem(String id) {
		return this.itemMap.get(id);
	}
}
