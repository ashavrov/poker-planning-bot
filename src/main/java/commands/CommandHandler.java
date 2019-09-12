package commands;

/**
 * @author ashavrov Command handler class
 */
public class CommandHandler {
	private CommandStart commandStart = new CommandStart();
	private CommandCreateMeeting commandCreateMeeting = new CommandCreateMeeting();
	public String execute(MessageCommandIn message) {
		String returnMessage = "";
		if ("/start".equals(message.getCommand())) {
			returnMessage = commandStart.execute(message);
			return returnMessage;
		} else if("/createMeeting".equals(message.getCommand())) {
			returnMessage = commandCreateMeeting.execute(message);
			return returnMessage;
		}
		else {
			return "Неизвестная команда.";
		}
	}
}
