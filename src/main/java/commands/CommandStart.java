package commands;

import dao.UserDAO;
import entities.MessageCommand;
import entities.User;

public class CommandStart implements Command {
	@Override
	public String execute(MessageCommand message) {
		UserDAO userDAO = new UserDAO();
		User user = userDAO.getById(message.getUserId().toString());
		if (user == null) {
			user = new User(message.getUserId().toString(), message.getChatId().toString(), message.getFirstName());
			userDAO.insert(user);
		}
		return "Привет, " + message.getFirstName() + "!";
	}
}
