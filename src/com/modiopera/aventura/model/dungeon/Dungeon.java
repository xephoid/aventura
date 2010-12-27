package com.modiopera.aventura.model.dungeon;

import java.util.ArrayList;
import java.util.List;

public class Dungeon {
	public static final Integer DUNGEON_NOWHERE_X = -1;
	public static final Integer DUNGEON_NOWHERE_Y = -1;
	
	private Integer width;
	private Integer height;
	private DungeonTile[][] mapTiles;
	private List<DungeonObject> objects = new ArrayList<DungeonObject>();
	
	private int startx;
	private int starty;
	
	public Dungeon(Integer width, Integer height) {
		this.width = width;
		this.height = height;
		this.mapTiles = new DungeonTile[this.width][this.height];
	}
	
	public void initialize() {
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				this.mapTiles[i][j] = new DungeonTile(i, j);
			}
		}
	}
	
	public void setTile(Integer x, Integer y, DungeonTile tile) {
		if (this.isValidLocation(x, y)) {
			this.mapTiles[x][y]= tile;
		}
	}
	
	public DungeonTile getTile(Integer x, Integer y) {
		if (!this.isValidLocation(x, y)) {
			return null;
		}
		return this.mapTiles[x][y];
	}
	
	public void setTileType(Integer x, Integer y, DungeonTileTypeEnum type) {
		if (this.isValidLocation(x, y)) {
			this.mapTiles[x][y].setType(type);
		}
	}
	
	public void setTileImage(Integer x, Integer y, String imgUrl) {
		if (this.isValidLocation(x, y)) {
			this.mapTiles[x][y].setImage(imgUrl);
		}
	}
	
	public boolean moveObject(Integer tox, Integer toy, DungeonObject obj) {
		if (!isValidLocation(tox, toy)) {
			return false;
		}
		if (this.setObject(tox, toy, obj)) {
			this.removeObject(obj.getxPosition(), obj.getyPosition(), obj);
			return true;
		}
		return false;
	}
	
	public boolean removeObject(Integer x, Integer y, DungeonObject obj) {
		if (!isValidLocation(x, y)) {
			return false;
		}
		this.objects.remove(obj);
		return this.mapTiles[x][y].leave(obj);
	}
	
	public boolean addObject(DungeonObject obj) {
		return this.setObject(obj.getxPosition(), obj.getyPosition(), obj);
	}
	
	public boolean setObject(Integer x, Integer y, DungeonObject obj) {
		if (!isValidLocation(x, y) || DungeonTileTypeEnum.WALL.equals(this.mapTiles[x][y].getType())) {
			return false;
		}
		obj.setxPosition(x);
		obj.setyPosition(y);
		this.objects.add(obj);
		return this.mapTiles[x][y].visit(obj);
	}
	
	private boolean isValidLocation(Integer x, Integer y) {
		return x >= 0 && y >= 0 && x < this.width && y < this.height;
	}

	public void setStartx(int startx) {
		this.startx = startx;
	}

	public int getStartx() {
		return startx;
	}

	public void setStarty(int starty) {
		this.starty = starty;
	}

	public int getStarty() {
		return starty;
	}

	public Integer getWidth() {
		return width;
	}

	public Integer getHeight() {
		return height;
	}
	
	public boolean isBlocked(int x, int y) {
		if (this.isValidLocation(x, y)) {
			return this.getTile(x, y).isBlocked();
		}
		return true;
	}
	
	public boolean isBlocked(int x1, int y1, int x2, int y2) {
		if (isValidLocation(x1, y1) && isValidLocation(x2, y2)) {
			int tx = x1;
			int ty = y1;
			do {
				if (this.isBlocked(tx, ty)) {
					return true;
				}
				if (tx < x2) {
					tx++;
				} else if (tx > x2) {
					tx--;
				}
				if (ty < y2) {
					ty++;
				} else if (ty > y2) {
					ty--;
				}
			} while (tx != x2 || ty != y2);
			return false;
		}
		return true;
	}
	
	public List<DungeonObject> getObjects() {
		return this.objects;
	}
}
