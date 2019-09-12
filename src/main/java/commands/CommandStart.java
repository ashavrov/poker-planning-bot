package commands;

import dao.UserDAO;
import entities.User;

public class CommandStart implements Command {
	@Override
	public String execute(MessageCommandIn message) {
		UserDAO userDAO = new UserDAO();
		User user = userDAO.getById(message.getUserId().toString());
		if (user == null) {
			user = new User(message.getUserId().toString(), message.getChatId().toString(), message.getFirstName());
			userDAO.insert(user);
		}
		return "Привет, " + message.getFirstName() + "!";
	}
}
