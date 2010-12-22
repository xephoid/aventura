package com.modiopera.aventura.model;

import java.util.List;

import com.modiopera.aventura.model.dungeon.Dungeon;

/**
 * 
 * @author xephoid
 *
 */
public class Town extends GameObject {
	
	private List<Person> townsPeople;
	private Dungeon dungeon;
	
	public List<Person> getTownsPeople() {
		return townsPeople;
	}
	
	public void setTownsPeople(List<Person> townsPeople) {
		this.townsPeople = townsPeople;
	}
	
	public Dungeon getDungeon() {
		return dungeon;
	}
	
	public void setDungeon(Dungeon dungeon) {
		this.dungeon = dungeon;
	}
}