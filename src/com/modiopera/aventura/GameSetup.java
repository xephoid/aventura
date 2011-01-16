package com.modiopera.aventura;

import java.util.ArrayList;
import java.util.List;

import com.modiopera.aventura.controller.BaseController;
import com.modiopera.aventura.controller.ConversationsController;
import com.modiopera.aventura.controller.ItemController;
import com.modiopera.aventura.controller.PeopleController;
import com.modiopera.aventura.controller.PlayerController;
import com.modiopera.aventura.controller.QuestController;
import com.modiopera.aventura.controller.TownsController;
import com.modiopera.aventura.controller.event.EventHandler;
import com.modiopera.aventura.model.PlayerDataMap;
import com.modiopera.aventura.parser.xml.XMLGameDataParser;
import com.modiopera.aventura.parser.xml.XMLParserException;
import com.modiopera.aventura.view.IGameView;

public abstract class GameSetup {
	
	protected List<BaseController> controllers = new ArrayList<BaseController>();

	private boolean initialized = false;
	
	protected GameSetup() {}
	
	protected void initialize() {
		if (this.initialized) {
			return;
		}
		
		EventHandler eventHandler = new EventHandler();
		XMLGameDataParser rawGameData = null;
		try {
            rawGameData = XMLGameDataParser.loadData(eventHandler);
        } catch (XMLParserException e) {
            e.printStackTrace();
            return;
        }
        
        PlayerController playerController = new PlayerController();
        this.controllers.add(playerController);
        
        TownsController townsController = new TownsController();
        townsController.setTownMap(rawGameData.getTownMap());
        this.controllers.add(townsController);
        
        PeopleController peopleController = new PeopleController();
        peopleController.setPersonMap(rawGameData.getPersonMap());
        this.controllers.add(peopleController);
        
        ConversationsController cController = new ConversationsController();
        cController.setConversationMap(rawGameData.getConversationMap());
        this.controllers.add(cController);
        
        QuestController questController = new QuestController();
        questController.setQuestMap(rawGameData.getQuestMap());
        this.controllers.add(questController);
        
        ItemController itemController = new ItemController();
        itemController.setItemMap(rawGameData.getItemMap());
        this.controllers.add(itemController);
        
        this.setEventHandler(eventHandler);
        for(BaseController controller : this.controllers) {
        	this.registerController(controller);
        }
        this.initialized = true;
	}
	
	public void setPlayerData(PlayerDataMap playerData) {
		for(BaseController controller : this.controllers) {
			controller.setPlayerData(playerData);
		}
	}
	
	public void setView(IGameView view) {
		for(BaseController controller : this.controllers) {
			controller.setView(view);
		}
	}
	
	private void setEventHandler(EventHandler eh) {
		for(BaseController controller : this.controllers) {
			controller.setEventHandler(eh);
		}
	}
	
	protected abstract void registerController(BaseController controller);
}