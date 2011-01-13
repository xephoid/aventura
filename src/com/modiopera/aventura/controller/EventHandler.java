package com.modiopera.aventura.controller;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.modiopera.aventura.controller.actions.Action;
import com.modiopera.aventura.model.GameObject;
import com.modiopera.aventura.model.PlayerDataMap;
import com.modiopera.aventura.view.IGameView;

public class EventHandler {

	private Integer eventCount = 0;
	private Set<Event> eventSet = new HashSet<Event>();
	private Map<EventEnum, Map<GameObject, Action>> eventToActionMap = new HashMap<EventEnum, Map<GameObject,Action>>();
	private List<Action> actions = new ArrayList<Action>();
	private IGameView view;
	private PlayerDataMap playerDataMap;
	
	public EventHandler() {
		for(EventEnum event : EnumSet.allOf(EventEnum.class)) {
			this.eventToActionMap.put(event, new HashMap<GameObject, Action>());
		}
	}
	
	public Event createEvent(EventEnum event, GameObject object) {
		Event e = new Event();
		e.setEventType(event);
		e.setGameObject(object);
		e.setIndex(this.eventCount++);
		this.eventSet.add(e);
		Action action = this.getAction(e);
		if (action != null) {
			action.act();
			this.view.eventOccured(action);
		}
		
		return e;
	}
	
	public void mapEventToAction(EventEnum event, GameObject object, Action action) {
		Map<GameObject, Action> miniMap = this.eventToActionMap.get(event);
		action.setPlayerData(this.playerDataMap);
		miniMap.put(object, action);
		this.actions.add(action);
	}
	
	public Action getAction(Event event) {
		Map<GameObject, Action> miniMap = this.eventToActionMap.get(event.getEventType());
		return miniMap.get(event.getGameObject());
	}
	
	public Integer getEventCount() {
		return eventCount;
	}
	
	public void setView(IGameView view) {
	    this.view = view;
	}
	
	protected void setEventSet(Set<Event> eventSet) {
		this.eventSet = eventSet;
	}
	
	public void setPlayerDataMap(PlayerDataMap dataMap) {
	    for (Action action : this.actions) {
	        action.setPlayerData(dataMap);
	    }
	    this.playerDataMap = dataMap;
	}
}