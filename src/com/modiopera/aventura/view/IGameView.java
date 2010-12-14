package com.modiopera.aventura.view;

import com.modiopera.aventura.controller.AbstractController;
import com.modiopera.aventura.model.Person;
import com.modiopera.aventura.model.Town;
import com.modiopera.aventura.model.conversation.Conversation;
import com.modiopera.aventura.model.dungeon.Dungeon;

public interface IGameView {

	public void enterTown(Town town);
	public void talkToPerson(Person person);
	public void startConversation(Conversation conversation);
	public void enterDungeon(Dungeon dungeon);
	public void queueEvent();
	public void setController(AbstractController controller);
	public AbstractController getController();
}
