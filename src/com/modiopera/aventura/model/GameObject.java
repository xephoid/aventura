package com.modiopera.aventura.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GameObject implements Serializable {
	
	private static final long serialVersionUID = 834215476489578381L;
	
	public static final int FIRST_ID = 0;
	public static final Map<Class<? extends GameObject>, Integer> _objectMap = new HashMap<Class<? extends GameObject>, Integer>();
	
	private String id;
	private String name;
	private String descrption;
	
	public GameObject() {
		this.id = this.getClass().getName() + "_" + nextId(this);
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescrption() {
		return descrption;
	}
	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameObject other = (GameObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public static int nextId(GameObject gameObject) {
		Class<? extends GameObject> objectType = gameObject.getClass();
		if (!_objectMap.containsKey(objectType)) {
			_objectMap.put(objectType, FIRST_ID);
		}
		int id = _objectMap.get(objectType);
		_objectMap.put(objectType, id + 1);
		return id;
	}
	
	@Override
	public String toString() {
		return this.id + "::" + this.name;
	}
}