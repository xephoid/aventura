package com.modiopera.aventura.controller;

import java.util.Map;

import com.modiopera.aventura.controller.event.EventEnum;
import com.modiopera.aventura.model.GameObject;
import com.modiopera.aventura.model.Person;

public class PeopleController extends BaseController {

	private Person currentPerson;
	private Map<String, Person> personMap;
	
	@Override
	protected GameObject getCurrentObject() {
		return this.currentPerson;
	}

	public void setPersonMap(Map<String, Person> personMap) {
		this.personMap = personMap;
	}
	
	public Person getPerson(String id) {
		return this.personMap.get(id);
	}
	
	public void speakToPerson(Person person) {
		this.currentPerson = person;
		fireEvent(EventEnum.SPEAK_TO_PERSON);
		view.showPerson(person);
		person.setMetBefore(true);
	}

	@Override
	public Class<? extends GameObject> getChildType() {
		return Person.class;
	}
}
