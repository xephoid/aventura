package com.modiopera.aventura.controller;

import java.util.Map;
import java.util.Map.Entry;

import com.modiopera.aventura.controller.event.EventEnum;
import com.modiopera.aventura.model.GameObject;
import com.modiopera.aventura.model.conversation.Conversation;
import com.modiopera.aventura.model.conversation.ConversationException;
import com.modiopera.aventura.model.conversation.Dialog;

public class ConversationsController extends BaseController {

	private Conversation currentConversation;
	private Map<String, Conversation> conversationMap;
	
	@Override
	protected GameObject getCurrentObject() {
		return this.currentConversation;
	}

	public void setConversationMap(Map<String, Conversation> conversationMap) {
		this.conversationMap = conversationMap;
	}
	
	public Conversation getConversation(String id) {
		return this.conversationMap.get(id);
	}
	
	public Conversation getCurrentConversation() {
		return currentConversation;
	}
	
	public void startConversation(Conversation conversation) {
		try {
			conversation.init();
			view.showConversation(conversation);
			this.currentConversation = conversation;
			fireEvent(EventEnum.INITIATE_CONVERSATION);
		} catch (ConversationException e) {
			e.printStackTrace();
		}
	}
	
	public void traverseConversation(String dialogId) {
		try {
			if (this.currentConversation.chooseOption(dialogId)) {
				Dialog toSay = this.currentConversation.getCurrentDialog();
				eventHandler.createEvent(EventEnum.SPEAK_DIALOG, toSay);
			}
			view.showConversation(currentConversation);
		} catch (ConversationException e) {
			e.printStackTrace();
		}
	}
	
	public void initializeConversations() {
		for (Entry<String, Conversation> entry : this.conversationMap.entrySet()) {
			try {
				entry.getValue().init();
			} catch (ConversationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public Class<? extends GameObject> getChildType() {
		return Conversation.class;
	}
}
