package commands;

import java.util.ArrayList;

import dao.UserDAO;
import entities.User;

public class CommandStart implements Command {
	@Override
	public ArrayList<MessageCommandOut> execute(MessageCommandIn message) {
		ArrayList<MessageCommandOut> listMessagesOut = new ArrayList<MessageCommandOut>();
		MessageCommandOut messageOut = new MessageCommandOut(message);
		UserDAO userDAO = new UserDAO();
		User user = userDAO.getById(message.getUserId().toString());
		if (user == null) {
			user = new User(message.getUserId().toString(), message.getChatId().toString(), message.getUserName() + " ");
			userDAO.insert(user);
		}
		messageOut.setText("Привет, " + message.getUserName() + "!");
		listMessagesOut.add(messageOut);
		return listMessagesOut;
	}
}
