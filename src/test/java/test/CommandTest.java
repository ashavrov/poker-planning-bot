package test;

import commands.CommandHandler;
import commands.MessageCommandIn;
import commands.MessageCommandOut;
import dao.MeetingDAO;
import dao.UserDAO;
import entities.Meeting;
import entities.User;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class CommandTest {
	private final CommandHandler handler = new CommandHandler();

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
		MessageCommandIn message = new MessageCommandIn("/createMeeting \"TestFromUnitTest\" \"2019-01-01\" \"12:00\"",
				174913664, (long) 1234, "Test", null);
		String text = handler.execute(message).get(0).getMessage().getText();
		Assert.assertEquals("Встреча создана.", text);
		message = new MessageCommandIn("/createMeeting \"TestFromUnitTest\" \"2019-01 01\" \"45:00\"", 174913664,
				(long) 1234, "Test", null);
		text = handler.execute(message).get(0).getMessage().getText();
		Assert.assertEquals("Ошибка при создании встречи.", text);
		message = new MessageCommandIn("/createMeeting \"TestFromUnitTest\" \"2019-01 01\"", 174913664, (long) 1234,
				"Test", null);
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
		MessageCommandIn message = new MessageCommandIn("/constructorCommand /createMeeting", 1234, (long) 1234, "Test",
				null);
		MessageCommandIn messageInAnswer = new MessageCommandIn("answer", 2123, (long) 123, "Test", null);
		MessageCommandOut messageOut = handler.execute(message).get(0);
		Assert.assertEquals("Введите название:", messageOut.getMessage().getText());
		while (messageOut.getQuestionAnswerHandler().isQuestionExists()) {
			messageOut.getQuestionAnswerHandler().getNewQuestion(messageInAnswer);
			messageOut.getQuestionAnswerHandler().addAnswer("answer");
		}
		Assert.assertEquals("/createMeeting \"answer\" \"answer\" \"answer\"",
				messageOut.getQuestionAnswerHandler().getFullCommand());
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

	@Test
	public void testCommandAddUser() {
		UserDAO userDAO = new UserDAO();
		MeetingDAO meetingDAO = new MeetingDAO();
		Meeting meeting = meetingDAO.getByName("TestFromUnitTest");
		User user = userDAO.getById("1234");

		MessageCommandIn messageIn1 = new MessageCommandIn("/addUser", 1234, (long) 1234, "Test", null);
		MessageCommandOut messageOut1 = handler.execute(messageIn1).get(0);
		Assert.assertEquals("Ошибка при добавлении участника. Неверный формат команды.",
				messageOut1.getMessage().getText());

		MessageCommandIn messageIn2 = new MessageCommandIn("/addUser \"123\" \"123\"", 1234, (long) 1234, "Test", null);
		MessageCommandOut messageOut2 = handler.execute(messageIn2).get(0);
		Assert.assertEquals("Некорректно заданая встреча.", messageOut2.getMessage().getText());

		MessageCommandIn messageIn3 = new MessageCommandIn("/addUser \"123\" \"" + meeting.getMeetingId() + "\"", 1234,
				(long) 1234, "Test", null);
		MessageCommandOut messageOut3 = handler.execute(messageIn3).get(0);
		Assert.assertEquals("Некорректно задан пользователь.", messageOut3.getMessage().getText());

		MessageCommandIn messageIn4 = new MessageCommandIn(
				"/addUser \"" + user.getUserId() + "\" \"" + meeting.getMeetingId() + "\"", 1234, (long) 1234, "Test",
				null);
		List<MessageCommandOut> messageOut4 = handler.execute(messageIn4);
		Assert.assertEquals("Вы добавленны во встречу \"" + meeting.getName() + "\"",
				messageOut4.get(0).getMessage().getText());
		Assert.assertEquals("Пользователь добавлен.", messageOut4.get(1).getMessage().getText());
	}

	@Test
	public void testCommandGetMeetings() {
		MessageCommandIn messageIn1 = new MessageCommandIn("/getMeetings", 1234, (long) 1234, "Test", null);
		MessageCommandOut messageOut1 = handler.execute(messageIn1).get(0);
		Assert.assertEquals("Детализация:", messageOut1.getMessage().getText());
	}

	@Test
	public void testCommandGetMeetingCommands() {
		MessageCommandIn message = new MessageCommandIn("/createMeeting \"testCommandGetMeetingCommands\" \"2019-01-01\" \"12:00\"",
				174913664, (long) 1234, "Test", null);
		String text = handler.execute(message).get(0).getMessage().getText();
		Assert.assertEquals("Встреча создана.", text);
		MeetingDAO meetingDAO = new MeetingDAO();
		Meeting meeting = meetingDAO.getByName("testCommandGetMeetingCommands");
		MessageCommandIn messageIn1 = new MessageCommandIn("/getMeetingCommands \"" + meeting.getMeetingId() + "\"", 1234, (long) 1234, "Test", null);
		MessageCommandOut messageOut1 = handler.execute(messageIn1).get(0);
		Assert.assertEquals("Создать игру:", messageOut1.getMessage().getText());
		Assert.assertNotNull(messageOut1.getMessage().getReplyMarkup());
		MessageCommandIn messageIn2 = new MessageCommandIn("/getMeetingCommands", 1234, (long) 1234, "Test", null);
		MessageCommandOut messageOut2 = handler.execute(messageIn2).get(0);
		Assert.assertEquals("Неверный формат команды.", messageOut2.getMessage().getText());
		Assert.assertNotNull(messageOut2.getMessage().getReplyMarkup());
		MessageCommandIn messageIn3 = new MessageCommandIn("/getMeetingCommands \"123\"", 1234, (long) 1234, "Test", null);
		MessageCommandOut messageOut3 = handler.execute(messageIn3).get(0);
		Assert.assertEquals("Некорректная встреча.", messageOut3.getMessage().getText());
	}

	@AfterClass
	public static void deleteTestData() {
		UserDAO userDAO = new UserDAO();
		User user = userDAO.getById("1234");
		Assert.assertEquals(user, userDAO.getById("1234"));
		userDAO.delete(user);
		Assert.assertNull(userDAO.getById("1234"));

		MeetingDAO meetingDAO = new MeetingDAO();
		Meeting meeting = meetingDAO.getByName("TestFromUnitTest");
		Assert.assertEquals(meeting, meetingDAO.getByName("TestFromUnitTest"));
		meetingDAO.delete(meeting);
		meeting = meetingDAO.getByName("testCommandGetMeetingCommands");
		meetingDAO.delete(meeting);
		Assert.assertNull(meetingDAO.getByName("TestFromUnitTest"));
		Assert.assertNull(meetingDAO.getByName("testCommandGetMeetingCommands"));
	}

}
