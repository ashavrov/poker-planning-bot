package commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import dao.MeetingDAO;
import entities.Meeting;

public class CommandCreateMeeting implements Command {

	@Override
	public ArrayList<MessageCommandOut> execute(MessageCommandIn message) {
		ArrayList<MessageCommandOut> listMessagesOut = new ArrayList<MessageCommandOut>();
		MessageCommandOut messageOut = new MessageCommandOut(message);
		String[] args = message.getMessage().split(" ");
		MeetingDAO meetingDAO = new MeetingDAO();
		try {
			Meeting meeting = new Meeting(new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS").parse(args[2]), args[1]);
			meetingDAO.insert(meeting);
			messageOut.setText("Встреча создана.");
			messageOut.addButton("Добавить пользователя.", "/addUser");
			listMessagesOut.add(messageOut);
			return listMessagesOut;
		} catch (ParseException e) {
			e.printStackTrace();
			messageOut.setText("Ошибка при создании встречи.");
			listMessagesOut.add(messageOut);
			return listMessagesOut;
		}
	}
}
