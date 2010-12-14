package com.modiopera.aventura.model;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class GameObjectTest {

	@Test
	public void testGetId() {
		GameObject obj = new GameObject();
		obj.setId("id");
		Assert.assertEquals("id", obj.getId());
		obj = new GameObject();
		Assert.assertEquals("GameObject_1", obj.getId());
	}

	@Test
	public void testGetName() {
		GameObject obj = new GameObject();
		obj.setName("name");
		Assert.assertEquals("name", obj.getName());
	}

	@Test
	public void testGetDescrption() {
		GameObject obj = new GameObject();
		obj.setDescrption("description");
		Assert.assertEquals("description", obj.getDescrption());
	}
}
