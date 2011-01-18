package com.modiopera.aventura.parser.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;
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

import com.modiopera.aventura.controller.actions.ActivateTopicAction;
import com.modiopera.aventura.controller.actions.CompleteQuestAction;
import com.modiopera.aventura.controller.actions.GiveItemToPlayerAction;
import com.modiopera.aventura.controller.event.EventEnum;
import com.modiopera.aventura.controller.event.EventHandler;
import com.modiopera.aventura.model.Critter;
import com.modiopera.aventura.model.GameObject;
import com.modiopera.aventura.model.Item;
import com.modiopera.aventura.model.ItemRequirement;
import com.modiopera.aventura.model.Person;
import com.modiopera.aventura.model.Quest;
import com.modiopera.aventura.model.Requirement;
import com.modiopera.aventura.model.StatRequirement;
import com.modiopera.aventura.model.Topic;
import com.modiopera.aventura.model.Town;
import com.modiopera.aventura.model.conversation.Conversation;
import com.modiopera.aventura.model.conversation.Dialog;
import com.modiopera.aventura.model.conversation.DialogNode;
import com.modiopera.aventura.model.conversation.DialogVector;
import com.modiopera.aventura.model.enums.StatEnum;
import com.modiopera.aventura.model.factory.FactoryFactory;

public class XMLGameDataParser {
	
	private static final XMLGameDataParser _instance = new XMLGameDataParser();
    
    private Map<String, DialogVector> dialogMap = new HashMap<String, DialogVector>();
    private Map<String, Topic> topicMap = new HashMap<String, Topic>();
    private Map<String, Person> personMap = new HashMap<String, Person>();
    private Map<String, Quest> questMap = new HashMap<String, Quest>();
    private Map<String, Town> townMap = new HashMap<String, Town>();
    private Map<String, Item> itemMap = new HashMap<String, Item>();
    private Map<String, Critter> critterMap = new HashMap<String, Critter>();
    private Map<String, Conversation> conversationMap = new HashMap<String, Conversation>();
    private EventHandler eventHandler;
    
    private XMLGameDataParser() {}
    
    public static void main(String[] argv) {
        try {
            loadData(new EventHandler());
        } catch (XMLParserException e) {
            e.printStackTrace();
        }
    }
    
	public static XMLGameDataParser loadData(EventHandler handler) throws XMLParserException {
	    _instance.eventHandler = handler;
	    File townDir = new File("data/towns");
	    
	    for (File file : townDir.listFiles()) {
	        if (file.isFile() && file.toString().endsWith(".xml")) {
	            Document doc = _instance.getDocument(file);
	            List<Town> singleTownList = _instance.parseTownData(doc.getElementsByTagName(XMLParserConstants.PARSER_TAG_TOWN));
	            _instance.townMap.put(singleTownList.get(0).getId(), singleTownList.get(0));
	            System.out.println("Loading town " + file + "...");
	        }
	    }
	    
	    // Random Towns
	    Document townDoc = _instance.getDocument(new File("data/towns.xml"));
	    _instance.parseTownData(townDoc.getElementsByTagName(XMLParserConstants.PARSER_TAG_TOWN));
		
	    // Wandering People
	    Document personDoc = _instance.getDocument(new File("data/people.xml"));
	    _instance.parsePersonData(personDoc.getElementsByTagName(XMLParserConstants.PARSER_TAG_PERSON), false);
		
	    // Random items
        Document itemDoc = _instance.getDocument(new File("data/items.xml"));
        _instance.parseItems(itemDoc.getElementsByTagName("item"));
        
        // Random critters
        Document critterDoc = _instance.getDocument(new File("data/critters.xml"));
        _instance.parseCritters(critterDoc.getElementsByTagName("critter"));
	    
	    // Random quests
	    Document questDoc = _instance.getDocument(new File("data/quests.xml"));
	    _instance.parseQuestData(null, questDoc.getElementsByTagName(XMLParserConstants.PARSER_TAG_QUEST));
	    
	    // Random conversations
	    Document convDoc = _instance.getDocument(new File("data/conversations.xml"));
	    NodeList convNodes = convDoc.getElementsByTagName(XMLParserConstants.PARSER_TAG_CONVERSATION);
	    for (int i = 0; i < convNodes.getLength(); i++) {
	        FactoryFactory.getInstance().getConversationFactory().add(_instance.parseConversation((Element) convNodes.item(i)));
	    }
	    
	    return _instance;
	}
	
