package com.modiopera.aventura.controller;

import java.util.Map;

import com.modiopera.aventura.model.GameObject;
import com.modiopera.aventura.model.Item;
import com.modiopera.aventura.model.Quest;
import com.modiopera.aventura.model.conversation.Conversation;

public class QuestController extends BaseController {
	private Quest currentQuest;
	private Map<String, Quest> questMap;

	@Override
	protected GameObject getCurrentObject() {
		return this.currentQuest;
	}

	@Override
	public Class<? extends GameObject> getChildType() {
		return Quest.class;
	}

	public void setQuestMap(Map<String, Quest> questMap) {
		this.questMap = questMap;
	}

	public Quest getQuest(String id) {
		return this.questMap.get(id);
	}
	
	public void acceptQuest(Quest quest) {
		this.currentQuest = quest;
		fireEvent(EventEnum.ACCEPT_QUEST);
		view.questAccepted(quest);
	}

	public void completeQuest(Quest quest) {
		quest.setCompleted(true);
        for (Conversation conv : quest.getSolveds()) {
            if (conv.getPerson() == null) {
                quest.getRequestor().addConversation(conv);
            } else {
                conv.getPerson().addConversation(conv);
            }
        }
        for (Item item : quest.getItems()) {
            playerData.removeItem(item);
        }
        this.currentQuest = quest;
        fireEvent(EventEnum.COMPLETE_QUEST);
        view.questCompleted(quest);
	}
}
