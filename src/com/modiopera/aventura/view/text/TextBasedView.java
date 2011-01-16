package com.modiopera.aventura.view.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.modiopera.aventura.controller.actions.Action;
import com.modiopera.aventura.model.Item;
import com.modiopera.aventura.model.Person;
import com.modiopera.aventura.model.PlayerDataMap;
import com.modiopera.aventura.model.Quest;
import com.modiopera.aventura.model.Town;
import com.modiopera.aventura.model.conversation.Conversation;
import com.modiopera.aventura.model.conversation.ConversationException;
import com.modiopera.aventura.model.conversation.ConversationFactory;
import com.modiopera.aventura.model.conversation.Dialog;
import com.modiopera.aventura.model.dungeon.Dungeon;
import com.modiopera.aventura.view.IGameView;

public class TextBasedView implements IGameView {
	private static final Logger logger = Logger.getLogger(TextBasedView.class);
	private static final String EXIT_KEY = "q";
	private static final Integer EXIT_CODE = -1;
	
	private Map<Integer, Person> personMap;
	private Map<Integer, Conversation> conversationMap;
	
	private Town currentTown;
	private Person currentPerson;
	private Conversation currentConversation;
	private PlayerDataMap playerData;
	
	@Override
	public void showTown(Town town) {
		this.currentTown = town;
		do {
			System.out.println(town.getName() + ":");
			System.out.println(town.getDescrption());
			System.out.println("People:");
			int option = 1;
			this.personMap = new HashMap<Integer, Person>();
			for(Person person : town.getTownsPeople()) {
				this.personMap.put(option, person);
				System.out.println("(" + option + ") " + person.getName());
				option++;
			}
			this.showPerson(this.getPersonChoice());
		} while(this.currentPerson != null);
	}
	
	public Person getPersonChoice() {
		Integer choice = this.getUserInput(this.personMap);
		if (choice == EXIT_CODE) {
			return null;
		}
		return this.personMap.get(choice);
	}

	@Override
	public void showPerson(Person person) {
		if (person == null) {
			return;
		}
		this.currentPerson = person;
		this.conversationMap = new HashMap<Integer, Conversation>();
		String again = "";
		if (!person.isMetBefore()) {
			System.out.println(person.getName() + ": " + person.getInitialGreeting().getText());
			person.setMetBefore(true);
		} else {
			System.out.println(person.getName() + ": " + person.getGreeting().getText());
			again = " again";
		}
		Integer choice = EXIT_CODE;
		do {
			System.out.println("What do you say to " +person.getName()+ "?");
			System.out.println("(1) Who are you" +again+ "?");
		
			int startIdx = 2;
			Quest quest = person.getAvailableQuest();
			if (quest != null) {
				System.out.println("(2) Do you have anything you need help with?");
				startIdx++;
				Conversation exp = quest.getExplination();
				try {
                    exp.init();
                } catch (ConversationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
				this.conversationMap.put(2, exp);
			}
			this.conversationMap.put(1, ConversationFactory.getSimpleConversation(person.getIntroduction()));
			
			for(Conversation bit : person.getConversations()) {
				try {
					bit.init();
					for(Dialog dialog : bit.getOptions(this.playerData)) {
						System.out.println("(" + startIdx + ") " + dialog.getText());
					}
					this.conversationMap.put(startIdx++, bit);
				} catch(ConversationException e) {
					// TODO: log broken conversation
				}
			}
			System.out.println("(q) I have to go now.");
			choice = this.getUserInput(this.conversationMap);
			if (choice == EXIT_CODE) {
				return;
			}
			this.showConversation(this.conversationMap.get(choice));
		} while (choice != EXIT_CODE);
	}
	
	public Integer getUserInput(Map<Integer, ?> options) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String choice = null;
		boolean valid = false;
		Integer option = null;
		do {
			try {
				System.out.print("Chose an option: ");
				choice = br.readLine();
				if (choice.equals(EXIT_KEY)) {
					return -1;
				}
				try {
					option = Integer.parseInt(choice);
				} catch (NumberFormatException e) {
					option = 0; // should always be invalid
				}
				if (options.containsKey(option)) {
					valid = true;
				} else {
					System.out.println("Invalid option!");
				}
			} catch (IOException e) {
				System.out.println("Invalid input!");
			}
		} while(!valid);
		
		return option;
	}
	
	@Override
	public void showConversation(Conversation conversation) {
		if (conversation == null) {
			this.showPerson(this.currentPerson);
		}
		this.currentConversation = conversation;
		Map<Integer, Dialog> dialogMap = new HashMap<Integer, Dialog>();
		Integer choice = null;
		try {
			do {
				System.out.println(this.currentPerson.getName() + ": " + conversation.getCurrentDialog().getText());
				List<Dialog> options = conversation.getOptions(this.playerData);
				if (options != null && !options.isEmpty()) {
					int idx = 1;
					for(Dialog d : options) {
						System.out.println("(" + idx + ") " + d.getText());
						dialogMap.put(idx, d);
						idx++;
					}
					System.out.println("(q) " + conversation.getExit());
					choice = this.getUserInput(dialogMap);
					if (choice == EXIT_CODE) {
						return;
					}
				}
			} while(choice != null && !conversation.isOver() && conversation.chooseOption(dialogMap.get(choice)));
		} catch(ConversationException e) {
			logger.error(e.getMessage(), e);
			System.out.println(e.getMessage());
		}
	}

    @Override
    public void eventOccured(Action action) {
        System.out.println("Event: " + action);
    }

	@Override
	public void showDungeon(Dungeon dungeon) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showDialog(Dialog dailog) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDialogOptions(List<Dialog> dialogs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void questAccepted(Quest quest) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void questCompleted(Quest quest) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aquireItem(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void useItem(Item item) {
		// TODO Auto-generated method stub
		
	}
}
