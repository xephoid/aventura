package com.modiopera.aventura.controller.event;

import com.modiopera.aventura.model.GameObject;

public interface IEventListener<ObjectType extends GameObject> {
	public void handleEvent(EventEnum eventType, GameObject obj);
	public Class<? extends GameObject> getChildType();
}
