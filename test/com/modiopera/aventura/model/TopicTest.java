package com.modiopera.aventura.model;


import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.modiopera.aventura.model.conversation.Conversation;

public class TopicTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testIsActive() {
		Topic topic = new Topic();
		Assert.assertFalse(topic.isActive());
		topic.setActive(true);
		Assert.assertTrue(topic.isActive());
	}
	
	@Test
	public void testGetConversations() {
		Topic topic = new Topic();
		Assert.assertNull(topic.getConversations());
		Conversation conversation = new Conversation();
		Person person = new Person();
		Map<Person, Conversation> map = new HashMap<Person, Conversation>();
		map.put(person, conversation);
		topic.setConversations(map);
		Assert.assertNotNull(topic.getConversations());
		Assert.assertFalse(topic.getConversations().isEmpty());
	}

	@Test
	public void testAddConversation() {
		Topic topic = new Topic();
		Conversation conversation = new Conversation();
		Person person = new Person();
		topic.addConversation(person, conversation);
		Assert.assertNotNull(topic.getConversations());
		Assert.assertFalse(topic.getConversations().isEmpty());
	}

	@Test
	public void testActivate() {
		Topic topic = new Topic();
		Assert.assertFalse(topic.isActive());
		Conversation conversation = new Conversation();
		Person person = new Person();
		Map<Person, Conversation> map = new HashMap<Person, Conversation>();
		map.put(person, conversation);
		topic.setConversations(map);
		Assert.assertTrue(person.getConversations().isEmpty());
		topic.activate();
		Assert.assertFalse(person.getConversations().isEmpty());
	}
}
