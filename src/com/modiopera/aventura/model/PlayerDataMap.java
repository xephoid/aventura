package com.modiopera.aventura.model;

import java.util.HashMap;
import java.util.Map;

import com.modiopera.aventura.model.enums.PlayerTypeEnum;
import com.modiopera.aventura.model.enums.StatEnum;

public class PlayerDataMap {

	private String name = "";
	private Map<StatEnum, Integer> dataMap = new HashMap<StatEnum, Integer >();
	private Map<String, Item> items = new HashMap<String, Item>();
	private PlayerTypeEnum playerType = null;
	private boolean modifiersActive = false;
	
	public PlayerDataMap(PlayerTypeEnum type) {
		this.playerType = type;
		this.initialize();
	}
	
	public PlayerTypeEnum getType() {
		return this.playerType;
	}
	
	public void initialize() {
		Integer str = 5;
		Integer rng = 5;
		Integer ine = 5;
		Integer cam = 5;
		Integer cha = 5;
		Integer frt = 5;
		Integer spd = 5;
		Integer crt = 2;
		
		switch (this.playerType) {
			case MONK :
				str = 8;
				rng = 1;
				ine = 7;
				cam = 2;
				cha = 2;
				frt = 10;
				spd = 6;
				break;
			case MARKSMAN :
				str = 10;
				rng = 9;
				ine = 2;
				cam = 2;
				cha = 3;
				frt = 4;
				spd = 4;
				break;
			case NINJA :
				str = 3;
				rng = 7;
				ine = 6;
				cam = 10;
				cha = 1;
				frt = 4;
				spd = 7;
				crt = 5;
				break;
			case REVOLUTIONARY :
				str = 1;
				rng = 2;
				ine = 8;
				cam = 0;
				cha = 10;
				frt = 9;
				spd = 5;
				break;
		}
		
		this.setValue(StatEnum.STRENGTH, str);
		this.setValue(StatEnum.RANGE, rng);
		this.setValue(StatEnum.INTELLIGENCE, ine);
		this.setValue(StatEnum.CAMOUFLAGE, cam);
		this.setValue(StatEnum.CHARISMA, cha);
		this.setValue(StatEnum.FORTITUDE, frt);
		this.setValue(StatEnum.SPEED, spd);
		this.setValue(StatEnum.CRITICAL, crt);
		
		this.setValue(StatEnum.LEVEL, 1);
		this.setValue(StatEnum.EXPERIENCE, 0);
		this.setValue(StatEnum.SIGHT, -cam);
		
		this.setValue(StatEnum.STRENGTH_MOD, 0);
		this.setValue(StatEnum.RANGE_MOD, 0);
		this.setValue(StatEnum.INTELLIGENCE_MOD, 0);
		this.setValue(StatEnum.CAMOFLAGE_MOD, 0);
		this.setValue(StatEnum.CHARISMA_MOD, 0);
		this.setValue(StatEnum.FORTITUDE_MOD, 0);
		 
		this.calcStats();
	}
	
	public void calcStats() {
		int level = this.getValue(StatEnum.LEVEL);
		this.setValue(StatEnum.DAMAGE, this.getValue(StatEnum.STRENGTH) * level);
		this.setValue(StatEnum.MAX_HIT_POINTS, this.getValue(StatEnum.FORTITUDE) * level);
		
		this.setValue(StatEnum.EXPERIENCE_TO_NEXT_LEVEL, (level ^ 10) + 100);
		
		this.resetHP();
		this.resetXP();
	}
	
	public void removeModifiers() {
		if (this.modifiersActive) {
			this.decreaseValue(StatEnum.STRENGTH, this.getValue(StatEnum.STRENGTH_MOD));
			this.decreaseValue(StatEnum.RANGE, this.getValue(StatEnum.RANGE_MOD));
			this.decreaseValue(StatEnum.INTELLIGENCE, this.getValue(StatEnum.INTELLIGENCE_MOD));
			this.decreaseValue(StatEnum.CAMOUFLAGE, this.getValue(StatEnum.CAMOFLAGE_MOD));
			this.decreaseValue(StatEnum.CHARISMA, this.getValue(StatEnum.CHARISMA_MOD));
			this.decreaseValue(StatEnum.FORTITUDE, this.getValue(StatEnum.FORTITUDE_MOD));
			this.modifiersActive = false;
		}
	}
	
	public void addModifiers() {
		if (!this.modifiersActive) {
			this.increaseValue(StatEnum.STRENGTH, this.getValue(StatEnum.STRENGTH_MOD));
			this.increaseValue(StatEnum.RANGE, this.getValue(StatEnum.RANGE_MOD));
			this.increaseValue(StatEnum.INTELLIGENCE, this.getValue(StatEnum.INTELLIGENCE_MOD));
			this.increaseValue(StatEnum.CAMOUFLAGE, this.getValue(StatEnum.CAMOFLAGE_MOD));
			this.increaseValue(StatEnum.CHARISMA, this.getValue(StatEnum.CHARISMA_MOD));
			this.increaseValue(StatEnum.FORTITUDE, this.getValue(StatEnum.FORTITUDE_MOD));
		}
	}
	
	public void resetHP() {
		this.setValue(StatEnum.HIT_POINTS, this.getValue(StatEnum.MAX_HIT_POINTS));
	}
	
	public void resetXP() {
		this.setValue(StatEnum.EXPERIENCE, 0);
	}
	
	public void setValue(StatEnum key, Integer value) {
		this.dataMap.put(key, value);
	}
	
	public Integer getValue(StatEnum key) {
		return this.dataMap.get(key);
	}
	
	public void increaseValue(StatEnum key, Integer amount) {
		switch (key) {
			case LEVEL :
			case EXPERIENCE_TO_NEXT_LEVEL :
			case SPEED :
				return; // no-op
			
			case EXPERIENCE : {
				int exp = this.getValue(key);
				int max = this.getValue(StatEnum.EXPERIENCE_TO_NEXT_LEVEL);
				exp += amount;
				
				if (exp > max) {
					this.levelUp();
					this.setValue(StatEnum.EXPERIENCE, max - exp);
				} else {
					this.setValue(key, exp);
				}
				break;
			}
			case HIT_POINTS :
				int hp = this.getValue(key) + amount;
				int max = this.getValue(StatEnum.MAX_HIT_POINTS);
 				if (hp > max) {
					this.setValue(key, max);
				} else {
					this.setValue(key, hp);
				}
			default:
				if (this.dataMap.containsKey(key)) {
					Integer val = (Integer) this.dataMap.get(key);
					val += amount;
					this.dataMap.put(key, val);
				}
				break;
		}
	}
	
	public void levelUp() {
		this.setValue(StatEnum.LEVEL, this.getValue(StatEnum.LEVEL) + 1);
		this.calcStats();
		this.resetHP();
		this.resetXP();
	}
	
	public void decreaseValue(StatEnum key, int amount) {
		if (this.dataMap.containsKey(key)) {
			int oldVal = this.dataMap.get(key);
			oldVal += amount;
			this.dataMap.put(key, oldVal);
		}
	}
	
	public void addItem(Item item) {
		this.items.put(item.getId(), item);
	}
	
	public boolean hasItem(Item item) {
		return this.items.containsKey(item.getId());
	}
	
	public void removeItem(Item item) {
		if (this.hasItem(item)) {
			this.items.remove(item.getId());
		} else {
			// Throw exception?
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}