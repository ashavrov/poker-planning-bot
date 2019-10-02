package commands;

import java.util.ArrayList;
import java.util.List;

class ConstructorCommand implements Command {

	@Override
	public List<MessageCommandOut> execute(MessageCommandIn message) {
		ArrayList<MessageCommandOut> listMessagesOut = new ArrayList<>();
		MessageCommandOut messageOut = new MessageCommandOut(message, message.getDeleteMessageId());
		ArrayList<String> questions = new ArrayList<>();
		if (message.getMessage().split(" ")[1].equals("/createMeeting")) {
			questions.add("Введите название:");
			questions.add("Введите дату:");
			questions.add("Введите время:");
		}else if(message.getMessage().split(" ")[1].equals("/createGame")){
			questions.add("Введите название:");
		} else {
			listMessagesOut.add(new MessageCommandOut(message, message.getDeleteMessageId())
					.setText("Конструктор не поддерживает команду."));
			return listMessagesOut;
		}
		messageOut.setText(questions.get(0));
		for (String question : questions) {
			messageOut.addQuestion(question);
		}
		listMessagesOut.add(messageOut);
		return listMessagesOut;
	}

}
