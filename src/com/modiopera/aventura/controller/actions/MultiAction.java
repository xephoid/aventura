package com.modiopera.aventura.controller.actions;

import java.util.ArrayList;
import java.util.List;

public class MultiAction extends Action {

	private List<Action> actions;
	
	@Override
	public boolean act() {
		for(Action action : this.actions) {
			action.act();
		}
		//TODO: return true only if one worked??
		return true;
	}
	
	public void addAction(Action action) {
		if (this.actions == null) {
			this.actions = new ArrayList<Action>();
		}
		this.actions.add(action);
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}
}
