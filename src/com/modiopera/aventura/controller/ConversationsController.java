package com.modiopera.aventura.controller;

import java.util.Map;

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
	
	public void startConversation(Conversation conversation) {
		try {
			conversation.init();
			view.setDialogOptions(conversation.getOptions(playerData));
			this.currentConversation = conversation;
			fireEvent(EventEnum.INITIATE_CONVERSATION);
		} catch (ConversationException e) {
			e.printStackTrace();
		}
	}
	
	public void traverseConversation(Dialog dialog) {
		try {
			if (this.currentConversation.chooseOption(dialog)) {
				Dialog toSay = this.currentConversation.getCurrentDialog();
				view.showDialog(toSay);
				view.setDialogOptions(this.currentConversation.getOptions(playerData));
				eventHandler.createEvent(EventEnum.SPEAK_DIALOG, toSay);
			}
		} catch (ConversationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Class<? extends GameObject> getChildType() {
		return Conversation.class;
	}
}
