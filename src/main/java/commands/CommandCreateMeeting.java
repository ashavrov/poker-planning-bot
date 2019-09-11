package commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import dao.MeetingDAO;
import entities.Meeting;
import entities.MessageCommand;

public class CommandCreateMeeting implements Command {

	@Override
	public String execute(MessageCommand message) {
		String[] args = message.getMessage().split(" ");
		MeetingDAO meetingDAO = new MeetingDAO();
		try {
			Meeting meeting = new Meeting(new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS").parse(args[2]), args[1]);
			meetingDAO.insert(meeting);
			return "Встреча создана.";
		} catch (ParseException e) {
			e.printStackTrace();
			return "Ошибка при создании встречи.";
		}
	}
}
