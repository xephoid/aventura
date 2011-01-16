package com.modiopera.aventura.controller;

import com.modiopera.aventura.GameSetup;
import com.modiopera.aventura.model.PlayerDataMap;
import com.modiopera.aventura.model.Town;
import com.modiopera.aventura.model.enums.PlayerTypeEnum;
import com.modiopera.aventura.view.IGameView;
import com.modiopera.aventura.view.text.TextBasedView;

public class MainController extends GameSetup {
	private TownsController tControl;
	private IGameView view;
	
	public static void main(String[] args) {
		MainController main = new MainController(new TextBasedView());
		main.setPlayerType(PlayerTypeEnum.NINJA);
		main.start();
	}

	public MainController(IGameView view) {
		this.view = view;
		super.initialize();
	}
	
	public void start() {
		this.tControl.enterTown(this.tControl.getRandomTown());
	}
	
	public void setPlayerType(PlayerTypeEnum type) {
		this.setPlayerData(new PlayerDataMap(type));
	}
	
	@Override
	protected void registerController(BaseController controller) {
		if (Town.class.equals(controller.getChildType())) {
			this.tControl = (TownsController) controller;
		}
		controller.setView(this.view);
	}
}
