package utilities;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.User;

import java.sql.Connection;

public class DataBase {
	private Connection connection = null;
	public int status = 0;

	public void init() {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
			status = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			connection.close();
			status = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public User getUser(Integer userId, Long chatId, String name) {
		try {
			User user = null;
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"select u.userId, u.chatId, name from bot.users u where userId = '" + userId.toString() + "'");
			if (resultSet.next()) {
				user = new User(resultSet.getString("userId"), resultSet.getString("chatId"),
						resultSet.getString("name"));
			} else {
				statement.executeUpdate("INSERT INTO bot.users (userId, chatId, name) VALUES('" + userId.toString()
						+ "', '" + chatId.toString() + "','" + name + "')");
				user = new User(userId.toString(), chatId.toString(), name);
			}
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public void removeUser(Integer userId) {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("delete from bot.users where userId='" + userId.toString() + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