	private Document getDocument(File file) {
		Document xml = null;
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            xml = docBuilder.parse (file);
            xml.getDocumentElement().normalize();
        } catch (SAXParseException err) {
        	System.out.println("file: " + file);
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
	
	private List<Town> parseTownData(NodeList towns) throws XMLParserException {
	    List<Town> townList = new ArrayList<Town>();
		if (towns != null) {
			for (int i = 0; i < towns.getLength(); i++) {
				Node node = towns.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Town town = new Town();
					Element townElem = (Element) node;
					town.setName(townElem.getAttribute(XMLParserConstants.PARSER_ATTRIBUTE_NAME));
					
					if (townElem.hasAttribute("id")) {
					    town.setId(townElem.getAttribute("id"));
					}
					
					if (townElem.hasAttribute("open")) {
					    town.setOpen(true);
					}
					
					town.setDescrption(extractTextFromNode(XMLParserConstants.PARSER_ATTRIBUTE_DESCRIPTION, node));
					
					Node peopleNode = extractFirstNode("people", node);
					if (peopleNode != null) {
					    town.setTownsPeople(parsePersonData(peopleNode.getChildNodes(), true));
					}
					town.setItems(parseItems(townElem.getElementsByTagName("item")));
					town.setCritters(parseCritters(townElem.getElementsByTagName("critter")));
					
					FactoryFactory.getInstance().getTownFactory().add(town);
					townList.add(town);
				}
			}
		}
		return townList;
	}
	
	private List<Person> parsePersonData(NodeList people, boolean inTown) throws XMLParserException {
	    List<Person> result = new ArrayList<Person>();
		if (people == null) {
			return result;
		}
		
		for (int i = 0; i < people.getLength(); i++) {
		    Node node = people.item(i);
		    if (Node.ELEMENT_NODE != node.getNodeType()) {
		        continue;
		    }
			Element elem = (Element) node;
			if (elem.getTagName() != XMLParserConstants.PARSER_TAG_PERSON) {
			    throw new XMLParserException("Non person tage in person section! tag: " + elem.getTagName());
			}
			Person person = null;
			if (elem.hasAttribute("id")) {
                person = getPerson(elem.getAttribute("id"));
            } else {
                person = new Person();
            }
            
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
			
			person.setQuests(parseQuestData(person, elem.getElementsByTagName(XMLParserConstants.PARSER_TAG_QUEST)));
			
			for (Node convNode : getChildrenWithTag("conversation", node)) {
			    if (convNode.getNodeType() == Node.ELEMENT_NODE) {
			        person.addConversation(parseConversation((Element) convNode));
			    }
			}
			// TODO: parse items
			
			personMap.put(person.getId(), person);
			if (!inTown) {
			    FactoryFactory.getInstance().getPersonFactory().add(person);
			}
			result.add(person);
		}
		return result;
	}
	
	private List<Quest> parseQuestData(Person requestor, NodeList questData) throws XMLParserException {
	    List<Quest> result = new ArrayList<Quest>();
		if (questData == null) {
			return result;
		}
		for (int i = 0; i < questData.getLength(); i++) {
			Node node = questData.item(i);
			
			Element elem = (Element) node;
			
			Node exNode = extractFirstNode(XMLParserConstants.PARSER_TAG_EXPLINATION, node);
			Quest quest = null;
			if (elem.hasAttribute("id")) {
                quest = getQuest(elem.getAttribute("id"));
            } else {
                quest = new Quest();
                questMap.put(quest.getId(), quest);
            }
			quest.setName(elem.getAttribute("name"));
             
			quest.setRequestor(requestor);
			// Explanation
			quest.setExplination(parseConversation((Element) extractFirstNode(XMLParserConstants.PARSER_TAG_CONVERSATION, exNode)));
			
			// Information
			Element infoElem = (Element) extractFirstNode(XMLParserConstants.PARSER_TAG_INFORMATION, node);
			if (infoElem != null) {
			    NodeList infoNodes = infoElem.getElementsByTagName(XMLParserConstants.PARSER_TAG_CONVERSATION);
			    List<Conversation> infos = new ArrayList<Conversation>();
			    for(int j = 0; j < infoNodes.getLength(); j++) {
			        infos.add(parseConversation((Element) infoNodes.item(j)));
			    }
			    quest.setInformation(infos);
			}
			
			// Solved conversations
			Element solElem = (Element) extractFirstNode(XMLParserConstants.PARSER_TAG_SOLVED, node);
			quest.getSolveds().add(parseConversation((Element) extractFirstNode(XMLParserConstants.PARSER_TAG_CONVERSATION, solElem)));
			
			// Items
			quest.setItems(parseItems(elem.getElementsByTagName("item")));
			
			// Critters
			quest.setCritters(parseCritters(elem.getElementsByTagName("critter")));
			
			if (requestor == null) {
			    FactoryFactory.getInstance().getQuestFactory().add(quest);
			}
			result.add(quest);
		}
		return result;
	}
	
