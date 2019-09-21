package commands;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.MeetingDAO;
import dao.UserDAO;
import entities.Meeting;
import entities.User;

public class CommandAddUser implements Command {

	@Override
	public List<MessageCommandOut> execute(MessageCommandIn message) {
		ArrayList<MessageCommandOut> listMessagesOut = new ArrayList<>();
		MeetingDAO meetingDAO = new MeetingDAO();
		UserDAO userDAO = new UserDAO();

		Pattern patternCommand = Pattern.compile("^(\\/.*?)(\\s)(\\\".*\\\")(\\s)(\".*\")$");
		Matcher macherCommand = patternCommand.matcher(message.getMessage());
		if (macherCommand.find()) {
			String userId = macherCommand.group(3).replace("\"", "");
			String meetingId = macherCommand.group(5).replace("\"", "");

			if (meetingDAO.getById(meetingId) != null) {
				if (userDAO.getById(userId) != null) {
					User userAdd = userDAO.getById(userId);
					Meeting meetingToAdd = meetingDAO.getById(meetingId);
					meetingDAO.addUser(userAdd, meetingToAdd);
					listMessagesOut.add(new MessageCommandOut(userAdd)
							.setText("Вы добавленны во встречу \"" + meetingToAdd.getName() + "\""));
					listMessagesOut.add(new MessageCommandOut(message, message.getDeleteMessageId())
							.setText("Пользователь добавлен."));
				} else {
					listMessagesOut.add(new MessageCommandOut(message, message.getDeleteMessageId())
							.setText("Некорректно задан пользователь."));
				}
			} else {
				listMessagesOut.add(new MessageCommandOut(message, message.getDeleteMessageId())
						.setText("Некорректно заданая встреча"));
			}

		} else {
			listMessagesOut.add(new MessageCommandOut(message, message.getDeleteMessageId())
					.setText("Ошибка при добавлении участника. Неверный формат команды."));
		}

		return listMessagesOut;
	}

}
