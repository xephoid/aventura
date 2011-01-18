package com.modiopera.aventura.controller;

import java.util.Map;
import java.util.Map.Entry;

import com.modiopera.aventura.controller.event.EventEnum;
import com.modiopera.aventura.controller.event.EventHandler;
import com.modiopera.aventura.controller.event.IEventListener;
import com.modiopera.aventura.model.Critter;
import com.modiopera.aventura.model.GameObject;
import com.modiopera.aventura.model.Item;
import com.modiopera.aventura.model.Quest;
import com.modiopera.aventura.model.conversation.Conversation;

public class QuestController extends BaseController {
	private Quest currentQuest;
	private Map<String, Quest> questMap;
	
	@Override
	public void setEventHandler(EventHandler eh) {
		super.setEventHandler(eh);
		
		eh.registerListener(new IEventListener<Critter>() {
			@Override
			public void handleEvent(EventEnum eventType, GameObject obj) {
				if (EventEnum.KILL_CRITTER.equals(eventType)) {
					killedCritter((Critter)obj);
				}
			}
			@Override
			public Class<? extends GameObject> getChildType() {
				return Critter.class;
			}
		});
	}
	
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
	
	public void killedCritter(Critter critter) {
		for (Entry<String, Quest> entry : this.questMap.entrySet()) {
			Quest quest = entry.getValue();
			if (quest.getCritters().contains(critter)) {
				// TODO
			}
		}
	}
}
