package com.modiopera.aventura.controller;

import java.util.HashMap;
import java.util.Map;

import com.modiopera.aventura.model.StatEnum;

public class PlayerDataMap {

	public static PlayerDataMap _instance = null;
	
	private Map<Enum<?>, Object> dataMap = new HashMap<Enum<?>, Object>();
	
	private PlayerDataMap() {
		// TODO: initialize base stats
	}
	
	public static PlayerDataMap getInstance() {
		if (_instance == null) {
			_instance = new PlayerDataMap();
		}
		return _instance;
	}
	
	public void setValue(Enum<?> key, Object value) {
		this.dataMap.put(key, value);
	}
	
	public Object getValue(Enum<?> key) {
		return this.dataMap.get(key);
	}
	
	public void increaseValue(StatEnum key, Integer amount) {
		switch (key) {
			case EXPERIENCE :
				// TODO
				break;
			default:
				if (this.dataMap.containsKey(key)) {
					Integer val = (Integer) this.dataMap.get(key);
					val += amount;
					this.dataMap.put(key, val);
				}
				break;
		}
	}
}