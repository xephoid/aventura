package com.modiopera.aventura.model.conversation;

import junit.framework.Assert;

import org.junit.Test;

public class DialogVectorTest {

	private static final Dialog PLAYER_DIALOG = new Dialog();
	
	@Test
	public void testGetPlayerDialog() {
		DialogVector vector = new DialogVector();
		vector.setPlayerDialog(PLAYER_DIALOG);
		Assert.assertEquals(PLAYER_DIALOG, vector.getPlayerDialog());
	}

	@Test
	public void testGetParent() {
		DialogNode node = new DialogNode();
		DialogVector vector = new DialogVector();
		vector.setParent(node);
		Assert.assertEquals(node, vector.getParent());
	}

	@Test
	public void testGetChild() {
		DialogNode node = new DialogNode();
		DialogVector vector = new DialogVector();
		vector.setChild(node);
		Assert.assertEquals(node, vector.getChild());
	}
}
