package com.modiopera.aventura.model.conversation;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class DialogTest {

	@Test
	public void testGetText() {
		Dialog dialog = new Dialog();
		dialog.setText("text");
		Assert.assertEquals("text", dialog.getText());
	}

	@Test
	public void testGetId() {
		Dialog dialog = new Dialog();
		Assert.assertEquals("Dialog_1", dialog.getId());
	}

}
