package com.modiopera.aventura.model;

import java.util.List;

import com.modiopera.aventura.model.conversation.Conversation;
import com.modiopera.aventura.model.conversation.Dialog;

public class Quest extends GameObject {
	
	private boolean completed = false;
	private Conversation explination;
	private List<Conversation> information;
	private Conversation solution;
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
    
    public Conversation getSolution() {
        return solution;
    }
    public void setSolution(Conversation solution) {
        this.solution = solution;
    }
}
