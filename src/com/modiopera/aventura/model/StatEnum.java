package com.modiopera.aventura.model;

public enum StatEnum {
	// Base
	STRENGTH ("STR"),
	RANGE ("RNG"),
	CAMOUFLAGE ("CAM"),
	CHARISMA ("CHA"),
	DEFENSE ("DEF"),
	
	// Calculated
	DAMAGE("DMG"),
	DAMAGE_RESISTANCE("DMG. RES"),
	MAX_HIT_POINTS ("HPMAX"),
	SIGHT("SGHT"),

	// Varying
	LEVEL("LVL"),
	HIT_POINTS("HP"),
	EXPERIENCE("XP"),
	EXPERIENCE_TO_NEXT_LEVEL("XP to next LVL"),
	
	// Modifiers
	STRENGTH_MOD("+STR"),
	RANGE_MOD("+RNG"),
	CAMOFLAGE_MOD("+CAM"),
	CHARISMA_MOD("+CHA"),
	DEFENSE_MOD("+DEF")
	;
	
	private String description;
	StatEnum(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
}
