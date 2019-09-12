package commands;

import java.util.ArrayList;

public interface Command {
	public ArrayList<MessageCommandOut> execute(MessageCommandIn message);
}
