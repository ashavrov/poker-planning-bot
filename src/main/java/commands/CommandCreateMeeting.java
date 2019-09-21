package commands;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.MeetingDAO;
import dao.UserDAO;
import entities.Meeting;
import entities.User;

public class CommandCreateMeeting implements Command {
	private static Logger log = LogManager.getLogger(CommandCreateMeeting.class);

	@Override
	public List<MessageCommandOut> execute(MessageCommandIn message) {
		ArrayList<MessageCommandOut> listMessagesOut = new ArrayList<>();
		MeetingDAO meetingDAO = new MeetingDAO();
		UserDAO userDAO = new UserDAO();

		Pattern patternCommand = Pattern.compile("^(\\/.*?)(\\s)(\\\".*\\\")(\\s)(\".*\")(\\s)(\".*\")$");
		Matcher macherCommand = patternCommand.matcher(message.getMessage());

		if (macherCommand.find()) {
			try {
				String meetingName = macherCommand.group(3).replace("\"", "");
				String stringDate = macherCommand.group(5).replace("\"", "");
				String stringTime = macherCommand.group(7).replace("\"", "");
				Meeting meeting = new Meeting(
						new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(stringDate + " " + stringTime), meetingName);
				meetingDAO.insert(meeting);
				meetingDAO.addUser(userDAO.getById(message.getUserId().toString()), meeting);
				listMessagesOut
						.add(new MessageCommandOut(message, message.getDeleteMessageId()).setText("Встреча создана."));
				for (User user : userDAO.getAll()) {
					if (!user.getUserId().equals(message.getUserId().toString())) {
						listMessagesOut
								.add(new MessageCommandOut(message, null).setText("Добавить участника:").addButton(
										user.getName(), "/addUser " + user.getName() + " " + meeting.getMeetingId()));
					}
				}
			} catch (Exception e) {
				log.catching(e);
				listMessagesOut.add(new MessageCommandOut(message, message.getDeleteMessageId())
						.setText("Ошибка при создании встречи."));

			}
		} else {
			listMessagesOut.add(new MessageCommandOut(message, message.getDeleteMessageId())
					.setText("Ошибка при создании встречи. Неверный формат команды."));
		}
		return listMessagesOut;
	}
}
