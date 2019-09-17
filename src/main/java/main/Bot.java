package main;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import commands.CommandHandler;
import commands.MessageCommandIn;
import commands.MessageCommandOut;

public class Bot extends TelegramLongPollingBot {
	private static Logger log = LogManager.getLogger(Bot.class);
	private CommandHandler commandHandler = new CommandHandler();

	@Override
	public void onUpdateReceived(Update update) {
		List<MessageCommandOut> messageOut = null;

		if (update.hasMessage() && update.getMessage().hasText()) {
			Integer userId = update.getMessage().getFrom().getId();
			String messageText = update.getMessage().getText();
			Long chatId = update.getMessage().getChatId();
			String userName = update.getMessage().getFrom().getFirstName() + " "
					+ update.getMessage().getFrom().getLastName();
			
			messageOut = commandHandler.execute(new MessageCommandIn(messageText, userId, chatId, userName, null));
			
		} else if (update.hasCallbackQuery()) {
			Integer userId = update.getCallbackQuery().getFrom().getId();
			String messageText = update.getCallbackQuery().getData();
			Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
			Long chatId = update.getCallbackQuery().getMessage().getChatId();
			String userName = update.getCallbackQuery().getMessage().getFrom().getFirstName() + " "
					+ update.getCallbackQuery().getMessage().getFrom().getLastName();
			
			messageOut = commandHandler.execute(new MessageCommandIn(messageText, userId, chatId, userName, messageId));
		}
		sendMsg(messageOut);
	}

	@Override
	public String getBotUsername() {
		return System.getenv("botName");
	}

	@Override
	public String getBotToken() {
		return System.getenv("botToken");
	}

	public void sendMsg(List<MessageCommandOut> listMessages) {
		try {
			for (MessageCommandOut message : listMessages) {
				if (message.getMessageDelete() != null) {
					execute(message.getMessageDelete());
				}
				execute(message.getMessage());
			}
		} catch (TelegramApiException e) {
			log.catching(e);
		}
	}
}
