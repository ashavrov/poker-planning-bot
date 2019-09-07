package main;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import commands.CommandHandler;

public class Bot extends TelegramLongPollingBot {
	private CommandHandler commandHandler = new CommandHandler();

	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			SendMessage message = new SendMessage();
			String textMessage = commandHandler.execute(update.getMessage());
			message.setChatId(update.getMessage().getChatId()).setText(textMessage);
			try {
				execute(message);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
	}

	public String getBotUsername() {
		return System.getenv("botName");
	}

	@Override
	public String getBotToken() {
		return System.getenv("botToken");
	}
}
