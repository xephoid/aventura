package com.modiopera.aventura.controller;

import com.modiopera.aventura.model.PlayerDataMap;
import com.modiopera.aventura.model.dungeon.Dungeon;
import com.modiopera.aventura.model.enums.PlayerTypeEnum;
import com.modiopera.aventura.view.text.TextBasedView;

public class MainController extends AbstractController {
	public static void main(String[] args) {
		MainController main = new MainController();
		
		main.setView(new TextBasedView());
		main.setPlayerData(new PlayerDataMap(PlayerTypeEnum.NINJA));
		main.start();
	}

	@Override
	protected void initDungeon(Dungeon dungeon) {
		// no-op
	}
}
