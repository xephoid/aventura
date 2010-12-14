package com.modiopera.aventura.controller;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.modiopera.aventura.controller.actions.Action;
import com.modiopera.aventura.model.GameObject;

public class EventHandler {

	private static EventHandler _instance;
	
	private Integer eventCount = 0;
	private Set<Event> eventSet = new HashSet<Event>();
	private Map<EventEnum, Map<GameObject, Action>> eventToActionMap = new HashMap<EventEnum, Map<GameObject,Action>>();
	
	private EventHandler() {
		for(EventEnum event : EnumSet.allOf(EventEnum.class)) {
			this.eventToActionMap.put(event, new HashMap<GameObject, Action>());
		}
	}
	
	public static EventHandler getInstance() {
		if (_instance == null) {
			_instance = new EventHandler();
		}
		return _instance;
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
		}
		
		return e;
	}
	
	public void mapEventToAction(EventEnum event, GameObject object, Action action) {
		Map<GameObject, Action> miniMap = this.eventToActionMap.get(event);
		miniMap.put(object, action);
	}
	
	public Action getAction(Event event) {
		Map<GameObject, Action> miniMap = this.eventToActionMap.get(event.getEventType());
		return miniMap.get(event.getGameObject());
	}
	
	public Integer getEventCount() {
		return eventCount;
	}
	
	protected void setEventSet(Set<Event> eventSet) {
		this.eventSet = eventSet;
	}
}