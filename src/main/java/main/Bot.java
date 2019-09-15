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
		if (update.hasMessage() && update.getMessage().hasText()) {
			MessageCommandIn message = new MessageCommandIn(update.getMessage().getText(),
					update.getMessage().getFrom().getId(), update.getMessage().getChatId(),
					update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName());
			sendMsg(commandHandler.execute(message));
		} else if (update.hasCallbackQuery()) {
			MessageCommandIn message = new MessageCommandIn(update.getCallbackQuery().getMessage().getText(),
					update.getCallbackQuery().getMessage().getFrom().getId(),
					update.getCallbackQuery().getMessage().getChatId(),
					update.getCallbackQuery().getMessage().getFrom().getFirstName() + " "
							+ update.getCallbackQuery().getMessage().getFrom().getLastName());
			sendMsg(commandHandler.execute(message));
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

	public void sendMsg(List<MessageCommandOut> list) {
		try {
			for (MessageCommandOut message : list) {
				execute(message.getMessage());
			}
		} catch (TelegramApiException e) {
			log.catching(e);
		}
	}
}
