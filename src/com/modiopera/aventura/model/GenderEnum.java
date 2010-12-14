package com.modiopera.aventura.model;

public enum GenderEnum {
	MALE("He", "His"),
	FEMALE("She", "Her"),
	NONE("It", "Its");
	
	private String pronoun;
	private String possesive;
	
	private GenderEnum(String proNoun, String possesion) {
		this.pronoun = proNoun;
		this.possesive = possesion;
	}
	
	public String getPronoun() {
		return this.pronoun;
	}
	
	public String getPosessiveForm() {
		return this.possesive;
	}
}
