package test;

import org.junit.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;

public class BotTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testOnUpdateReceived() {
		TelegramLongPollingBot bot = Mockito.mock(TelegramLongPollingBot.class);
		Mockito.doCallRealMethod().when(bot).onUpdatesReceived((List<Update>) any());
		Update update1 = new Update();
		Update update2 = new Update();
		bot.onUpdatesReceived(asList(update1, update2));
		Mockito.verify(bot).onUpdateReceived(update1);
		Mockito.verify(bot).onUpdateReceived(update2);
	}
}
