package commands;

import dao.MeetingDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CommandGetMeetingCommands implements Command {

	@Override
	public List<MessageCommandOut> execute(MessageCommandIn message) {
		ArrayList<MessageCommandOut> listMessagesOut = new ArrayList<>();
		Pattern patternCommand = Pattern.compile("^(/.*?)(\\s)(\".*\")$");
		Matcher matcherCommand = patternCommand.matcher(message.getMessage());
		MeetingDAO meetingDAO = new MeetingDAO();
		if (matcherCommand.find()) {
			String meetingId = matcherCommand.group(3).replace("\"", "");
			if (meetingDAO.getById(meetingId) != null) {
				MessageCommandOut messageOut = new MessageCommandOut(message, message.getDeleteMessageId());
				HashMap<String, String> buttonHashMap = new HashMap<>();
				messageOut.setText("Игры:");
				buttonHashMap.put("Создать", "/c /createGame \"" + meetingId + "\"");
				buttonHashMap.put("Показать игры", "/showGames \"" + meetingId + "\"");
				messageOut.addButtons(buttonHashMap);
				listMessagesOut.add(messageOut);
			} else {
				listMessagesOut.add(new MessageCommandOut(message, message.getDeleteMessageId())
						.setText("Некорректная встреча."));
			}
		} else {
			listMessagesOut.add(new MessageCommandOut(message, message.getDeleteMessageId())
					.setText("Неверный формат команды."));
		}
		return listMessagesOut;
	}

}
