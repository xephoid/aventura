package com.modiopera.aventura.model;

import com.modiopera.aventura.controller.EventEnum;
import com.modiopera.aventura.controller.EventHandler;
import com.modiopera.aventura.controller.actions.Action;

public class Item extends GameObject {

    private String imgUrl;
    private Action action;

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

	public void setAction(Action action) {
		this.action = action;
	}

	public Action getAction() {
		return action;
	}
	
	public boolean isConsumable() {
		return this.action == null;
	}
}