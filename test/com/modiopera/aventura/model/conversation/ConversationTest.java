package com.modiopera.aventura.model.conversation;

import static org.junit.Assert.fail;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ConversationTest {

	private static final Dialog TEST_PLAYER_DIALOG = new Dialog();
	private static final Dialog TEST_PLAYER_DIALOG2 = new Dialog();
	private static final Dialog TEST_DIALOG1 = new Dialog();
	private static final Dialog TEST_DIALOG2 = new Dialog();
	private static final Dialog TEST_DIALOG3 = new Dialog();
	private static final Dialog TEST_DIALOG4 = new Dialog();
	private static final DialogNode TEST_ROOT = new DialogNode();
	
	private static final String TEST_DIALOG_TEXT1 = "Dialog 1";
	private static final String TEST_DIALOG_TEXT2 = "dialog the second";
	private static final String TEST_DIALOG_TEXT3 = "Third dialog";
	private static final String TEST_DIALOG_TEXT4 = "Other third dialog";
	private static final String TEST_PLAYER_DIALOG_TEXT = "player dialog";
	private static final String TEST_PLAYER_DIALOG_TEXT2 = "Second player dialog";
	
	@Before
	public void setUp() throws Exception {
		TEST_DIALOG1.setText(TEST_DIALOG_TEXT1);
		TEST_DIALOG2.setText(TEST_DIALOG_TEXT2);
		TEST_DIALOG3.setText(TEST_DIALOG_TEXT3);
		TEST_DIALOG4.setText(TEST_DIALOG_TEXT4);
		TEST_PLAYER_DIALOG.setText(TEST_PLAYER_DIALOG_TEXT);
		TEST_PLAYER_DIALOG2.setText(TEST_PLAYER_DIALOG_TEXT2);
		TEST_ROOT.setDialog(TEST_DIALOG1);
		//TEST_ROOT.addOption(TEST_PLAYER_DIALOG, TEST_DIALOG2);
		//TEST_ROOT.addOption(TEST_PLAYER_DIALOG2, TEST_DIALOG3);
	}

	@Test
	public void testInit() {
		Conversation conversation = new Conversation();
		conversation.setRoot(TEST_ROOT);
		try {
			conversation.init();
		} catch (ConversationException e) {
			fail("Conversation exception thrown when not needed");
		}
		
		Conversation conv = new Conversation();
		try {
			conv.init();
		} catch (ConversationException e) {
			// success
			return;
		}
		fail("Exception not thrown on bad init");
	}

	@Test
	public void testGetOptions() throws ConversationException {
		Conversation conv = new Conversation();
		conv.setRoot(TEST_ROOT);
		conv.init();
		List<Dialog> options = conv.getOptions();
		Assert.assertEquals(TEST_PLAYER_DIALOG, options.get(1));
		Assert.assertEquals(TEST_PLAYER_DIALOG2, options.get(0));
	}

	@Test
	public void testChooseOption() throws ConversationException {
		Conversation conv = new Conversation();
		conv.setRoot(TEST_ROOT);
		conv.init();
		conv.chooseOption(TEST_PLAYER_DIALOG);
		Assert.assertEquals(TEST_DIALOG2, conv.getCurrentDialog());
		conv.init();
		conv.chooseOption(TEST_PLAYER_DIALOG2);
		Assert.assertEquals(TEST_DIALOG3, conv.getCurrentDialog());
	}

	@Test
	public void testGetCurrentDialog() throws ConversationException {
		Conversation conv = new Conversation();
		conv.setRoot(TEST_ROOT);
		conv.init();
		Assert.assertEquals(TEST_ROOT.getDialog(), conv.getCurrentDialog());
	}

	@Test
	public void testSetRoot() {
		Conversation conv = new Conversation();
		conv.setRoot(TEST_ROOT);
		Assert.assertEquals(TEST_ROOT, conv.getRoot());
	}
}
