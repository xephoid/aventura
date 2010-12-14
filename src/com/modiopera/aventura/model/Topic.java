package com.modiopera.aventura.model;

import java.util.HashMap;
import java.util.Map;

import com.modiopera.aventura.model.conversation.Conversation;

public class Topic extends GameObject {

	private static final long serialVersionUID = -793057833705995076L;
	
	private Map<Person, Conversation> conversations;
	private boolean active = false;

	public void activate() {
		if (!this.isActive()) {
			for(Map.Entry<Person, Conversation> entry : this.conversations.entrySet()) {
				entry.getKey().addConversation(entry.getValue());
			}
			this.active = true;
		}
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
