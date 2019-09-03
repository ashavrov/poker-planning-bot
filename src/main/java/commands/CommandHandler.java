package commands;

import java.util.HashMap;

import org.telegram.telegrambots.meta.api.objects.Message;

import main.User;
import utilities.DataBase;

/**
 * @author ashavrov 
 * Command handler class
 */
public class CommandHandler {
	DataBase dataBase = new DataBase();
	HashMap<Integer, User> users = new HashMap<Integer, User>();

	public CommandHandler() {
		dataBase.init();
	}

	public String execute(Message message) {
		return process(message.getText(), message.getFrom().getId(), message.getChatId(),
				message.getFrom().getFirstName());
	}

	public String execute(String command, Integer userId, Long chatId, String firstName) {
		return process(command, userId, chatId, firstName);
	}

	private String process(String command, Integer userId, Long chatId, String firstName) {
		String returnMessage = "Привет, " + firstName + "!";
		if (command.equals("/start")) {
			if (!users.containsKey(userId)) {
				User user = dataBase.getUser(userId, chatId, firstName);
				if (!user.equals(null)) {
					users.put(userId, user);
				} else {
					returnMessage = "Ошибка при инициализации пользователя.";
				}
			}
			return returnMessage;
		} else {
			return "Неизвестная команда.";
		}
	}
}