	protected Conversation parseConversation(Element xml) throws XMLParserException {
	    dialogMap.clear();
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
		conversationMap.put(conv.getId(), conv);
		
		return conv;
	}
	
	protected void parseSubNodes(Node xml, DialogNode parent) throws XMLParserException {
		Element elem = (Element) xml;
		
		DialogNode leaf = new DialogNode();
		Requirement req = null;
		
		for (StatEnum stat : EnumSet.allOf(StatEnum.class)) {
		    String abrev = stat.getDescription().toLowerCase();
		    if (elem.hasAttribute(abrev)) {
		        req = new StatRequirement(stat, Integer.parseInt(elem.getAttribute(abrev)));
		    }
		}
		if (elem.hasAttribute("item")) {
			req = new ItemRequirement(getItem(elem.getAttribute("item")));
		}
		
		if(elem.hasAttribute("ref")) {
		    // Node is a refference to another node.
		    // Create a copy of the vector to give to the new parent
		    DialogVector example = dialogMap.get(elem.getAttribute("ref"));
		    if (example == null) {
		        throw new XMLParserException("Node with ref id " + elem.getAttribute("ref") + " reference before definition.");
		    }
		    DialogVector copy = new DialogVector();
		    copy.setChild(example.getChild());
		    copy.setPlayerDialog(example.getPlayerDialog());
		    copy.setRequirement(req);
		    parent.addVector(copy);
		} else {
		    Dialog dialog = new Dialog();
		    dialog.setText(extractTextFromNode(XMLParserConstants.PARSER_TAG_DIALOG, xml));
		    leaf.setDialog(dialog);
		    Dialog player = new Dialog();
		    String playerText = extractTextFromNode(XMLParserConstants.PARSER_TAG_PLAYER, xml);
		    if (playerText == null) {
		        throw new XMLParserException("No player tag for node! " + parent.toString());
		    }
	        player.setText(playerText);
	        
	        parseAction(EventEnum.SPEAK_DIALOG, 
					extractFirstNode(XMLParserConstants.PARSER_TAG_ACTION, xml),
					dialog);
	        
	        DialogVector vector = parent.addNode(player, leaf);
	        vector.setRequirement(req);
	        if (elem.hasAttribute("limit")) {
	            vector.setUseLimit(Integer.parseInt(elem.getAttribute("limit")));
	        }
	        if (elem.hasAttribute("id") && !dialogMap.containsKey(elem.getAttribute("id"))) {
	            dialogMap.put(elem.getAttribute("id"), vector);
	        }
		}
		
		List<Node> nodeChildren = getChildrenWithTag(XMLParserConstants.PARSER_TAG_NODE, elem); 
		for(Node child : nodeChildren) {
		    parseSubNodes(child, leaf);
		}
	}
	
	private List<Item> parseItems(NodeList nodes) {
	    List<Item> results = new ArrayList<Item>();
	    if (nodes == null) {
	        return results;
	    }
	    for (int i = 0; i < nodes.getLength(); i++) {
	        Element elem = (Element) nodes.item(i);
	        Item item = null;
	        if (elem.hasAttribute(XMLParserConstants.PARSER_ATTRIBUTE_ID)) {
	            item = getItem(elem.getAttribute(XMLParserConstants.PARSER_ATTRIBUTE_ID));
	        } else {
	            item = new Item();
	        }
	        item.setName(elem.getAttribute(XMLParserConstants.PARSER_ATTRIBUTE_NAME));
	        item.setDescrption(extractTextFromNode(XMLParserConstants.PARSER_ATTRIBUTE_DESCRIPTION, elem));
	        Element displayElem = (Element) extractFirstNode("display", elem);
	        item.setImgUrl(displayElem.getAttribute("url"));
	        FactoryFactory.getInstance().getItemFactory().add(item);
	        results.add(item);
	    }
	    return results;
	}
	
