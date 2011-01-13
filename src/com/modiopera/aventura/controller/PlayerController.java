package com.modiopera.aventura.controller;

import com.modiopera.aventura.model.GameObject;

/**
 * All things player data related.
 * 
 * @author xephoid
 *
 */

public class PlayerController extends BaseController {
	private GameObject activeObject;

	public GameObject getCurrentObject() {
		return this.activeObject;
	}

	@Override
	public Class<? extends GameObject> getChildType() {
		return null;
	}
}