package com.modiopera.aventura.controller;

import java.util.Map;

import com.modiopera.aventura.controller.event.EventEnum;
import com.modiopera.aventura.model.Critter;
import com.modiopera.aventura.model.GameObject;
import com.modiopera.aventura.model.enums.StatEnum;

public class CritterController extends BaseController {

	private Critter currentCritter;
	private Map<String, Critter> critterMap;
	
	@Override
	protected GameObject getCurrentObject() {
		return currentCritter;
	}
	
	public void setCritterMap(Map<String, Critter> map) {
		critterMap = map;
	}
	
	public Critter getCritter(String id) {
		return critterMap.get(id);
	}
	
	public void killedCritter(Critter critter) {
		currentCritter = critter;
		fireEvent(EventEnum.KILL_CRITTER);
		int xp = critter.getBaseStats().get(StatEnum.INTELLIGENCE) * playerData.getValue(StatEnum.INTELLIGENCE);
		playerData.increaseValue(StatEnum.EXPERIENCE, xp);
	}

	@Override
	public Class<? extends GameObject> getChildType() {
		return Critter.class;
	}
}
