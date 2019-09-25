package commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandShowMenu implements Command {
	@Override
	public List<MessageCommandOut> execute(MessageCommandIn message) {
		ArrayList<MessageCommandOut> listMessagesOut = new ArrayList<>();
		MessageCommandOut messageOut = new MessageCommandOut(message, message.getDeleteMessageId());
		HashMap<String, String> buttonHashMap = new HashMap<>();
		messageOut.setText("Меню:");
		buttonHashMap.put("Создать встречу", "/constructorCommand /createMeeting");
		buttonHashMap.put("Все встречи", "/getMeetings");
		messageOut.addButtons(buttonHashMap);
		listMessagesOut.add(messageOut);
		return listMessagesOut;
	}

}
