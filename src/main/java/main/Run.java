package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import dao.MeetingDAO;

public class Run {
	public static void main(String[] args) {
		Logger log = LogManager.getLogger(MeetingDAO.class);
		ApiContextInitializer.init();

		TelegramBotsApi botsApi = new TelegramBotsApi();

		try {
			botsApi.registerBot(new Bot());
		} catch (TelegramApiException e) {
			log.catching(e);
		}
	}
}