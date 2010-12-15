package com.modiopera.aventura.parser.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.modiopera.aventura.controller.Event;
import com.modiopera.aventura.controller.EventEnum;
import com.modiopera.aventura.controller.EventHandler;
import com.modiopera.aventura.controller.actions.Action;
import com.modiopera.aventura.controller.actions.ActivateTopicAction;
import com.modiopera.aventura.model.GameObject;
import com.modiopera.aventura.model.Person;
import com.modiopera.aventura.model.Topic;
import com.modiopera.aventura.model.Town;
import com.modiopera.aventura.model.conversation.Conversation;
import com.modiopera.aventura.model.conversation.Dialog;
import com.modiopera.aventura.model.conversation.DialogNode;
import com.modiopera.aventura.view.GameView;
import com.modiopera.aventura.view.text.TextBasedView;

public class XMLGameParser {

	public static final String PARSER_TAG_TOWN = "town";
	public static final String PARSER_TAG_PERSON = "person";
	public static final String PARSER_TAG_CONVERSATION = "conversation";
	public static final String PARSER_ATTRIBUTE_NAME = "name";
	public static final String PARSER_ATTRIBUTE_DESCRIPTION = "description";
	
	private static XMLGameParser _instance = new XMLGameParser();
	
	private Map<String, Topic> topicMap = new HashMap<String, Topic>();
	
	private XMLGameParser() {}
	
	public static void parseAndRun(String filename) {
		Document xml = null;
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            xml = docBuilder.parse (new File(filename));
            xml.getDocumentElement().normalize();
        } catch (SAXParseException err) {
        	System.out.println("file: " + filename);
        	System.out.println ("** Parsing error" + ", line " 
        			+ err.getLineNumber () + ", uri " + err.getSystemId ());
        	System.out.println(" " + err.getMessage ());
        	return;
        } catch (SAXException e) {
        	Exception x = e.getException ();
        	((x == null) ? e : x).printStackTrace ();
        	return;
        } catch (Throwable t) {
        	t.printStackTrace ();
        	return;
        }
		
		GameView view = new TextBasedView();
		view.enterTown(_instance.parseTown(xml));
	}
	
	protected Town parseTown(Document xml) {
		Town town = new Town();
		NodeList towns = xml.getElementsByTagName(PARSER_TAG_TOWN);
		Node townNode = towns.item(0);
		if (townNode.getNodeType() == Node.ELEMENT_NODE) {
			Element townElem = (Element) townNode;
			town.setName(townElem.getAttribute(PARSER_ATTRIBUTE_NAME));
			
			town.setDescrption(this.extractTextFromNode("description", xml));
		}
		town.setDescrption(this.extractTextFromNode(PARSER_ATTRIBUTE_DESCRIPTION, xml));
		town.setTownsPeople(this.parseTownsPeople(xml));
		return town;
	}
	
	protected List<Person> parseTownsPeople(Document xml) {
		List<Person> people = new ArrayList<Person>();
		NodeList children = xml.getElementsByTagName(PARSER_TAG_PERSON);
		
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			people.add(this.parsePerson((Element)node));
		}
		return people;
	}
	
	protected Person parsePerson(Element xml) {
		Person person = new Person();
		person.setName(xml.getAttribute("name"));
		
		// initial greeting
		Dialog dialog = new Dialog();
		dialog.setText(this.extractTextFromNode("initialGreeting", xml));
		person.setInitialGreeting(dialog);
		
		// greeting
		dialog = new Dialog();
		dialog.setText(this.extractTextFromNode("greeting", xml));
		person.setGreeting(dialog);
		
		// introduction
		dialog = new Dialog();
		dialog.setText(this.extractTextFromNode("introduction", xml));
		person.setIntroduction(dialog);
		
		// conversations
		person.setConversations(this.parseConversations(person, xml));
		
		// events
		this.parseEvent(person, this.extractFirstNode("event", xml));
		
		return person;
	}
	
	protected List<Conversation> parseConversations(Person parent, Element xml) {
		NodeList children = xml.getChildNodes();
		List<Conversation> list = new ArrayList<Conversation>();
		for(int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if (PARSER_TAG_CONVERSATION.equals(node.getNodeName())
					&& node.getNodeType() == Node.ELEMENT_NODE) {
				Conversation conversation = this.parseConversation(parent, (Element) node);
				if (conversation != null) {
					// null because conversation was added to a topic
					list.add(conversation);
				}
			}
		}
		return list;
	}
	
	protected Conversation parseConversation(Person parent, Element xml) {
		Conversation conv = new Conversation();
		
		Node node = this.extractFirstNode("node", xml);
		if (node != null) {
			DialogNode dialogNode = new DialogNode();
			Dialog dialog = new Dialog();
			dialog.setText(this.extractTextFromNode("dialog", node));
			dialogNode.setDialog(dialog);
			dialog = new Dialog();
			dialog.setText(this.extractTextFromNode("option", node));
			//dialogNode.addOption(dialog, dialog);
			conv.setRoot(dialogNode);
		}
		
		// events
		this.parseEvent(conv, this.extractFirstNode("event", xml));
		String topicName = xml.getAttribute("topic");
		if (topicName != null) {
			Topic topic = this.topicMap.get(topicName);
			if (topic == null) {
				topic = new Topic();
				topic.setId(topicName);
				this.topicMap.put(topicName, topic);
			}
			topic.addConversation(parent, conv);
			return null;
		}
		return conv;
	}
	
	protected void parseEvent(GameObject parent, Node xml) {
		if (xml != null) {
			Event event = new Event();
			if (parent instanceof Person) {
				event.setEventType(EventEnum.SPEAK_TO_PERSON);
			} else if(parent instanceof Conversation) {
				event.setEventType(EventEnum.INITIATE_CONVERSATION);
			}
			event.setGameObject(parent);
			this.parseAction(event, this.extractFirstNode("action", xml));
		}
	}
	
	protected void parseAction(Event parent, Node xml) {
		if (xml != null && xml.getNodeType() == Node.ELEMENT_NODE) {
			Element elem = (Element) xml;
			Action action = null;
			String className = elem.getAttribute("class");
			if ("ActivateTopic".equals(className)) {
				elem = (Element) this.extractFirstNode("property", xml);
				ActivateTopicAction poly = new ActivateTopicAction();
				String topicId = elem.getAttribute("id");
				if (!this.topicMap.containsKey(topicId)) {
					Topic topic = new Topic();
					topic.setId(topicId);
					this.topicMap.put(topicId, topic);
				}
				poly.setTopic(this.topicMap.get(topicId));
				action = poly;
			}
			EventHandler.getInstance().mapEventToAction(parent.getEventType(), parent.getGameObject(), action);
		}
	}
	
	private String extractTextFromNode(String childName,  Node xml) {
		NodeList nodes = xml.getChildNodes();
		
		for (int i=0 ; i < nodes.getLength() ; i++) {
			Node node = nodes.item(i);
			if (node.getNodeName().equalsIgnoreCase(childName)) {
				return node.getTextContent().trim();
			}
		}
		return null;
	}
	
	private Node extractFirstNode(String name, Node xml) {
		NodeList nodes = xml.getChildNodes();
		
		for (int i=0 ; i < nodes.getLength() ; i++) {
			Node node = nodes.item(i);
			if (node.getNodeName().equalsIgnoreCase(name)) {
				return node;
			}
		}
		return null;
	}
}
