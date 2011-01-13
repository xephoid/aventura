package com.modiopera.aventura.view;

import java.util.List;

import com.modiopera.aventura.controller.actions.Action;
import com.modiopera.aventura.model.Item;
import com.modiopera.aventura.model.Person;
import com.modiopera.aventura.model.Quest;
import com.modiopera.aventura.model.Town;
import com.modiopera.aventura.model.conversation.Conversation;
import com.modiopera.aventura.model.conversation.Dialog;
import com.modiopera.aventura.model.dungeon.Dungeon;

public interface IGameView {
	public void showTown(Town town);
	public void showPerson(Person person);
	public void showConversation(Conversation conversation);
	public void showDungeon(Dungeon dungeon);
	public void eventOccured(Action action);
	
	public void showDialog(Dialog dailog);
	public void setDialogOptions(List<Dialog> dialogs);
	public void questAccepted(Quest quest);
	public void questCompleted(Quest quest);
	public void aquireItem(Item item);
	public void useItem(Item item);
}
