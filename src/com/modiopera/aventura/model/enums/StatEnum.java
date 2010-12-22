package com.modiopera.aventura.model.enums;

public enum StatEnum {
	// Base
	STRENGTH ("STR", false),
	INTELLIGENCE("INT", false),
	RANGE ("RNG", false),
	CAMOUFLAGE ("CAM", false),
	CHARISMA ("CHA", false),
	FORTITUDE ("FRT", false),
	SPEED("SPD", false),
	
	// Calculated
	DAMAGE("DMG", true),
	DAMAGE_RESISTANCE("DMG. RES", true),
	MAX_HIT_POINTS ("HPMAX", true),
	SIGHT("SGHT", true),

	// Varying
	LEVEL("LVL", false),
	HIT_POINTS("HP", false),
	EXPERIENCE("XP", false),
	EXPERIENCE_TO_NEXT_LEVEL("XP to next LVL", true),
	
	// Modifiers
	CRITICAL("CRT", false),
	STRENGTH_MOD("+STR", false),
	RANGE_MOD("+RNG", false),
	CAMOFLAGE_MOD("+CAM", false),
	CHARISMA_MOD("+CHA", false),
	FORTITUDE_MOD("+FRT", false),
	INTELLIGENCE_MOD("+INT", false)
	;
	
	private String description;
	private boolean isCalculated;
	
	StatEnum(String description, boolean calculated) {
		this.description = description;
		this.isCalculated = calculated;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public boolean isCalculated() {
		return this.isCalculated;
	}
}
