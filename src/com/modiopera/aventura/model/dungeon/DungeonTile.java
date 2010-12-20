package com.modiopera.aventura.model.dungeon;

import java.util.ArrayList;
import java.util.List;

public class DungeonTile {

	private String image;
	private Integer xLocation;
	private Integer yLocation;
	private DungeonTileTypeEnum type = DungeonTileTypeEnum.FLOOR;
	
	private List<DungeonObject> objectsOnTile = new ArrayList<DungeonObject>();
	
	public DungeonTile(Integer x, Integer y) {
		this.xLocation = x;
		this.yLocation = y;
	}
	
	public Integer getxLocation() {
		return this.xLocation;
	}
	
	public Integer getyLocation() {
		return this.yLocation;
	}
	
	public boolean visit(DungeonObject obj) {
		for (DungeonObject local : this.objectsOnTile) {
			local.visited(obj);
		}
		if (this.objectsOnTile.add(obj)) {
			obj.setCurrentTile(this);
			return true;
		}
		return false;
	}
	
	public boolean leave(DungeonObject obj) {
		if (this.objectsOnTile.remove(obj)) {
			obj.setCurrentTile(null);
		}
		return false;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImage() {
		return image;
	}

	public void setType(DungeonTileTypeEnum type) {
		this.type = type;
	}

	public DungeonTileTypeEnum getType() {
		return type;
	}
	
	public List<DungeonObject> getObjects() {
		return this.objectsOnTile;
	}
	
	public boolean isBlocked() {
		if (DungeonTileTypeEnum.WALL.equals(this.type)) {
			return true;
		}
		for (DungeonObject obj : this.objectsOnTile) {
			if (obj.isObstruction()) {
				return true;
			}
		}
		return false;
	}
}
