package commands;

import entities.MessageCommand;

/**
 * @author ashavrov Command handler class
 */
public class CommandHandler {
	public CommandHandler() {

	}

	public String execute(MessageCommand message) {
		String returnMessage = "";
		if ("/start".equals(message.getCommand())) {
			CommandStart commandStart = new CommandStart();
			returnMessage = commandStart.execute(message);
			return returnMessage;
		} else if("/createMeeting".equals(message.getCommand())) {
			CommandCreateMeeting commandCreateMeeting = new CommandCreateMeeting();
			returnMessage = commandCreateMeeting.execute(message);
			return returnMessage;
		}
		else {
			return "Неизвестная команда.";
		}
	}
}
