package commands;

import java.util.ArrayList;

/**
 * @author ashavrov Command handler class
 */
public class CommandHandler {
	private CommandStart commandStart = new CommandStart();
	private CommandCreateMeeting commandCreateMeeting = new CommandCreateMeeting();

	public ArrayList<MessageCommandOut> execute(MessageCommandIn message) {
		if ("/start".equals(message.getCommand())) {
			return commandStart.execute(message);
		} else if ("/createMeeting".equals(message.getCommand())) {
			return commandCreateMeeting.execute(message);
		} else {
			ArrayList<MessageCommandOut> listMessagesOut = new ArrayList<MessageCommandOut>();
			MessageCommandOut messageOut = new MessageCommandOut(message);
			messageOut.setText("Неизвестная команда.");
			listMessagesOut.add(messageOut);
			return listMessagesOut;
		}
	}
}
