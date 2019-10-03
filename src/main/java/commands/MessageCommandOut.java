package commands;

import entities.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MessageCommandOut {
	private final SendMessage message = new SendMessage();
	private DeleteMessage messageDelete = null;
	private QuestionAnswerHandler questionAnswerHandler = new QuestionAnswerHandler("");
	private final String userId;

	public MessageCommandOut(MessageCommandIn messageIn, Integer deleteMessageId) {
		setChatId(messageIn.getChatId().toString());
		setButtons();
		if ("/c".equals(messageIn.getCommand())) {
			questionAnswerHandler = new QuestionAnswerHandler(messageIn.getMessage().replace("/c ", ""));
		}
		this.userId = messageIn.getUserId().toString();
		if (deleteMessageId != null) {
			messageDelete = new DeleteMessage();
			messageDelete.setChatId(messageIn.getChatId()).setMessageId(deleteMessageId);
		}
	}

	public MessageCommandOut(User user, Integer deleteMessageId) {
		this.userId = user.getChatId();
		setChatId(user.getChatId());
		setButtons();
		if (deleteMessageId != null) {
			messageDelete = new DeleteMessage();
			messageDelete.setChatId(user.getChatId()).setMessageId(deleteMessageId);
		}
	}

	private void setChatId(String chatId) {
		message.setChatId(chatId);
	}

	public String getUserId() {
		return userId;
	}

	public QuestionAnswerHandler getQuestionAnswerHandler() {
		return questionAnswerHandler;
	}

	public MessageCommandOut setText(String text) {
		message.setText(text);
		return this;
	}

	public SendMessage getMessage() {
		return message;
	}

	public DeleteMessage getMessageDelete() {
		return messageDelete;
	}

	public void addQuestion(String question) {
		questionAnswerHandler.addQuestion(question);
	}

	public boolean isQuestionExists() {
		return questionAnswerHandler.isQuestionExists();
	}

	public void addAnswer(String answer) {
		questionAnswerHandler.addAnswer(answer);
	}

	public String getCommandText() {
		return questionAnswerHandler.getFullCommand();
	}

	public MessageCommandOut addButton(String text, String callBack) {
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		List<InlineKeyboardButton> rowInline = new ArrayList<>();
		rowInline.add(new InlineKeyboardButton().setText(text).setCallbackData(callBack));
		rowsInline.add(rowInline);
		markupInline.setKeyboard(rowsInline);
		message.setReplyMarkup(markupInline);
		return this;
	}

	public void addButtons(Map<String, String> buttonHashMap) {
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		for (Map.Entry<String, String> entry : buttonHashMap.entrySet()) {
			List<InlineKeyboardButton> rowInline = new ArrayList<>();
			rowInline.add(new InlineKeyboardButton().setText(entry.getKey()).setCallbackData(entry.getValue()));
			rowsInline.add(rowInline);
		}
		markupInline.setKeyboard(rowsInline);
		message.setReplyMarkup(markupInline);
	}

	private void setButtons() {
		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
		message.setReplyMarkup(replyKeyboardMarkup);
		replyKeyboardMarkup.setSelective(true);
		replyKeyboardMarkup.setResizeKeyboard(true);
		replyKeyboardMarkup.setOneTimeKeyboard(false);
		List<KeyboardRow> keyboard = new ArrayList<>();
		KeyboardRow keyboardFirstRow = new KeyboardRow();
		keyboardFirstRow.add(new KeyboardButton("/showMenu"));
		KeyboardRow keyboardSecondRow = new KeyboardRow();
		keyboardSecondRow.add(new KeyboardButton("/help"));
		keyboard.add(keyboardFirstRow);
		keyboard.add(keyboardSecondRow);
		replyKeyboardMarkup.setKeyboard(keyboard);
	}

}
