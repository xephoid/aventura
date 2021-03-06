package com.modiopera.aventura.model;

import java.util.HashMap;
import java.util.Map;

import com.modiopera.aventura.model.conversation.Conversation;

public class Topic extends GameObject {

	private Map<Person, Conversation> conversations;
	private boolean active = false;

	public boolean activate() {
		if (!this.isActive()) {
			for(Map.Entry<Person, Conversation> entry : this.conversations.entrySet()) {
				entry.getKey().addConversation(entry.getValue());
			}
			this.active = true;
			return true;
		}
		return false;
	}
	
	public Map<Person, Conversation> getConversations() {
		return conversations;
	}
	public void setConversations(Map<Person, Conversation> conversations) {
		this.conversations = conversations;
	}
	
	public void addConversation(Person person, Conversation conversation) {
		if (this.conversations == null) {
			this.conversations = new HashMap<Person, Conversation>();
		}
		this.conversations.put(person, conversation);
	}
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
}
