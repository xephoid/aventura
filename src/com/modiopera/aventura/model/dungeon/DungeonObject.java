package com.modiopera.aventura.model.dungeon;

public abstract class DungeonObject {

	private Integer xPosition;
	private Integer yPosition;
	private DungeonTile currentTile = null;
	
	public void setxPosition(Integer xPosition) {
		this.xPosition = xPosition;
	}
	
	public Integer getxPosition() {
		return xPosition;
	}
	
	public void setyPosition(Integer yPosition) {
		this.yPosition = yPosition;
	}
	
	public Integer getyPosition() {
		return yPosition;
	}
	
	public abstract String getCurrentImageURL();
	public abstract void visited(DungeonObject obj);

	public void setCurrentTile(DungeonTile currentTile) {
		this.currentTile = currentTile;
		if (this.currentTile != null) {
			this.xPosition = this.currentTile.getxLocation();
			this.yPosition = this.currentTile.getyLocation();
		} else {
			this.xPosition = Dungeon.DUNGEON_NOWHERE_X;
			this.yPosition = Dungeon.DUNGEON_NOWHERE_Y;
		}
	}

	public DungeonTile getCurrentTile() {
		return currentTile;
	}
	
	public boolean isObstruction() {
		return false;
	}
}
