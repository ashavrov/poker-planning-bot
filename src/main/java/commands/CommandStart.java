package commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.UserDAO;
import entities.User;

public class CommandStart implements Command {
	private static Logger log = LogManager.getLogger(CommandStart.class);
	@Override
	public List<MessageCommandOut> execute(MessageCommandIn message) {
		log.debug("Test","11");
		ArrayList<MessageCommandOut> listMessagesOut = new ArrayList<>();
		MessageCommandOut messageOut = new MessageCommandOut(message, null);
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
