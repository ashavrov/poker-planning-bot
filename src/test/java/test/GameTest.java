package test;

import entities.Game;
import org.junit.Assert;
import org.junit.Test;

public class GameTest {
    @Test
    public void testGame(){
        Game game1 = new Game("NewGame", "1234");
        Game game2 = new Game("NewGame", "1234");
        Assert.assertEquals(game1, game2);
        Assert.assertEquals(game1.hashCode(), game2.hashCode());
        Assert.assertNotEquals(game1.getGameId(), game2.getGameId());
        game1.setName("NewGame1");
        Assert.assertEquals("NewGame1", game1.getName());
        Assert.assertNotEquals(game1, game2);
        Assert.assertNotEquals(game1.hashCode(), game2.hashCode());
        game1.setMeetingId("4321");
        Assert.assertEquals("4321", game1.getMeetingId());
        Assert.assertNotEquals(game1, game2);
        game1.setEnds(true);
        Assert.assertTrue(game1.isEnds());
        Assert.assertNotEquals(game1, game2);
        Game game3 = new Game("1234", "NewGame3", "1234", true);
        Assert.assertEquals("1234", game3.getGameId());
        Assert.assertEquals("NewGame3", game3.getName());
        Assert.assertEquals("1234", game3.getMeetingId());
        Assert.assertTrue(game3.isEnds());
    }
}
