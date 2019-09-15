package commands;

import java.util.List;

public interface Command {
	public List<MessageCommandOut> execute(MessageCommandIn message);
}
