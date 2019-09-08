package test;


import org.junit.Assert;
import org.junit.Test;

import commands.CommandHandler;

public class TestCommand {
	private CommandHandler handler = new CommandHandler();

	@Test
	public void testCommandStart() {
		String text = handler.execute("/start", 123456, (long) 123456, "Тест");
		Assert.assertEquals("Привет, Тест!", text);
	}

	@Test
	public void testCommandUnknown() {
		String text = handler.execute("/unknown", 345, (long) 345, "Тест");
		Assert.assertEquals("Неизвестная команда.", text);
	}

}
