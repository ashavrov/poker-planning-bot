package commands;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandGetMeetingCommands implements Command {

	@Override
	public List<MessageCommandOut> execute(MessageCommandIn message) {
		ArrayList<MessageCommandOut> listMessagesOut = new ArrayList<>();
		Pattern patternCommand = Pattern.compile("^(/.*?)(\\s)(\".*\")$");
		Matcher matcherCommand = patternCommand.matcher(message.getMessage());

		if (matcherCommand.find()) {
			String meetingId = matcherCommand.group(3).replace("\"", "");
			listMessagesOut.add(new MessageCommandOut(message, message.getDeleteMessageId()).setText("Создать игру:")
					.addButton("Создать", "/createGame +\"" + meetingId + "\""));

		} else {
			listMessagesOut.add(new MessageCommandOut(message, message.getDeleteMessageId())
					.setText("Неверный формат команды."));
		}
		return listMessagesOut;
	}

}
