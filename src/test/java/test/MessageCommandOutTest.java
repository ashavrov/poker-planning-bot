package test;

import org.junit.Assert;
import org.junit.Test;

import commands.MessageCommandIn;
import commands.MessageCommandOut;
import entities.User;

public class MessageCommandOutTest {

	@Test
	public void test() {
		MessageCommandIn messageIn1 = new MessageCommandIn("/command", 1234, (long) 1234, "Test", null);
		MessageCommandOut messageOut1 = new MessageCommandOut(messageIn1, null);
		messageOut1.setText("Test Text 0");
		Assert.assertEquals("1234", messageOut1.getMessage().getChatId());
		Assert.assertEquals("Test Text 0", messageOut1.getMessage().getText());
		Assert.assertNull(messageOut1.getMessageDelete());

		User user1 = new User("123123", "23423423", "TestUser");
		MessageCommandOut messageOut2 = new MessageCommandOut(user1);
		messageOut2.setText("Test Text 1");
		Assert.assertEquals("23423423", messageOut2.getMessage().getChatId());
		Assert.assertEquals("Test Text 1", messageOut2.getMessage().getText());
		Assert.assertNull(messageOut2.getMessageDelete());

		MessageCommandIn messageIn3 = new MessageCommandIn("/command", 1234, (long) 1234, "Test", null);
		MessageCommandOut messageOut3 = new MessageCommandOut(messageIn3, 789);
		messageOut3.setText("Test Text 2");
		Assert.assertEquals("1234", messageOut3.getMessage().getChatId());
		Assert.assertEquals("Test Text 2", messageOut3.getMessage().getText());
		Assert.assertEquals("1234", messageOut3.getMessageDelete().getChatId());

		MessageCommandIn messageIn4 = new MessageCommandIn("/constructorCommand /command", 1234, (long) 1234, "Test", null);
		MessageCommandOut messageOut4 = new MessageCommandOut(messageIn4, 789);
		messageOut4.setText("Test Text 3");
		messageOut4.addQuestion("Why?");
		messageOut4.addAnswer("Mmm");
		Assert.assertEquals("/command \"Mmm\"", messageOut4.getCommandText());
		Assert.assertEquals("1234", messageOut4.getMessage().getChatId());
		Assert.assertEquals("Test Text 3", messageOut4.getMessage().getText());
		Assert.assertEquals("1234", messageOut4.getMessageDelete().getChatId());
	}

}
