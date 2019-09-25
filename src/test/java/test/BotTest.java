package test;

import org.junit.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;

import main.Bot;

import static java.util.Arrays.asList;

public class BotTest {

	@Test
	public void testOnUpdateReceived() {
		Bot bot = Mockito.mock(Bot.class);
		
		Update update1 = new Update();
		Update update2 = new Update();
		Mockito.doCallRealMethod().when(bot).onUpdatesReceived(asList(update1, update2));
		bot.onUpdatesReceived(asList(update1, update2));
		Mockito.verify(bot).onUpdateReceived(update1);
		Mockito.verify(bot).onUpdateReceived(update2);
	}
}
