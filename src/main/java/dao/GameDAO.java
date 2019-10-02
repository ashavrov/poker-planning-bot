package dao;

import entities.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameDAO implements DAO<Game> {
    private static final Logger log = LogManager.getLogger(GameDAO.class);
    private final ArrayList<Game> games = new ArrayList<>();

    private void executeSQL(String sqlText) throws SQLException {
        try (Connection connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"))) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(sqlText);
                try (ResultSet resultSet = statement.getResultSet()) {
                    if (resultSet != null) {
                        getGameSQL(resultSet);
                    }
                }
            }
        }
    }

    private void getGameSQL(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            games.add(new Game(resultSet.getObject("gameId").toString(),
                    resultSet.getObject("name").toString(),
                    resultSet.getObject("meetingId").toString(),
                    resultSet.getObject("endsFlag").toString().equals("Y")));
        }
    }

    public GameDAO() {
        try {
            executeSQL("SELECT gameId, name, meetingId, endsFlag FROM bot.games");
        } catch (SQLException e) {
            log.catching(e);
        }
    }

    @Override
    public void insert(Game obj) {
        try {
            executeSQL("INSERT INTO bot.games (gameId, name, meetingId) " + "VALUES ('"
                    + obj.getGameId()
                    + "', '" + obj.getName()
                    + "', '" + obj.getMeetingId() + "')");
        } catch (SQLException e) {
            log.catching(e);
        }
        games.add(obj);
    }

    @Override
    public Game getById(String id) {
        for (Game game : games) {
            if (game.getGameId().equals(id)) {
                return game;
            }
        }
        return null;
    }

    @Override
    public Game getByName(String name) {
        for (Game game : games) {
            if (game.getName().equals(name)) {
                return game;
            }
        }
        return null;
    }

    public List<Game> getByMeetingId(String meetingId) {
        ArrayList<Game> meetingGames = new ArrayList<>();
        for (Game game : games) {
            if (game.getMeetingId().equals(meetingId)) {
                meetingGames.add(game);
            }
        }
        return meetingGames;
    }

    @Override
    public void update(Game obj) {
        try {
            executeSQL("UPDATE bot.games " + "SET name='" + obj.getName()
                    + "', meetingId='" + obj.getMeetingId()
                    + "', endsFlag = '" + (obj.isEnds() ? "Y" : "N") + "'"
                    + "WHERE gameId='" + obj.getGameId() + "'");
        } catch (SQLException e) {
            log.catching(e);
        }
        for (Game game : games) {
            if (game.getGameId().equals(obj.getGameId())) {
                game.setMeetingId(obj.getMeetingId());
                game.setName(obj.getName());
                game.setEnds(obj.isEnds());
            }
        }
    }

    @Override
    public void delete(Game obj) {
        try {
            executeSQL("DELETE FROM bot.games WHERE gameId='" + obj.getGameId() + "'");
        } catch (SQLException e) {
            log.catching(e);
        }
        games.remove(obj);
    }

    @Override
    public List<Game> getAll() {
        return games;
    }
}
