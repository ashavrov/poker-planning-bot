package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import entities.User;

public class UserDAO implements DAO<User> {
	private static Logger log = LogManager.getLogger(UserDAO.class);
	private ArrayList<User> users = new ArrayList<>();

	public void executeSQL(String sqlText) throws SQLException {
		try (Connection connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"))) {
			try (Statement statement = connection.createStatement()) {
				statement.execute(sqlText);
				try (ResultSet resultSet = statement.getResultSet()) {
					if (resultSet != null) {
						getUsersSQL(resultSet);
					}
				}
			}
		}
	}

	private void getUsersSQL(ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {
			users.add(new User(resultSet.getObject("userId").toString(), resultSet.getObject("chatId").toString(),
					resultSet.getObject("name").toString()));
		}
	}

	public UserDAO() {
			try {
				executeSQL("select userId, chatId, name from bot.users");
			} catch (SQLException e) {
				log.catching(e);
			}
	}

	@Override
	public void insert(User obj) {
		try {
			executeSQL("INSERT INTO bot.users (userId, chatId, name) VALUES('" + obj.getUserId() + "', '"
					+ obj.getChatId() + "','" + obj.getName() + "')");
		} catch (SQLException e) {
			log.catching(e);
		}
		users.add(obj);
	}

	@Override
	public User getById(String id) {
		for (User user : users) {
			if (user.getUserId().equals(id)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public User getByName(String name) {
		for (User user : users) {
			if (user.getName().equals(name)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public void update(User obj) {
		try {
			executeSQL("update bot.users set userId='" + obj.getUserId() + "', chatId='" + obj.getChatId() + "', name='"
					+ obj.getName() + "' where userId='" + obj.getUserId() + "'");
		} catch (SQLException e) {
			log.catching(e);
		}
		for (User user : users) {
			if (user.getUserId().equals(obj.getUserId())) {
				user.setChatId(obj.getChatId());
				user.setName(obj.getName());
			}
		}
	}

	@Override
	public void delete(User obj) {
		try {
			executeSQL("delete from bot.users where userId='" + obj.getUserId() + "'");
		} catch (SQLException e) {
			log.catching(e);
		}
		users.remove(obj);
	}

	@Override
	public ArrayList<User> getAll() {
		return users;
	}
}
