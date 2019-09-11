package test;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import commands.CommandHandler;
import dao.UserDAO;
import entities.MessageCommand;
import entities.User;

public class CommandTest {
	private CommandHandler handler = new CommandHandler();

	@Test
	public void testCommandStart() {
		MessageCommand message = new MessageCommand("/start", 1234, (long) 1234, "Test");
		String text = handler.execute(message);
		Assert.assertEquals("Привет, Test!", text);
		text = handler.execute(message);
		Assert.assertEquals("Привет, Test!", text);
	}
	
	@Test
	public void testCommandСreateMeeting() {
		MessageCommand message = new MessageCommand("/createMeeting TestFromUnitTest 2019-01-01_12:00:00.0", 1234, (long) 1234, "Test");
		String text = handler.execute(message);
		Assert.assertEquals("Встреча создана.", text);
		message = null;
		message = new MessageCommand("/createMeeting TestFromUnitTest 2019-01-01", 1234, (long) 1234, "Test");
		text = handler.execute(message);
		Assert.assertEquals("Ошибка при создании встречи.", text);
	}

	@Test
	public void testCommandUnknown() {
		MessageCommand message = new MessageCommand("/unknown", 345, (long) 345, "Тест");
		String text = handler.execute(message);
		Assert.assertEquals("Неизвестная команда.", text);
	}

	@AfterClass
	public static void deleteTestData() {
		UserDAO userDAO = new UserDAO();
		User user = userDAO.getById("1234");
		Assert.assertTrue(user.equals(userDAO.getById("1234")));
		userDAO.delete(user);
	}

}
