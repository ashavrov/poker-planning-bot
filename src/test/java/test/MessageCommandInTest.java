package test;

import org.junit.Assert;
import org.junit.Test;

import commands.MessageCommandIn;

public class MessageCommandInTest {

	@Test
	public void test() {
		MessageCommandIn messageIn = new MessageCommandIn("/command 123", 12345, (long) 1234, "Test", 9871);
		Assert.assertTrue(messageIn.getCommand().equals("/command"));
		Assert.assertTrue(messageIn.getMessage().equals("/command 123"));
		Assert.assertTrue(messageIn.getUserName().equals("Test"));
		Assert.assertTrue(messageIn.getChatId().equals((long) 1234));
		Assert.assertTrue(messageIn.getUserId().equals(12345));
		Assert.assertTrue(messageIn.getDeleteMessageId().equals(9871));
		messageIn.setChatId((long) 23456);
		messageIn.setMessage("/command1 1234");
		messageIn.setUserId(23456);
		messageIn.setUserName("Test4");
		Assert.assertTrue(messageIn.getCommand().equals("/command1"));
		Assert.assertTrue(messageIn.getMessage().equals("/command1 1234"));
		Assert.assertTrue(messageIn.getUserName().equals("Test4"));
		Assert.assertTrue(messageIn.getChatId().equals((long) 23456));
		Assert.assertTrue(messageIn.getUserId().equals(23456));
	}

}
