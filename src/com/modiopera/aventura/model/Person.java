package com.modiopera.aventura.model;

import java.util.ArrayList;
import java.util.List;

import com.modiopera.aventura.model.conversation.Conversation;
import com.modiopera.aventura.model.conversation.Dialog;

public class Person extends GameObject {
	private static final long serialVersionUID = -9074309636174918540L;
	
	private boolean metBefore;
	private Dialog initialGreeting;
	private Dialog greeting;
	private Dialog introduction;
	private List<Quest> quests;
	private List<Conversation> conversations = new ArrayList<Conversation>();
	private String imageUrl;
	private GenderEnum gender;
	
	public boolean isMetBefore() {
		return metBefore;
	}
	public void setMetBefore(boolean metBefore) {
		this.metBefore = metBefore;
	}
	public Dialog getInitialGreeting() {
		return initialGreeting;
	}
	public void setInitialGreeting(Dialog initialGreeting) {
		this.initialGreeting = initialGreeting;
	}
	public Dialog getGreeting() {
		return greeting;
	}
	public void setGreeting(Dialog greeting) {
		this.greeting = greeting;
	}
	public Dialog getIntroduction() {
		return introduction;
	}
	public void setIntroduction(Dialog introduction) {
		this.introduction = introduction;
	}
	public List<Quest> getQuests() {
		return quests;
	}
	public void setQuests(List<Quest> quests) {
		this.quests = quests;
	}
	
	public void addQuest(Quest quest) {
		if (this.quests == null) {
			this.quests = new ArrayList<Quest>();
		}
		this.quests.add(quest);
	}
	
	public Quest getAvailableQuest() {
		if (this.quests == null || this.quests.isEmpty()) {
			return null;
		}
		for(Quest q: this.quests) {
			if (!q.isCompleted()) {
				return q;
			}
		}
		return null;
	}
	
	public List<Conversation> getConversations() {
		return conversations;
	}
	
	/**
	 * This function does not account for topics.
	 * @param conversations
	 */
	public void setConversations(List<Conversation> conversations) {
		this.conversations = conversations;
	}
	
	public void addConversation(Conversation c) {
		// if conversation is part of a topic 
		// add it to the topic instead
		if (c.getTopic() != null) {
			c.getTopic().addConversation(this, c);
			// TODO: this may bite me in the ass...
			c.setTopic(null);
		} else {
			this.conversations.add(c);
		}
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}
	
	public GenderEnum getGender() {
		return gender;
	}
}