package commands;

import java.util.List;

interface Command {
	List<MessageCommandOut> execute(MessageCommandIn message);
}
