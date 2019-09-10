package commands;

import entities.MessageCommand;

public interface Command {
	public String execute(MessageCommand message);
}
