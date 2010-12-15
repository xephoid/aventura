package com.modiopera.aventura.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.modiopera.aventura.model.conversation.Conversation;
import com.modiopera.aventura.model.conversation.Dialog;

public class PersonTest {

	@Test
	public void testIsMetBefore() {
		Person person = new Person();
		Assert.assertFalse(person.isMetBefore());
		person.setMetBefore(true);
		Assert.assertTrue(person.isMetBefore());
	}

	@Test
	public void testGetInitialGreeting() {
		Person person = new Person();
		Dialog dialog = new Dialog();
		dialog.setText("initial greeting");
		Assert.assertNull(person.getInitialGreeting());
		person.setInitialGreeting(dialog);
		Assert.assertEquals(dialog, person.getInitialGreeting());
	}

	@Test
	public void testGetGreeting() {
		Person person = new Person();
		Dialog dialog = new Dialog();
		dialog.setText("greeting");
		Assert.assertNull(person.getGreeting());
		person.setGreeting(dialog);
		Assert.assertEquals(dialog, person.getGreeting());
	}

	@Test
	public void testGetIntroduction() {
		Person person = new Person();
		Dialog dialog = new Dialog();
		dialog.setText("intro");
		Assert.assertNull(person.getIntroduction());
		person.setIntroduction(dialog);
		Assert.assertEquals(dialog, person.getIntroduction());
	}

	@Test
	public void testGetQuests() {
		Person person = new Person();
		Assert.assertNull(person.getQuests());
		Quest quest = new Quest();
		List<Quest> quests = new ArrayList<Quest>();
		quests.add(quest);
		person.setQuests(quests);
		Assert.assertFalse(person.getQuests().isEmpty());
	}

	@Test
	public void testAddQuest() {
		Person person = new Person();
		Assert.assertNull(person.getQuests());
		Quest quest = new Quest();
		person.addQuest(quest);
		Assert.assertFalse(person.getQuests().isEmpty());
	}

	@Test
	public void testGetAvailableQuest() {
		Person person = new Person();
		Quest quest = new Quest();
		List<Quest> quests = new ArrayList<Quest>();
		quests.add(quest);
		person.setQuests(quests);
		Assert.assertEquals(quest, person.getAvailableQuest());
		quest.setCompleted(true);
		Assert.assertNull(person.getAvailableQuest());
	}

	@Test
	public void testGetConversations() {
		Person person = new Person();
		Assert.assertNull(person.getConversations());
		Conversation conversation = new Conversation();
		List<Conversation> list = new ArrayList<Conversation>();
		list.add(conversation);
		person.setConversations(list);
		Assert.assertFalse(person.getConversations().isEmpty());
	}

	@Test
	public void testAddConversation() {
		Person person = new Person();
		Assert.assertNull(person.getConversations());
		Conversation conversation = new Conversation();
		person.addConversation(conversation);
		Assert.assertFalse(person.getConversations().isEmpty());
	}
}
