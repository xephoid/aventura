package com.modiopera.aventura.model.conversation;

import java.util.List;

import com.modiopera.aventura.model.GameObject;
import com.modiopera.aventura.model.PlayerDataMap;
import com.modiopera.aventura.model.Topic;

public class Conversation extends GameObject {

	private boolean initialized = false;
	private DialogNode root;
	private DialogNode current;
	private Dialog exit;
	private Topic topic;
	
	public void init() throws ConversationException {
		if (this.root == null) {
			throw new ConversationException("init called without a root!");
		}
		this.current = this.root;
		this.initialized = true;
	}
	
	public List<Dialog> getOptions(PlayerDataMap player) throws ConversationException {
		if (!this.initialized) {
			throw new ConversationException("Conversation not initialized");
		}
		return this.current.getOptions(player);
	}
	
	public boolean chooseOption(Dialog dialog) throws ConversationException {
		return this.chooseOption(dialog.getId());
	}
	
	public boolean chooseOption(String dialogId) throws ConversationException {
		if (!this.initialized) {
			throw new ConversationException("Conversation not initialized");
		}
		this.current = this.current.traverse(dialogId);
		return true;
	}
	
	public Dialog getCurrentDialog() throws ConversationException {
		if (!this.initialized) {
			throw new ConversationException("Conversation not initialized");
		}
		return this.current.getDialog();
	}
	
	public boolean isOver() {
		return this.current == null || this.current.isEnd();
	}
	
	public void setRoot(DialogNode root) {
		this.root = root;
	}
	
	public DialogNode getRoot() {
		return this.root;
	}

	public Dialog getExit() {
		return exit;
	}

	public void setExit(Dialog exit) {
		this.exit = exit;
	}
	
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	
	public Topic getTopic() {
		return this.topic;
	}
}