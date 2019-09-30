package commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dao.MeetingDAO;
import entities.Meeting;

class CommandGetMeetings implements Command {

	@Override
	public List<MessageCommandOut> execute(MessageCommandIn message) {
		ArrayList<MessageCommandOut> listMessagesOut = new ArrayList<>();
		MeetingDAO meetingDAO = new MeetingDAO();
		MessageCommandOut messageOut = new MessageCommandOut(message, message.getDeleteMessageId())
				.setText("Детализация:");
		HashMap<String, String> buttonHashMap = new HashMap<>();
		for (Meeting meeting : meetingDAO.getAll()) {
			buttonHashMap.put(meeting.getName(), "/getMeetingCommands \"" + meeting.getMeetingId() + "\"");
		}
		messageOut.addButtons(buttonHashMap);
		listMessagesOut.add(messageOut);
		return listMessagesOut;
	}

}
