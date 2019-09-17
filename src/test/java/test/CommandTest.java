package test;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import commands.CommandHandler;
import commands.MessageCommandIn;
import commands.MessageCommandOut;
import dao.MeetingDAO;
import dao.UserDAO;
import entities.Meeting;
import entities.User;

public class CommandTest {
	private CommandHandler handler = new CommandHandler();

	@Test
	public void testCommandStart() {
		MessageCommandIn message = new MessageCommandIn("/start", 1234, (long) 1234, "Test", null);
		String text = handler.execute(message).get(0).getMessage().getText();
		Assert.assertEquals("Привет, Test!", text);
		text = handler.execute(message).get(0).getMessage().getText();
		Assert.assertEquals("Привет, Test!", text);
	}

	@Test
	public void testCommandCreateMeeting() {
		MessageCommandIn message = new MessageCommandIn("/createMeeting \"TestFromUnitTest\" \"2019-01-01\" \"12:00\"", 1234,
				(long) 1234, "Test", null);
		String text = handler.execute(message).get(0).getMessage().getText();
		Assert.assertEquals("Встреча создана.", text);
		message = new MessageCommandIn("/createMeeting \"TestFromUnitTest\" \"2019-01 01\" \"45:00\"", 1234, (long) 1234, "Test", null);
		text = handler.execute(message).get(0).getMessage().getText();
		Assert.assertEquals("Ошибка при создании встречи.", text);
		message = new MessageCommandIn("/createMeeting \"TestFromUnitTest\" \"2019-01 01\"", 1234, (long) 1234, "Test", null);
		text = handler.execute(message).get(0).getMessage().getText();
		Assert.assertEquals("Ошибка при создании встречи. Неверный формат команды.", text);
	}

	@Test
	public void testCommandUnknown() {
		MessageCommandIn message = new MessageCommandIn("/unknown", 345, (long) 345, "Тест", null);
		String text = handler.execute(message).get(0).getMessage().getText();
		Assert.assertEquals("Неизвестная команда.", text);
	}

	@Test
	public void testConstructorCommands() {
		MessageCommandIn message = new MessageCommandIn("/constructorCommand /createMeeting", 1234, (long) 1234, "Test", null);
		MessageCommandIn messageInAnswer = new MessageCommandIn("answer", 2123, (long) 123, "Test", null);
		MessageCommandOut messageOut = handler.execute(message).get(0);
		Assert.assertEquals("Введите название:", messageOut.getMessage().getText());
		while(messageOut.getQuestionAnswerHandler().isQuestionExists()) {
			messageOut.getQuestionAnswerHandler().getNewQuestion(messageInAnswer);
			messageOut.getQuestionAnswerHandler().addAnswer("answer");
		}
		Assert.assertEquals(messageOut.getQuestionAnswerHandler().getFullCommand(), "/createMeeting \"answer\" \"answer\" \"answer\"");
	}
	@Test
	public void testCommandShowMenu() {
		MessageCommandIn message = new MessageCommandIn("/showMenu", 1234, (long) 1234, "Test", null);
		MessageCommandOut messageOut = handler.execute(message).get(0);
		Assert.assertEquals("Меню:", messageOut.getMessage().getText());
		Assert.assertNotNull(messageOut.getMessage().getReplyMarkup());
		message = new MessageCommandIn("/constructorCommand /createMeeting", 1234, (long) 1234, "Test", null);
		messageOut = handler.execute(message).get(0);
		Assert.assertEquals("Введите название:", messageOut.getMessage().getText());
		message = new MessageCommandIn("qwe", 1234, (long) 1234, "Test", null);
		messageOut = handler.execute(message).get(0);
		Assert.assertEquals("Введите дату:", messageOut.getMessage().getText());
		message = new MessageCommandIn("2019-08-19", 1234, (long) 1234, "Test", null);
		messageOut = handler.execute(message).get(0);
		Assert.assertEquals("Введите время:", messageOut.getMessage().getText());
		message = new MessageCommandIn("12:00", 1234, (long) 1234, "Test", null);
		messageOut = handler.execute(message).get(0);
		Assert.assertEquals("Необходимо подтверждение:", messageOut.getMessage().getText());
	}

	@AfterClass
	public static void deleteTestData() {
		UserDAO userDAO = new UserDAO();
		User user = userDAO.getById("1234");
		Assert.assertTrue(user.equals(userDAO.getById("1234")));
		userDAO.delete(user);
		Assert.assertNull(userDAO.getById("1234"));

		MeetingDAO meetingDAO = new MeetingDAO();
		Meeting meeting = meetingDAO.getByName("TestFromUnitTest");
		Assert.assertTrue(meeting.equals(meetingDAO.getByName("TestFromUnitTest")));
		meetingDAO.delete(meeting);
		Assert.assertNull(meetingDAO.getByName("TestFromUnitTest"));
	}

}
