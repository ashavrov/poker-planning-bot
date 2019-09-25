package commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Handler message class
 *
 * @author ashavrov
 */
public class CommandHandler {
	private final HashMap<String, QuestionAnswerHandler> questionAnswerHandlers = new HashMap<>();

	private final CommandStart commandStart = new CommandStart();
	private final ConstructorCommand constructorCommand = new ConstructorCommand();
	private final CommandCreateMeeting commandCreateMeeting = new CommandCreateMeeting();
	private final CommandShowMenu commandShowMenu = new CommandShowMenu();
	private final CommandAddUser commandAddUser = new CommandAddUser();
	private final CommandGetMeetings commandGetMeetings = new CommandGetMeetings();
	private final CommandGetMeetingCommands commandGetMeetingCommands = new CommandGetMeetingCommands();

	public List<MessageCommandOut> execute(MessageCommandIn message) {
		List<MessageCommandOut> messagesOut = new ArrayList<>();
		String userId = message.getUserId().toString();
		if (questionAnswerHandlers.containsKey(userId)) {
			QuestionAnswerHandler questionAnswerHandler = questionAnswerHandlers.get(userId);
			if (questionAnswerHandler.isQuestionExists()) {
				questionAnswerHandler.addAnswer(message.getMessage());
				messagesOut = questionAnswerHandler.getNewQuestion(message);
				if (!questionAnswerHandler.isQuestionExists()) {
					questionAnswerHandlers.remove(userId);
				}
				return messagesOut;
			}
		}
		switch (message.getCommand()) {
			case "/constructorCommand":
				messagesOut = constructorCommand.execute(message);
				break;
			case "/start":
				messagesOut = commandStart.execute(message);
				break;
			case "/createMeeting":
				messagesOut = commandCreateMeeting.execute(message);
				break;
			case "/showMenu":
				messagesOut = commandShowMenu.execute(message);
				break;
			case "/addUser":
				messagesOut = commandAddUser.execute(message);
				break;
			case "/getMeetings":
				messagesOut = commandGetMeetings.execute(message);
				break;
			case "/getMeetingCommands":
				messagesOut = commandGetMeetingCommands.execute(message);
				break;
			default:
				messagesOut
						.add(new MessageCommandOut(message, message.getDeleteMessageId()).setText("Неизвестная команда."));
				break;
		}
		for (MessageCommandOut messageOut : messagesOut) {
			if (messageOut.isQuestionExists()) {
				questionAnswerHandlers.put(messageOut.getUserId(), messageOut.getQuestionAnswerHandler());
			}
		}
		return messagesOut;
	}
}
