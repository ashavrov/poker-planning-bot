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
		Assert.assertTrue(messageOut1.getMessage().getChatId().equals("1234"));
		Assert.assertTrue(messageOut1.getMessage().getText().equals("Test Text 0"));
		Assert.assertTrue(messageOut1.getMessageDelete() == null);

		User user1 = new User("123123", "23423423", "TestUser");
		MessageCommandOut messageOut2 = new MessageCommandOut(user1);
		messageOut2.setText("Test Text 1");
		Assert.assertTrue(messageOut2.getMessage().getChatId().equals("23423423"));
		Assert.assertTrue(messageOut2.getMessage().getText().equals("Test Text 1"));
		Assert.assertTrue(messageOut2.getMessageDelete() == null);

		MessageCommandIn messageIn3 = new MessageCommandIn("/command", 1234, (long) 1234, "Test", null);
		MessageCommandOut messageOut3 = new MessageCommandOut(messageIn3, 789);
		messageOut3.setText("Test Text 2");
		Assert.assertTrue(messageOut3.getMessage().getChatId().equals("1234"));
		Assert.assertTrue(messageOut3.getMessage().getText().equals("Test Text 2"));
		Assert.assertTrue(messageOut3.getMessageDelete().getChatId().equals("1234"));

		MessageCommandIn messageIn4 = new MessageCommandIn("/constructorCommand /command", 1234, (long) 1234, "Test", null);
		MessageCommandOut messageOut4 = new MessageCommandOut(messageIn4, 789);
		messageOut4.setText("Test Text 3");
		messageOut4.addQuestion("Why?");
		messageOut4.addAnswer("Mmm");
		Assert.assertTrue(messageOut4.getComandText().equals("/command \"Mmm\""));
		Assert.assertTrue(messageOut4.getMessage().getChatId().equals("1234"));
		Assert.assertTrue(messageOut4.getMessage().getText().equals("Test Text 3"));
		Assert.assertTrue(messageOut4.getMessageDelete().getChatId().equals("1234"));
	}

}
