package commands;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;


public class MessageCommandOut {
	private SendMessage message = new SendMessage();
	public MessageCommandOut(MessageCommandIn messageIn) {
		setChatId(messageIn.getChatId());
	}
	public void setChatId(Long chatId) {
		message.setChatId(chatId);
	}
	public void setText(String text) {
		message.setText(text);
	}
	public SendMessage getMessage() {
		return message;
	}
	public void addButton(String text, String callBack) {
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
	    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
	    List<InlineKeyboardButton> rowInline = new ArrayList<>();
	    rowInline.add(new InlineKeyboardButton().setText(text).setCallbackData(callBack));
	    rowsInline.add(rowInline);
	    markupInline.setKeyboard(rowsInline);
	    message.setReplyMarkup(markupInline);
	}
	
}
