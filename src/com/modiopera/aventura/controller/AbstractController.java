package com.modiopera.aventura.controller;

import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.modiopera.aventura.model.Person;
import com.modiopera.aventura.model.PlayerDataMap;
import com.modiopera.aventura.model.Quest;
import com.modiopera.aventura.model.Town;
import com.modiopera.aventura.model.conversation.Conversation;
import com.modiopera.aventura.model.factory.FactoryFactory;
import com.modiopera.aventura.parser.xml.XMLGameDataParser;
import com.modiopera.aventura.parser.xml.XMLParserException;
import com.modiopera.aventura.view.IGameView;

public abstract class AbstractController {

	protected IGameView view;
	protected PlayerDataMap playerData;

	protected Town currentTown;
	protected Person currentPerson;
	
	protected Map<String, Person> personMap;
	protected Map<String, Conversation> conversationMap;

	public void start() {
		this.setup();
		this.view.enterTown(this.currentTown);
	}

	public Town getTown() {
		return this.currentTown;
	}
	
	public void setTown(Town town) {
		this.currentTown = town;
	}
	
	public Person getCurrentPerson() {
		return this.currentPerson;
	}
	
	public void setCurrentPerson(Person person) {
		this.currentPerson = person;
	}

	public Person getPerson(String id) {
		return this.personMap.get(id);
	}

	public Conversation getConversation(String id) {
		return this.conversationMap.get(id);
	}

	protected void setup() {
		try {
            XMLGameDataParser.loadData();
        } catch (XMLParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }
		this.personMap = new HashMap<String, Person>();
		this.conversationMap = new HashMap<String, Conversation>();
		
		Town town = FactoryFactory.getInstance().getTownFactory().getRandom();
		if (town.isOpen()) {
		    int pop = (int) (Math.random() * 5);
	        List<Person> people = FactoryFactory.getInstance().getPersonFactory()
	                .getMultiple(pop + 5);
		    for (Person p : people) {
		        if (!town.getTownsPeople().contains(p)) {
		            town.getTownsPeople().add(p);
		        }
		    }
		    List<Conversation> conversations = FactoryFactory.getInstance().getConversationFactory().getMultiple(pop + 5);
		    Collections.shuffle(conversations);
		    for (Conversation c : conversations) {
		        people.get((int)(Math.random() * people.size())).addConversation(c);
		    }
		}
		
		Collections.shuffle(town.getTownsPeople());
		Collections.shuffle(town.getCritters());
		Collections.shuffle(town.getItems());
		
		for (Person p : town.getTownsPeople()) {
            this.personMap.put(p.getId(), p);
            for (Conversation c : p.getConversations()) {
                this.conversationMap.put(c.getId(), c);
            }
        }
		
		/*
		Quest quest = FactoryFactory.getInstance().getQuestFactory()
				.getRandom();
		this.assignQuest(quest, people);
		*/
		this.currentTown = town;
	}

	protected void assignQuest(Quest quest, List<Person> people) {
		if (people.size() < 2) {
			throw new InvalidParameterException(
					"People list must have at least two people");
		}
		Collections.shuffle(people);
		Iterator<Person> group = people.iterator();

		this.conversationMap.put(quest.getExplination().getId(),
				quest.getExplination());
		group.next().addQuest(quest);
/*
		this.conversationMap.put(quest.getSolution().getId(),
				quest.getSolution());
		group.next().addConversation(quest.getSolution());
*/
		if (quest.getInformation() != null) {
			for (Conversation c : quest.getInformation()) {
				if (group.hasNext()) {
					this.conversationMap.put(c.getId(), c);
					group.next().addConversation(c);
				}
			}
		}
		Collections.shuffle(people);
	}

	public void setView(IGameView view) {
		this.view = view;
		this.view.setController(this);
		EventHandler.getInstance().setView(view);
	}

	public IGameView getView() {
		return this.view;
	}

	public PlayerDataMap getPlayerData() {
		return playerData;
	}

	public void setPlayerData(PlayerDataMap playerData) {
		this.playerData = playerData;
		EventHandler.getInstance().setPlayerDataMap(playerData);
	}
	
	
}