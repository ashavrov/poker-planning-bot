package main;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import commands.CommandHandler;
import entities.MessageCommand;

public class Bot extends TelegramLongPollingBot {
	private CommandHandler commandHandler = new CommandHandler();

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			MessageCommand message = new MessageCommand(update.getMessage().getText(),
					update.getMessage().getFrom().getId(), update.getMessage().getChatId(),
					update.getMessage().getFrom().getFirstName());
			commandHandler.execute(message);
		}
	}

	@Override
	public String getBotUsername() {
		return System.getenv("botName");
	}

	@Override
	public String getBotToken() {
		return System.getenv("botToken");
	}

	public void sendMsg(String chatId, String textMessage) {
		SendMessage message = new SendMessage();
		message.setChatId(chatId).setText(textMessage);
		try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
