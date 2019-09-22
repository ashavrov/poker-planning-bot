package commands;

import java.util.ArrayList;
import java.util.List;

public class CommandShowMenu implements Command {
	@Override
	public List<MessageCommandOut> execute(MessageCommandIn message) {
		ArrayList<MessageCommandOut> listMessagesOut = new ArrayList<>();
		MessageCommandOut messageOut = new MessageCommandOut(message, message.getDeleteMessageId());
		messageOut.setText("Меню:");
		messageOut.addButton("Создать встречу", "/constructorCommand /createMeeting");
		listMessagesOut.add(messageOut);
		return listMessagesOut;
	}

}
