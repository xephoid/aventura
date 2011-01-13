package com.modiopera.aventura.model;

import java.util.ArrayList;
import java.util.List;

import com.modiopera.aventura.model.conversation.Conversation;
import com.modiopera.aventura.model.conversation.Dialog;

public class Quest extends GameObject {
	
	private boolean completed = false;
	private Person requestor;
	private List<Person> participants = new ArrayList<Person>();
	private Conversation explination;
	private List<Conversation> information = new ArrayList<Conversation>();
	private List<Conversation> solveds = new ArrayList<Conversation>();
	private List<Item> items = new ArrayList<Item>();
	private List<Critter> critters = new ArrayList<Critter>();
	private Dialog intro;
	
	public boolean isCompleted() {
		return completed;
	}
	
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	public Conversation getExplination() {
		return explination;
	}
	
	public void setExplination(Conversation explination) {
		this.explination = explination;
	}
	
	public Dialog getIntro() {
		return this.intro;
	}
	
	public void setIntro(Dialog dialog) {
		this.intro = dialog;
	}
	
    public List<Conversation> getInformation() {
        return information;
    }
    
    public void setInformation(List<Conversation> information) {
        this.information = information;
    }
    
    public List<Conversation> getSolveds() {
        return this.solveds;
    }
    
    public void setSolution(List<Conversation> solveds) {
        this.solveds = solveds;
    }
    
    public void setRequestor(Person requestor) {
        this.requestor = requestor;
    }

    public Person getRequestor() {
        return requestor;
    }
 
    public List<Person> getParticipants() {
        return this.participants;
    }
    
    public void addParticipant(Person participant) {
        this.participants.add(participant);
    }

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setCritters(List<Critter> critters) {
		this.critters = critters;
	}

	public List<Critter> getCritters() {
		return critters;
	}
}
