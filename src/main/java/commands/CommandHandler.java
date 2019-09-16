package commands;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ashavrov Command handler class
 */
public class CommandHandler {
	private CommandStart commandStart = new CommandStart();
	private CommandCreateMeeting commandCreateMeeting = new CommandCreateMeeting();

	public List<MessageCommandOut> execute(MessageCommandIn message) {
		if ("/start".equals(message.getCommand())) {
			return commandStart.execute(message);
		} else if ("/createMeeting".equals(message.getCommand())) {
			return commandCreateMeeting.execute(message);
		} else {
			ArrayList<MessageCommandOut> listMessagesOut = new ArrayList<>();
			MessageCommandOut messageOut = new MessageCommandOut(message);
			messageOut.setText("Неизвестная команда.");
			listMessagesOut.add(messageOut);
			return listMessagesOut;
		}
	}
}
