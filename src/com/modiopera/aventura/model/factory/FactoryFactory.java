package com.modiopera.aventura.model.factory;

import com.modiopera.aventura.model.Critter;
import com.modiopera.aventura.model.Item;
import com.modiopera.aventura.model.Person;
import com.modiopera.aventura.model.Quest;
import com.modiopera.aventura.model.Town;
import com.modiopera.aventura.model.conversation.Conversation;

public class FactoryFactory {

	private FactoryFactory() {}
	private static FactoryFactory _instance = new FactoryFactory();
	
	public static FactoryFactory getInstance() {
		return _instance;
	}
	
	private GameObjectFactory<Person> personFactory = new GameObjectFactory<Person>();
	private GameObjectFactory<Town> townFactory = new GameObjectFactory<Town>();
	private GameObjectFactory<Item> itemFactory = new GameObjectFactory<Item>();
	private GameObjectFactory<Conversation> conversationFactory = new GameObjectFactory<Conversation>();
	private GameObjectFactory<Quest> questFactory = new GameObjectFactory<Quest>();
	private GameObjectFactory<Critter> critterFactory = new GameObjectFactory<Critter>();

	public GameObjectFactory<Person> getPersonFactory() {
		return personFactory;
	}
	public GameObjectFactory<Town> getTownFactory() {
		return townFactory;
	}
	public GameObjectFactory<Item> getItemFactory() {
		return itemFactory;
	}
	public GameObjectFactory<Conversation> getConversationFactory() {
		return conversationFactory;
	}
	public GameObjectFactory<Quest> getQuestFactory() {
		return questFactory;
	}
	public GameObjectFactory<Critter> getCritterFactory() {
		return critterFactory;
	}
}
