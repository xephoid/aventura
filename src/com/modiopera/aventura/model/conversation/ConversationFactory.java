package com.modiopera.aventura.model.conversation;

public class ConversationFactory {

	public static Conversation getSimpleConversation(Dialog dialog) {
		Conversation conversation = new Conversation();
		DialogNode root = new DialogNode();
		root.setDialog(dialog);
		conversation.setRoot(root);
		try {
			conversation.init();
		} catch (ConversationException e) {
			// TODO: logger!
			System.out.println("Conversation not initialized!");
		}
		return conversation;
	}
}
