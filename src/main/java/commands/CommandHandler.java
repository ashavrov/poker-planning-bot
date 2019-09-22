package commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Handler message class
 * 
 * @author ashavrov
 * 
 */
public class CommandHandler {
	private HashMap<String, QuestionAnswerHandler> questionAnswerHandlers = new HashMap<>();

	private CommandStart commandStart = new CommandStart();
	private ConstrunctorCommand constructorCommand = new ConstrunctorCommand();
	private CommandCreateMeeting commandCreateMeeting = new CommandCreateMeeting();
	private CommandShowMenu commandShowMenu = new CommandShowMenu();
	private CommandAddUser commandAddUser = new CommandAddUser();
	private CommandGetMeetings commandGetMeetings = new CommandGetMeetings();
	private CommandGetMeetingCommands commandGetMeetingCommands = new CommandGetMeetingCommands();

	public List<MessageCommandOut> execute(MessageCommandIn message) {
		List<MessageCommandOut> messagesOut = new ArrayList<>();
		String userId = message.getUserId().toString();
		if (questionAnswerHandlers.containsKey(userId)) {
			QuestionAnswerHandler questionAnswerHandler = questionAnswerHandlers.get(userId);
			if (questionAnswerHandler.isQuestionExists()) {
				questionAnswerHandler.addAnswer(message.getMessage());
				messagesOut = questionAnswerHandler.getNewQuestion(message);
				if(!questionAnswerHandler.isQuestionExists()) {
					questionAnswerHandlers.remove(userId);
				}
				return messagesOut;
			}
		}
		if ("/constructorCommand".equals(message.getCommand())) {
			messagesOut = constructorCommand.execute(message);
		} else if ("/start".equals(message.getCommand())) {
			messagesOut = commandStart.execute(message);
		} else if ("/createMeeting".equals(message.getCommand())) {
			messagesOut = commandCreateMeeting.execute(message);
		} else if ("/showMenu".equals(message.getCommand())) {
			messagesOut = commandShowMenu.execute(message);
		} else if ("/addUser".equals(message.getCommand())) {
			messagesOut = commandAddUser.execute(message);
		}else if ("/getMeetings".equals(message.getCommand())) {
				messagesOut = commandGetMeetings.execute(message);
		}else if ("/getMeetingCommands".equals(message.getCommand())) {
			messagesOut = commandGetMeetingCommands.execute(message);
		} else {
			messagesOut
					.add(new MessageCommandOut(message, message.getDeleteMessageId()).setText("Неизвестная команда."));
		}
		for (MessageCommandOut messageOut : messagesOut) {
			if (messageOut.isQuestionExists()) {
				questionAnswerHandlers.put(messageOut.getUserId(), messageOut.getQuestionAnswerHandler());
			}
		}
		return messagesOut;
	}
}
