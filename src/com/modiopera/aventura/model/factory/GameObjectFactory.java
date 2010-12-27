package com.modiopera.aventura.model.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.modiopera.aventura.model.GameObject;

public class GameObjectFactory<GameObjectType extends GameObject> {
	private Map<String, GameObjectType> objects = new HashMap<String, GameObjectType>();
	
    @SuppressWarnings("unchecked")
	public GameObjectType getRandom() {
    	if (this.objects.values().size() < 1) {
    		return null;
    	}
		Object[] grabBag = this.objects.values().toArray();
		int index = (int) (Math.random() * grabBag.length);
		
		return (GameObjectType) grabBag[index];
	}
	
	public List<GameObjectType> getMultiple(int howMany) {
	    if (howMany > this.objects.size()) {
	        howMany = this.objects.size();
	    }
	    List<GameObjectType> result = new ArrayList<GameObjectType>();
	    if (howMany < 1) {
	        return result;
	    }
	    do {
	        GameObjectType obj = this.getRandom();
	        if (!result.contains(obj)) {
	            result.add(obj);
	        }
	    } while(result.size() != howMany);
	    return result;
	}
	
	public GameObjectType get(String id) {
		return this.objects.get(id);
	}
	
	public void add(GameObjectType object) {
		this.objects.put(object.getId(), object);
	}
	
	public void update() {
		Map<String, GameObjectType> other = new HashMap<String, GameObjectType>();
		for (String key : this.objects.keySet()) {
			GameObjectType obj = this.objects.get(key);
			other.put(obj.getId(), obj);
		}
		this.objects = other;
	}
}