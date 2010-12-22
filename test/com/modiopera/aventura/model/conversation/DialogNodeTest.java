package com.modiopera.aventura.model.conversation;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.modiopera.aventura.model.PlayerDataMap;
import com.modiopera.aventura.model.enums.PlayerTypeEnum;

public class DialogNodeTest {

	private static final Dialog TEST_DIALOG = new Dialog();
	private static final Dialog TEST_PLAYER_DIALOG = new Dialog();
	
	@Test
	public void testSetDialog() {
		DialogNode node = new DialogNode();
		node.setDialog(TEST_DIALOG);
		Assert.assertEquals(TEST_DIALOG, node.getDialog());
	}

	@Test
	public void testOptions() {
		DialogNode node = new DialogNode();
		//node.addOption(TEST_PLAYER_DIALOG, TEST_DIALOG);
		
		List<Dialog> options = node.getOptions(new PlayerDataMap(PlayerTypeEnum.CUSTOM));
		Assert.assertFalse(options.isEmpty());
		Assert.assertEquals(TEST_PLAYER_DIALOG, options.get(0));
	}

	@Test
	public void testTraverse() {
		DialogNode node = new DialogNode();
		//node.addOption(TEST_PLAYER_DIALOG, TEST_DIALOG);
		DialogNode next = node.traverse(TEST_PLAYER_DIALOG);
		Assert.assertEquals(TEST_DIALOG, next.getDialog());
	}

	@Test
	public void testIsEnd() {
		DialogNode node = new DialogNode();
		Assert.assertTrue(node.isEnd());
		//node.addOption(TEST_PLAYER_DIALOG, TEST_DIALOG);
		Assert.assertFalse(node.isEnd());
	}
}
