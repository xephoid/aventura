package com.modiopera.aventura.controller;

import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.modiopera.aventura.controller.event.EventEnum;
import com.modiopera.aventura.controller.event.EventHandler;
import com.modiopera.aventura.controller.event.IEventListener;
import com.modiopera.aventura.model.Critter;
import com.modiopera.aventura.model.GameObject;
import com.modiopera.aventura.model.Item;
import com.modiopera.aventura.model.Person;
import com.modiopera.aventura.model.Quest;
import com.modiopera.aventura.model.Town;
import com.modiopera.aventura.model.conversation.Conversation;
import com.modiopera.aventura.model.dungeon.MazeGenerator;
import com.modiopera.aventura.model.factory.FactoryFactory;

/**
 * Controls all town related stuff.
 * 
 * @author xephoid
 *
 */
public class TownsController extends BaseController {

	private Map<String, Town> townMap;
	
	private Town currentTown;
	
	@Override
	public void setEventHandler(EventHandler eh) {
		super.setEventHandler(eh);
		eh.registerListener(new IEventListener<Item>() {
			@Override
			public void handleEvent(EventEnum eventType, GameObject obj) {
				if (EventEnum.AQUIRE_ITEM.equals(eventType)) {
					removeItemFromTown((Item) obj);
				}
			}
			@Override
			public Class<Item> getChildType() {
				return Item.class;
			}
		});
	}
	
	@Override
	protected GameObject getCurrentObject() {
		return this.currentTown;
	}

	public void setTownMap(Map<String, Town> townMap) {
		this.townMap = townMap;
	}
	
	public Town getTown(String id) {
		return this.townMap.get(id);
	}
	
	public Town getRandomTown() {
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
		    
		    town.setCritters(FactoryFactory.getInstance().getCritterFactory().getMultiple(5));
		    town.getItems().addAll(FactoryFactory.getInstance().getItemFactory().getMultiple(2));
		    Quest quest = FactoryFactory.getInstance().getQuestFactory()
				.getRandom();
		    this.assignQuest(quest, people);
		    
		    for (Item i: quest.getItems()) {
		    	town.getItems().add(i);
		    }
		    for (Critter c: quest.getCritters()) {
		    	town.getCritters().add(c);
		    }
		}
		
		Collections.shuffle(town.getTownsPeople());
		Collections.shuffle(town.getCritters());
		Collections.shuffle(town.getItems());
		
		return town;
	}


	protected void assignQuest(Quest quest, List<Person> people) {
		if (people.size() < 2) {
			throw new InvalidParameterException(
					"People list must have at least two people");
		}
		Collections.shuffle(people);
		Person person = people.get(1);
		person.addQuest(quest);
		quest.setRequestor(person);
		if (quest.getInformation() != null) {
			for (Conversation c : quest.getInformation()) {
				person.addConversation(c);
			}
		}
	}
	
	public void enterTown(Town town) {
		this.currentTown = town;
		this.fireEvent(EventEnum.ENTER_TOWN);
		this.view.showTown(town);
	}
	
	public void generateDungeon(int width, int height) {
		MazeGenerator generator = new MazeGenerator();
		if (this.currentTown != null) {
			this.currentTown.setDungeon(generator.generateDungeon(width, height));
		}
	}
	
	@Override
	public Class<? extends GameObject> getChildType() {
		return Town.class;
	}
	
	public void removeItemFromTown(Item item) {
		if (this.currentTown != null) {
			this.currentTown.getItems().remove(item);
		}
	}
}