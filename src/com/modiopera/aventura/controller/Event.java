package com.modiopera.aventura.controller;

import com.modiopera.aventura.model.GameObject;

public class Event {

	private Integer index;
	private EventEnum eventType;
	private GameObject gameObject;
	
	public EventEnum getEventType() {
		return eventType;
	}
	
	public void setEventType(EventEnum eventType) {
		this.eventType = eventType;
	}
	
	public GameObject getGameObject() {
		return gameObject;
	}
	
	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
}
