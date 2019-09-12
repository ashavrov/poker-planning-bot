package commands;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.*;;

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
	private InlineKeyboardMarkup createButton() {
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
	    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<List<InlineKeyboardButton>>();
	    List<InlineKeyboardButton> rowInline = new ArrayList<InlineKeyboardButton>();
	    rowInline.add(new InlineKeyboardButton().setText("Update message text").setCallbackData("update_msg_text"));
	    // Set the keyboard to the markup
	    rowsInline.add(rowInline);
	    // Add it to the message
	    markupInline.setKeyboard(rowsInline);
	    return markupInline;
	}
	
}
