package com.modiopera.aventura.controller;

import java.util.Map;

import com.modiopera.aventura.model.Critter;
import com.modiopera.aventura.model.GameObject;

public class CritterController extends BaseController {

	private Critter currentCritter;
	private Map<String, Critter> critterMap;
	
	@Override
	protected GameObject getCurrentObject() {
		return currentCritter;
	}
	
	public void setCritterMap(Map<String, Critter> map) {
		this.critterMap = map;
	}
	
	public Critter getCritter(String id) {
		return this.critterMap.get(id);
	}
	
	public void killedCritter(Critter critter) {
		fireEvent(EventEnum.KILL_CRITTER);
	}

	@Override
	public Class<? extends GameObject> getChildType() {
		return Critter.class;
	}

}
