package test;

import org.junit.Assert;
import org.junit.Test;

import commands.MessageCommandIn;

public class MessageCommandInTest {

	@Test
	public void test() {
		MessageCommandIn messageIn = new MessageCommandIn("/command 123", 12345, (long) 1234, "Test", 9871);
		Assert.assertEquals("/command", messageIn.getCommand());
		Assert.assertEquals("/command 123", messageIn.getMessage());
		Assert.assertEquals("Test", messageIn.getUserName());
		Assert.assertEquals((long) messageIn.getChatId(), 1234);
		Assert.assertEquals(12345, (int) messageIn.getUserId());
		Assert.assertEquals(9871, (int) messageIn.getDeleteMessageId());
		messageIn.setChatId((long) 23456);
		messageIn.setMessage("/command1 1234");
		messageIn.setUserId(23456);
		messageIn.setUserName("Test4");
		Assert.assertEquals("/command1", messageIn.getCommand());
		Assert.assertEquals("/command1 1234", messageIn.getMessage());
		Assert.assertEquals("Test4", messageIn.getUserName());
		Assert.assertEquals((long) messageIn.getChatId(), 23456);
		Assert.assertEquals(23456, (int) messageIn.getUserId());
	}

}
