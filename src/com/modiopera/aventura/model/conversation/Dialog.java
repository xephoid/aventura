package com.modiopera.aventura.model.conversation;

import com.modiopera.aventura.model.GameObject;

public class Dialog extends GameObject {
	private String text;
	
	public void setText(String text) {
		this.text = text.trim();
	}
	
	public String getText() {
		return this.text;
	}
	
	@Override
	public String toString() {
		return this.text;
	}
}