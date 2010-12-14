package com.modiopera.aventura.view;

import com.modiopera.aventura.controller.AbstractController;
import com.modiopera.aventura.controller.EventEnum;
import com.modiopera.aventura.controller.EventHandler;
import com.modiopera.aventura.model.Person;
import com.modiopera.aventura.model.Town;
import com.modiopera.aventura.model.conversation.Conversation;
import com.modiopera.aventura.model.conversation.Dialog;
import com.modiopera.aventura.model.dungeon.Dungeon;

public abstract class GameView implements IGameView {
	
	protected AbstractController controller;
	
	public final void enterTown(Town town) {
		EventHandler.getInstance().createEvent(EventEnum.ENTER_TOWN, town);
		this.showTown(town);
	}
	protected abstract void showTown(Town town);
	
	public final void talkToPerson(Person person) {
		EventHandler.getInstance().createEvent(EventEnum.SPEAK_TO_PERSON, person);
		this.speak(person);
	}
	protected abstract void speak(Person person);
	
	public final void startConversation(Conversation conversation) {
		EventHandler.getInstance().createEvent(EventEnum.INITIATE_CONVERSATION, conversation);
		this.converse(conversation);
	}
	protected abstract void converse(Conversation conversation);
	
	public abstract Person getPersonChoice();
	
	public final void spokeDialog(Dialog dialog) {
		EventHandler.getInstance().createEvent(EventEnum.SPEAK_DIALOG, dialog);
	}
	
	public final void enterDungeon(Dungeon dungeon) {
		//EventFactory.getInstance().createEvent(EventEnum., dungeon);
		//this.startDungeon(dungeon);
	}
	//protected abstract void startDungeon(Dungeon dungeon);
	
	public void queueEvent() {
		// TODO!!!!
	}
	public AbstractController getController() {
		return controller;
	}
	public void setController(AbstractController controller) {
		this.controller = controller;
	}
}
