package com.modiopera.aventura.model.conversation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.modiopera.aventura.model.PlayerDataMap;

public class DialogNode {
	private Dialog dialog;
	private Map<String, DialogVector> vectors = new HashMap<String, DialogVector>();
	
	public void setDialog(Dialog dialog) {
		this.dialog = dialog;
	}
	
	public Dialog getDialog() {
		return this.dialog;
	}
	
	public boolean addVector(DialogVector vector) {
		if (vector != null && vector.getPlayerDialog() != null) {
			vector.setParent(this);
			this.vectors.put(vector.getPlayerDialog().getId(), vector);
			return true;
		}
		return false;
	}
	
	public DialogVector addNode(Dialog playerDialog, DialogNode node) {
	    DialogVector vector = new DialogVector();
	    vector.setParent(this);
	    vector.setPlayerDialog(playerDialog);
	    vector.setChild(node);
	    this.vectors.put(playerDialog.getId(), vector);
	    return vector;
	}
	
	public List<Dialog> getOptions(PlayerDataMap player) {
		if (this.isEnd()) {
			return null;
		}
		List<Dialog> result = new ArrayList<Dialog>();
		for(DialogVector vector: this.vectors.values()) {
			if (!vector.overLimit() && (vector.getRequirement() == null || vector.getRequirement().met(player))) {
				result.add(vector.getPlayerDialog());
			}
		}
		return result;
	}
	
	public DialogNode traverse(Dialog dialog) {
		return this.traverse(dialog.getId());
	}
	
	public DialogNode traverse(String id) {
		if (this.isEnd() || !this.vectors.containsKey(id)) {
			return null;
		}
		DialogVector vector = this.vectors.get(id);
		vector.use();
		return vector.getChild();
	}
	
	public boolean isEnd() {
		return (this.vectors == null || this.vectors.isEmpty());
	}
}