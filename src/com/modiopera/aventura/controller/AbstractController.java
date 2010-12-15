package com.modiopera.aventura.controller;

import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.modiopera.aventura.model.Person;
import com.modiopera.aventura.model.Quest;
import com.modiopera.aventura.model.Town;
import com.modiopera.aventura.model.conversation.Conversation;
import com.modiopera.aventura.model.factory.FactoryFactory;
import com.modiopera.aventura.parser.xml.XMLGameDataParser;
import com.modiopera.aventura.view.IGameView;

public abstract class AbstractController {

	protected IGameView view;

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
		XMLGameDataParser.loadData();
		this.personMap = new HashMap<String, Person>();
		this.conversationMap = new HashMap<String, Conversation>();
		
		int pop = (int) (Math.random() * 5);
		List<Person> people = FactoryFactory.getInstance().getPersonFactory()
				.getMultiple(pop + 5);
		for (Person p : people) {
			this.personMap.put(p.getId(), p);
			for (Conversation c : p.getConversations()) {
				this.conversationMap.put(c.getId(), c);
			}
		}
		Town town = FactoryFactory.getInstance().getTownFactory().getRandom();
		town.setTownsPeople(people);

		Quest quest = FactoryFactory.getInstance().getQuestFactory()
				.getRandom();
		this.assignQuest(quest, people);
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

		this.conversationMap.put(quest.getSolution().getId(),
				quest.getSolution());
		group.next().addConversation(quest.getSolution());

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
	}

	public IGameView getView() {
		return this.view;
	}
}