	private List<Critter> parseCritters(NodeList nodes) {
	    List<Critter> results = new ArrayList<Critter>();
	    if (nodes == null) {
	        return results;
	    }
	    for (int i = 0; i < nodes.getLength(); i++) {
	        Element elem = (Element) nodes.item(i);
	        Critter critter = null;
	        if (elem.hasAttribute(XMLParserConstants.PARSER_ATTRIBUTE_ID)) {
	            critter = getCritter(elem.getAttribute(XMLParserConstants.PARSER_ATTRIBUTE_ID));
	        } else {
	            critter = new Critter();
	            this.critterMap.put(critter.getId(), critter);
	        }
	        critter.setName(elem.getAttribute(XMLParserConstants.PARSER_ATTRIBUTE_NAME));
	        critter.setHerdSize(Integer.parseInt(elem.getAttribute("herd")));
	        critter.setImgPath(elem.getAttribute("urlPath"));
	        
	        Element statElem = (Element) extractFirstNode("stats", elem);
	        Map<StatEnum, Integer> stats = new HashMap<StatEnum, Integer>();
	        for (StatEnum stat : EnumSet.allOf(StatEnum.class)) {
	            if (statElem.hasAttribute(stat.getDescription().toLowerCase())) {
	                stats.put(stat, Integer.parseInt(statElem.getAttribute(stat.getDescription().toLowerCase())));
	            }
	        }
	        critter.setBaseStats(stats);
	        FactoryFactory.getInstance().getCritterFactory().add(critter);
	        results.add(critter);
	    }
	    return results;
	}
	
	private List<Node> getChildrenWithTag(String tag, Node node) {
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
	
	private void parseAction(EventEnum eventType, Node xml, GameObject actionObject) {
		if (xml != null) {
			Element elem = (Element) xml;
			if (elem.hasAttribute(XMLParserConstants.PARSER_ATTRIBUTE_TOPIC)) {
				Topic topic = getTopic(elem.getAttribute(XMLParserConstants.PARSER_ATTRIBUTE_TOPIC));
				ActivateTopicAction action = new ActivateTopicAction();
				action.setTopic(topic);
				this.eventHandler.mapEventToAction(eventType, actionObject, action);
			} else if (elem.hasAttribute("quest")) {
			    Quest quest = getQuest(elem.getAttribute("quest"));
			    CompleteQuestAction action = new CompleteQuestAction();
			    action.setQuest(quest);
			    this.eventHandler.mapEventToAction(eventType, actionObject, action);
			} else if (elem.hasAttribute("item")) {
			    Item item = getItem(elem.getAttribute("item"));
			    GiveItemToPlayerAction action = new GiveItemToPlayerAction();
			    action.setItem(item);
			    this.eventHandler.mapEventToAction(eventType, actionObject, action);
			}
		}
	}
	
	private Critter getCritter(String id) {
	    if (!critterMap.containsKey(id)) {
	        Critter critter = new Critter();
	        critter.setId(id);
	        critterMap.put(id, critter);
	    }
	    return critterMap.get(id);
	}
	
	private Item getItem(String id) {
	    if (!itemMap.containsKey(id)) {
	        Item item = new Item();
	        item.setId(id);
	        itemMap.put(id, item);
	    }
	    return itemMap.get(id);
	}
	
	private Quest getQuest(String id) {
	    if (!questMap.containsKey(id)) {
	        Quest quest = new Quest();
	        quest.setId(id);
	        questMap.put(id, quest);
	    }
	    return questMap.get(id);
	}
	
	private Person getPerson(String id) {
	    if (!personMap.containsKey(id)) {
	        Person person = new Person(id);
	        personMap.put(id, person);
	    }
	    return personMap.get(id);
	}
	
	private Topic getTopic(String id) {
		if (!topicMap.containsKey(id)) {
		    Topic topic = new Topic();
	        topic.setId(id);
	        topicMap.put(id, topic);
		}
		return topicMap.get(id);
	}

	public Map<String, Topic> getTopicMap() {
		return topicMap;
	}

	public Map<String, Person> getPersonMap() {
		return personMap;
	}

	public Map<String, Quest> getQuestMap() {
		return questMap;
	}

	public Map<String, Town> getTownMap() {
		return townMap;
	}

	public Map<String, Item> getItemMap() {
		return itemMap;
	}

	public Map<String, Critter> getCritterMap() {
		return critterMap;
	}
	
	public Map<String, Conversation> getConversationMap() {
		return conversationMap;
	}
}
