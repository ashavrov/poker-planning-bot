package commands;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QuestionAnswerHandler {
	private ArrayList<String> arrayQuestion = new ArrayList<>();
	private ArrayList<String> arrayAnswer = new ArrayList<>();
	private String command = "";

	public QuestionAnswerHandler(String command) {
		this.command = command;
	}

	public void addQuestion(String question) {
		arrayQuestion.add(question);
	}

	public boolean addAnswer(String answer) {
		arrayAnswer.add("\"" + answer + "\"");
		arrayQuestion.remove(0);
		return arrayQuestion.isEmpty();
	}

	public ArrayList<MessageCommandOut> getNewQuestion(MessageCommandIn message) {
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
		String fullCommand = command;
		for (String answer : arrayAnswer) {
			fullCommand += " " + answer;
		}
		return fullCommand;
	}
}
