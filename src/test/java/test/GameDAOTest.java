package test;

import dao.GameDAO;
import entities.Game;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class GameDAOTest {
    @Test
    public void testGameDAO(){
        GameDAO gameDAO = new GameDAO();
        Game game1 = new Game("1234", "NewGame1", "1234", false);
        Game game2 = new Game("1235", "NewGame2", "1234", true);
        gameDAO.insert(game1);
        Assert.assertEquals(game1, gameDAO.getById("1234"));
        Assert.assertEquals(game1, gameDAO.getByName("NewGame1"));
        Assert.assertEquals(game1, gameDAO.getByMeetingId("1234").get(0));
        gameDAO.insert(game2);
        ArrayList<Game> games = new ArrayList<>();
        games.add(game1);
        games.add(game2);
        Assert.assertEquals(games, gameDAO.getByMeetingId("1234"));

        game1.setEnds(true);
        game1.setMeetingId("2345");
        game1.setName("NewGame1Upd");
        gameDAO.update(game1);
        gameDAO = new GameDAO();
        Assert.assertEquals(game1, gameDAO.getById("1234"));
        Assert.assertEquals("NewGame1Upd", game1.getName());
        Assert.assertEquals("2345", game1.getMeetingId());
        Assert.assertTrue(game1.isEnds());

        Assert.assertNotNull(gameDAO.getAll());

        gameDAO.delete(gameDAO.getById("1234"));
        gameDAO.delete(gameDAO.getById("1235"));
        Assert.assertNull(gameDAO.getById("1234"));
        Assert.assertNull(gameDAO.getById("1235"));
        gameDAO = new GameDAO();
        Assert.assertNull(gameDAO.getById("1234"));
        Assert.assertNull(gameDAO.getById("1235"));
    }
}
