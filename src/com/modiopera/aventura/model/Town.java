package com.modiopera.aventura.model;

import java.util.ArrayList;
import java.util.List;

import com.modiopera.aventura.model.dungeon.Dungeon;

/**
 * 
 * @author xephoid
 *
 */
public class Town extends GameObject {
	
	private List<Person> townsPeople;
	private List<Item> items;
	private List<Critter> critters;
	private Dungeon dungeon;
	private boolean open;
	
	public List<Person> getTownsPeople() {
	    if (this.townsPeople == null) {
	        this.townsPeople = new ArrayList<Person>();
	    }
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
	
	public void setOpen(boolean open) {
	    this.open = open;
	}
	
	public boolean isOpen() {
	    return this.open;
	}

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setCritters(List<Critter> critters) {
        this.critters = critters;
    }

    public List<Critter> getCritters() {
        return critters;
    }
	    
}