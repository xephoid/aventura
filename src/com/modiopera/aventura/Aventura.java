package com.modiopera.aventura;

import java.util.ArrayList;
import java.util.List;

import com.modiopera.aventura.model.Person;
import com.modiopera.aventura.model.Town;
import com.modiopera.aventura.model.conversation.Dialog;
import com.modiopera.aventura.parser.xml.XMLGameParser;
import com.modiopera.aventura.view.text.TextBasedView;

public class Aventura {

	private List<Town> towns = new ArrayList<Town>();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Aventura game = new Aventura();
		game.start();
	}

	/**
	 * TODO:
	 */
	public void start() {
		TextBasedView view = new TextBasedView();
		
		XMLGameParser.parseAndRun("towns/test.xml");
		
	}
	
	public void addTown(Town town) {
		this.towns.add(town);
	}
	
	private void old() {
		Town town = new Town();
		town.setName("Townville");
		town.setDescrption("Townville is a pretty average town.  It has some people, farms, a town hall.  You know, town stuff.");
		
		List<Person> people = new ArrayList<Person>();
		Person mcDude = new Person();
		mcDude.setName("Guy McDude");
		
		Dialog dialog = new Dialog();
		dialog.setText("Howdy there");
		mcDude.setInitialGreeting(dialog);
		dialog = new Dialog();
		dialog.setText("Howdy again");
		mcDude.setGreeting(dialog);
		dialog = new Dialog();
		dialog.setText("My name is Guy.  Guy McDude, I live with my wife, Woman, in our farm near the outside of town.");
		mcDude.setIntroduction(dialog);
		
		people.add(mcDude);
		
		town.setTownsPeople(people);
	}
}
