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

import com.modiopera.aventura.controller.EventEnum;
import com.modiopera.aventura.controller.EventHandler;
import com.modiopera.aventura.controller.actions.Action;
import com.modiopera.aventura.controller.actions.ActivateTopicAction;
import com.modiopera.aventura.model.GameObject;
import com.modiopera.aventura.model.Person;
import com.modiopera.aventura.model.Quest;
import com.modiopera.aventura.model.Topic;
import com.modiopera.aventura.model.Town;
import com.modiopera.aventura.model.conversation.Conversation;
import com.modiopera.aventura.model.conversation.Dialog;
import com.modiopera.aventura.model.conversation.DialogNode;
import com.modiopera.aventura.model.conversation.DialogVector;
import com.modiopera.aventura.model.factory.FactoryFactory;

public class XMLGameDataParser {
    
    private static Map<String, DialogVector> dialogMap;
    private static Map<String, Topic> topicMap = new HashMap<String, Topic>();
    
    public static void main(String[] argv) {
        loadData();
    }

	public static void loadData() {
		parseTownData(getDocument("data/towns.xml"));
		parsePersonData(getDocument("data/people.xml"));
		parseQuestData(getDocument("data/quests.xml"));
	}
	
	public static Document getDocument(String filename) {
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
        } catch (SAXException e) {
        	Exception x = e.getException ();
        	((x == null) ? e : x).printStackTrace ();
        } catch (Throwable t) {
        	t.printStackTrace ();
        }
        return xml;
	}
	
	private static void parseTownData(Document xml) {
		if (xml != null) {
			NodeList towns = xml.getElementsByTagName(XMLParserConstants.PARSER_TAG_TOWN);
			for (int i = 0; i < towns.getLength(); i++) {
				Node node = towns.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Town town = new Town();
					Element townElem = (Element) node;
					town.setName(townElem.getAttribute(XMLParserConstants.PARSER_ATTRIBUTE_NAME));
					
					town.setDescrption(extractTextFromNode(XMLParserConstants.PARSER_ATTRIBUTE_DESCRIPTION, node));
					FactoryFactory.getInstance().getTownFactory().add(town);
				}
			}
		}
	}
	
	private static void parsePersonData(Document xml) {
		if (xml == null) {
			// TODO: throw exception!
			return;
		}
		
		NodeList people = xml.getElementsByTagName(XMLParserConstants.PARSER_TAG_PERSON);
		
		for (int i = 0; i < people.getLength(); i++) {
			Element elem = (Element) people.item(i);
			Person person = new Person();
			person.setName(elem.getAttribute("name"));
			
			// initial greeting
			Dialog dialog = new Dialog();
			dialog.setText(extractTextFromNode(XMLParserConstants.PARSER_TAG_INITIAL_GREETING, elem));
			person.setInitialGreeting(dialog);
			
			// greeting
			dialog = new Dialog();
			dialog.setText(extractTextFromNode(XMLParserConstants.PARSER_TAG_GREETING, elem));
			person.setGreeting(dialog);
			
			// introduction
			dialog = new Dialog();
			dialog.setText(extractTextFromNode(XMLParserConstants.PARSER_TAG_INTRODUCTION, elem));
			person.setIntroduction(dialog);
			
			Element portrait = (Element) extractFirstNode("portrait", elem);
			person.setImageUrl(portrait.getAttribute("url"));
			
			// action on speak to
			parseAction(EventEnum.SPEAK_TO_PERSON, 
					extractFirstNode(XMLParserConstants.PARSER_TAG_ACTION, elem),
					person);
			
			FactoryFactory.getInstance().getPersonFactory().add(person);
		}
	}
	
	private static void parseQuestData(Document xml) {
		if (xml == null) {
			return;
		}
		dialogMap = new HashMap<String, DialogVector>();
		NodeList questData = xml.getElementsByTagName(XMLParserConstants.PARSER_TAG_QUEST);
		for (int i = 0; i < questData.getLength(); i++) {
			Node node = questData.item(i);
			Node exNode = extractFirstNode(XMLParserConstants.PARSER_TAG_EXPLINATION, node);
			Quest quest = new Quest();
			//Dialog intro = new Dialog();
			//intro.setText(extractTextFromNode(XMLParserConstants.PARSER_TAG_DIALOG, exNode));
			//quest.setIntro(intro);
			quest.setExplination(parseConversation((Element) extractFirstNode(XMLParserConstants.PARSER_TAG_CONVERSATION, exNode)));
			
			Element infoElem = (Element) extractFirstNode(XMLParserConstants.PARSER_TAG_INFORMATION, node);
			NodeList infoNodes = infoElem.getElementsByTagName(XMLParserConstants.PARSER_TAG_CONVERSATION);
			List<Conversation> infos = new ArrayList<Conversation>();
			for(int j = 0; j < infoNodes.getLength(); j++) {
			    infos.add(parseConversation((Element) infoNodes.item(j)));
			}
			quest.setInformation(infos);
			
			Element solElem = (Element) extractFirstNode(XMLParserConstants.PARSER_TAG_SOLUTION, node);
			quest.setSolution(parseConversation((Element) extractFirstNode(XMLParserConstants.PARSER_TAG_CONVERSATION, solElem)));
			
			FactoryFactory.getInstance().getQuestFactory().add(quest);
		}
	}
	
	protected static Conversation parseConversation(Element xml) {
		Conversation conv = new Conversation();
		
		if (xml.hasAttribute(XMLParserConstants.PARSER_ATTRIBUTE_TOPIC)) {
			conv.setTopic(getTopic(xml.getAttribute(XMLParserConstants.PARSER_ATTRIBUTE_TOPIC)));
		}
		Node node = extractFirstNode(XMLParserConstants.PARSER_TAG_NODE, xml);
		if (node != null) {
			DialogNode root = new DialogNode();
			Dialog start = new Dialog();
			start.setText(extractTextFromNode(XMLParserConstants.PARSER_TAG_DIALOG, xml));
			
			root.setDialog(start);
			parseSubNodes(node, root);
			
			conv.setRoot(root);
			
			Dialog exit = new Dialog();
			exit.setText(extractTextFromNode("exit", xml));
			conv.setExit(exit);
			
			parseAction(EventEnum.INITIATE_CONVERSATION, 
					extractFirstNode(XMLParserConstants.PARSER_TAG_ACTION, xml),
					conv);
		}
		
		// TODO: events
		return conv;
	}
	
	protected static void parseSubNodes(Node xml, DialogNode parent) {
		Element elem = (Element) xml;
		
		DialogNode leaf = new DialogNode();
		
		if(elem.hasAttribute("ref")) {
		    // Node is a refference to another node.
		    // Create a copy of the vector to give to the new parent
		    DialogVector example = dialogMap.get(elem.getAttribute("ref"));
		    if (example == null) {
		        // TODO: throw exeption!
		    }
		    DialogVector copy = new DialogVector();
		    copy.setChild(example.getChild());
		    copy.setPlayerDialog(example.getPlayerDialog());
		    parent.addVector(copy);
		} else {
		    Dialog dialog = new Dialog();
		    dialog.setText(extractTextFromNode(XMLParserConstants.PARSER_TAG_DIALOG, xml));
		    leaf.setDialog(dialog);
		    Dialog player = new Dialog();
	        player.setText(extractTextFromNode(XMLParserConstants.PARSER_TAG_PLAYER, xml));
	        
	        parseAction(EventEnum.SPEAK_DIALOG, 
					extractFirstNode(XMLParserConstants.PARSER_TAG_ACTION, xml),
					dialog);
	        
	        DialogVector vector = parent.addNode(player, leaf);
	        if (elem.hasAttribute("id") && !dialogMap.containsKey(elem.getAttribute("id"))) {
	            dialogMap.put(elem.getAttribute("id"), vector);
	        }
		}
		
		List<Node> nodeChildren = getChildrenWithTag(XMLParserConstants.PARSER_TAG_NODE, elem); 
		for(Node child : nodeChildren) {
		    parseSubNodes(child, leaf);
		}
	}
	
	private static List<Node> getChildrenWithTag(String tag, Node node) {
	    List<Node> results = new ArrayList<Node>();
	    NodeList children = node.getChildNodes();
	    for(int i = 0; i < children.getLength(); i++) {
	        Node child = children.item(i);
	        if (child.getNodeName().equalsIgnoreCase(tag)) {
	            results.add(child);
	        }
	    }
	    return results;
	}
	
	private static String extractTextFromNode(String childName,  Node xml) {
		NodeList nodes = xml.getChildNodes();
		
		for (int i=0 ; i < nodes.getLength() ; i++) {
			Node node = nodes.item(i);
			if (node.getNodeName().equalsIgnoreCase(childName)) {
				return node.getTextContent().trim();
			}
		}
		return null;
	}
	
	private static Node extractFirstNode(String name, Node xml) {
		NodeList nodes = xml.getChildNodes();
		
		for (int i=0 ; i < nodes.getLength() ; i++) {
			Node node = nodes.item(i);
			if (node.getNodeName().equalsIgnoreCase(name)) {
				return node;
			}
		}
		return null;
	}
	
	private static void parseAction(EventEnum eventType, Node xml, GameObject actionObject) {
		if (xml != null) {
			Element elem = (Element) xml;
			if (elem.hasAttribute(XMLParserConstants.PARSER_ATTRIBUTE_TOPIC)) {
				Topic topic = getTopic(elem.getAttribute(XMLParserConstants.PARSER_ATTRIBUTE_TOPIC));
				ActivateTopicAction action = new ActivateTopicAction();
				action.setTopic(topic);
				EventHandler.getInstance().mapEventToAction(eventType, actionObject, action);
			}
		}
	}
	
	private static Topic getTopic(String id) {
		if (topicMap.containsKey(id)) {
			return topicMap.get(id);
		}
		Topic topic = new Topic();
		topic.setId(id);
		topicMap.put(id, topic);
		return topic;
	}
}
