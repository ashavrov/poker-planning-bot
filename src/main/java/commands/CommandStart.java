package commands;

import java.util.ArrayList;
import java.util.List;

import dao.UserDAO;
import entities.User;

public class CommandStart implements Command {
	@Override
	public List<MessageCommandOut> execute(MessageCommandIn message) {
		ArrayList<MessageCommandOut> listMessagesOut = new ArrayList<>();
		MessageCommandOut messageOut = new MessageCommandOut(message);
		UserDAO userDAO = new UserDAO();
		User user = userDAO.getById(message.getUserId().toString());
		if (user == null) {
			user = new User(message.getUserId().toString(), message.getChatId().toString(), message.getUserName());
			userDAO.insert(user);
		}
		messageOut.setText("Привет, " + message.getUserName() + "!");
		listMessagesOut.add(messageOut);
		return listMessagesOut;
	}
}
