package commands;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
		MessageCommandOut messageOut = new MessageCommandOut(message);
		String[] args = message.getMessage().split(" ");
		MeetingDAO meetingDAO = new MeetingDAO();
		UserDAO userDAO = new UserDAO();
		try {
			Meeting meeting = new Meeting(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(args[2] + " " + args[3]),
					args[1]);
			meetingDAO.insert(meeting);
			messageOut.setText("Встреча создана.");
			for (User user : userDAO.getAll()) {
				messageOut.addButton("Добавить пользователя: " + user.getName(), "/addUser");
				listMessagesOut.add(messageOut);
			}
			return listMessagesOut;
		} catch (Exception e) {
			log.catching(e);
			messageOut.setText("Ошибка при создании встречи.");
			listMessagesOut.add(messageOut);
			return listMessagesOut;
		}
	}
}
