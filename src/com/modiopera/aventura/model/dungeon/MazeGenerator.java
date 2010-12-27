package com.modiopera.aventura.model.dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class MazeGenerator {
	private enum Dir {
		UP(0),
		DOWN(1),
		LEFT(2),
		RIGHT(3);
		
		private int num;
		Dir(int num) {
			this.num = num;
		}
		public int getNum() {
			return this.num;
		}
		
		public int getOp() {
			switch (this) {
				case UP:
					return DOWN.getNum();
				case DOWN:
					return UP.getNum();
				case LEFT:
					return RIGHT.getNum();
				case RIGHT:
					return LEFT.getNum();
				default:
					return -1;
			}
		}
	}
	
	private class Room {
		public boolean[] walls = new boolean[4];
		public boolean visited = false;
	}
	
	private static final int SQUARE_SIZE = 5;
	
	private Room[][] generateMaze(int width, int height) {
		Room[][] rooms = new Room[width][height];
		for (int i = 0; i < rooms.length ;i ++) {
			for (int j = 0; j < rooms[i].length; j++) {
				rooms[i][j] = new MazeGenerator.Room();
			}
		}
		this.enterRoom(rooms, (int) Math.random() * width, (int) Math.random() * height);
		return rooms;
	}
	
	private void enterRoom(Room[][] rooms, int x, int y) {
		if (validPosition(rooms, x, y) && !rooms[x][y].visited) {
			rooms[x][y].visited = true;
			List<Dir> dirs = new ArrayList<MazeGenerator.Dir>(EnumSet.allOf(Dir.class));
			Collections.shuffle(dirs);
			for (Dir dir : dirs) {
				int deltaX = 0;
				int deltaY = 0;
				switch(dir) {
					case UP :
						deltaY = -1;
						break;
					case DOWN :
						deltaY = 1;
						break;
					case RIGHT :
						deltaX = 1;
						break;
					case LEFT:
						deltaX = -1;
						break;
				}
				if (validPosition(rooms, x + deltaX, y + deltaY)) {
					if (!rooms[x + deltaX][y + deltaY].visited) {
						rooms[x][y].walls[dir.getNum()] = true;
						enterRoom(rooms, x + deltaX, y + deltaY);
					} else {
						rooms[x][y].walls[dir.getNum()] = rooms[x + deltaX][y + deltaY].walls[dir.getOp()];
					}
				} else {
					rooms[x][y].walls[dir.getNum()] = false;
				}
			}
		}
	}
	
	private static boolean validPosition(Room[][] rooms, int x, int y) {
		return x >= 0 && x < rooms.length && y >= 0 && y < rooms[0].length;
	}
	
	public Dungeon generateDungeon(int width, int height) {
		Dungeon dungeon = new Dungeon(width, height);
		dungeon.initialize();

		MazeGenerator maze = new MazeGenerator();
		Room[][] rooms =  maze.generateMaze(width / SQUARE_SIZE, width / SQUARE_SIZE);
		
		// Generate borders
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (x == 0 || y == 0 || x == width -1 || y == width -1) {
					dungeon.setTileType(x, y, DungeonTileTypeEnum.WALL);
				}
			}
		}
		
		
		for (int i = 0; i < rooms.length; i++) {
			for (int j = 0; j < rooms[i].length; j++) {
				for (Dir d : EnumSet.allOf(Dir.class)) {
					if (!rooms[i][j].walls[d.getNum()]) {
						switch(d) {
							case UP:
								for (int k = 0; k < SQUARE_SIZE; k++) {
									dungeon.setTileType((i * SQUARE_SIZE) + k, j * SQUARE_SIZE, DungeonTileTypeEnum.WALL);
								}
								break;
							case DOWN:
								for (int k = 0; k < SQUARE_SIZE; k++) {
									dungeon.setTileType((i * SQUARE_SIZE) + k, (j * SQUARE_SIZE) + SQUARE_SIZE, DungeonTileTypeEnum.WALL);
								}
								break;
							case LEFT:
								for (int k = 0; k < SQUARE_SIZE; k++) {
									dungeon.setTileType(i * SQUARE_SIZE, (j * SQUARE_SIZE) + k, DungeonTileTypeEnum.WALL);
								}
								break;
							case RIGHT:
								for (int k = 0; k < SQUARE_SIZE; k++) {
									dungeon.setTileType((i * SQUARE_SIZE) + SQUARE_SIZE, (j * SQUARE_SIZE) + k, DungeonTileTypeEnum.WALL);
								}
								break;
						}
					}
				}
			}
		}
		
		dungeon.setStartx(1);
		dungeon.setStarty(1);
		
		return dungeon;
	}
}