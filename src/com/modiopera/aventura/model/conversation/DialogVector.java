package com.modiopera.aventura.model.conversation;

import com.modiopera.aventura.model.StatRequirement;

public class DialogVector {
	private Dialog playerDialog;
	private DialogNode parent;
	private DialogNode child;
	private StatRequirement requirement;
	
	public Dialog getPlayerDialog() {
		return playerDialog;
	}
	public void setPlayerDialog(Dialog playerDialog) {
		this.playerDialog = playerDialog;
	}
	public DialogNode getParent() {
		return parent;
	}
	public void setParent(DialogNode parent) {
		this.parent = parent;
	}
	public DialogNode getChild() {
		return child;
	}
	public void setChild(DialogNode child) {
		this.child = child;
	}
	public void setRequirement(StatRequirement requirement) {
		this.requirement = requirement;
	}
	public StatRequirement getRequirement() {
		return requirement;
	}
}
