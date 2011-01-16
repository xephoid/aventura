package com.modiopera.aventura.controller;

import com.modiopera.aventura.controller.event.EventEnum;
import com.modiopera.aventura.controller.event.EventHandler;
import com.modiopera.aventura.model.GameObject;
import com.modiopera.aventura.model.PlayerDataMap;
import com.modiopera.aventura.view.IGameView;

public abstract class BaseController {
	protected EventHandler eventHandler;
	protected PlayerDataMap playerData;
	protected IGameView view;

	public void setEventHandler(EventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}

	public EventHandler getEventHandler() {
		return eventHandler;
	}
	
	protected void fireEvent(EventEnum eventType) {
		this.eventHandler.createEvent(eventType, this.getCurrentObject());
	}
	
	public void setPlayerData(PlayerDataMap playerData) {
		this.playerData = playerData;
	}
	
	public void setView(IGameView view) {
		this.view = view;
	}
	
	protected abstract GameObject getCurrentObject();
	public abstract Class<? extends GameObject> getChildType();
}
