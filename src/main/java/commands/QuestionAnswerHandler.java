package commands;

import java.util.ArrayList;
import java.util.List;


public class QuestionAnswerHandler {
	private final ArrayList<String> arrayQuestion = new ArrayList<>();
	private final ArrayList<String> arrayAnswer = new ArrayList<>();
	private final String command;

	public QuestionAnswerHandler(String command) {
		this.command = command;
	}

	public void addQuestion(String question) {
		arrayQuestion.add(question);
	}

	public void addAnswer(String answer) {
		arrayAnswer.add("\"" + answer + "\"");
		arrayQuestion.remove(0);
    }

	public List<MessageCommandOut> getNewQuestion(MessageCommandIn message) {
		ArrayList<MessageCommandOut> listMessagesOut = new ArrayList<>();
		MessageCommandOut messageOut = new MessageCommandOut(message, message.getDeleteMessageId());
		if (!isQuestionExists()) {
			messageOut.setText("Необходимо подтверждение:");
			messageOut.addButton("Подтвердить", getFullCommand());
		} else {
			messageOut.setText(arrayQuestion.get(0));
		}
		listMessagesOut.add(messageOut);
		return listMessagesOut;
	}

	public boolean isQuestionExists() {
		return !arrayQuestion.isEmpty();
	}

	public String getFullCommand() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(command);
		for (String answer : arrayAnswer) {
			stringBuilder.append(" ").append(answer);
		}
		return stringBuilder.toString();
	}
}
