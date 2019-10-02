package commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class-handler for manage all commands
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
	private final CommandCreateGame commandCreateGame = new CommandCreateGame();
	private final CommandShowGames commandShowGames = new CommandShowGames();

	public List<MessageCommandOut> execute(MessageCommandIn message) {
		List<MessageCommandOut> messagesOut = new ArrayList<>();
		String userId = message.getUserId().toString();
		if (questionAnswerHandlers.containsKey(userId)) {
			QuestionAnswerHandler questionAnswerHandler = questionAnswerHandlers.get(userId);
			if (questionAnswerHandler.isQuestionExists()) {
				questionAnswerHandler.addAnswer(message.getMessage());
				if (!questionAnswerHandler.isQuestionExists()) {
					message.setMessage(questionAnswerHandler.getFullCommand());
					questionAnswerHandlers.remove(userId);
				} else {
					messagesOut = questionAnswerHandler.getNewQuestion(message);
					return messagesOut;
				}
			}
		}
		switch (message.getCommand()) {
			case "/constructorCommand":
			case "/c":
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
			case "/createGame":
				messagesOut = commandCreateGame.execute(message);
				break;
			case "/showGames":
				messagesOut = commandShowGames.execute(message);
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
