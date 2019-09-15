package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entities.User;

public class UserDAO extends DAO<User> {
	private ArrayList<User> users = new ArrayList<User>();

	private ResultSet selectSQL(String sqlText) {
		ResultSet resultSet = null;
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlText);
			connection.close();
			return resultSet;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	private void updateSQL(String sqlText) {
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
			Statement statement = connection.createStatement();
			statement.executeUpdate(sqlText);
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public UserDAO() {
		try {
			ResultSet resultSet = selectSQL("select userId, chatId, name from bot.users");
			while (resultSet.next()) {
				users.add(new User(resultSet.getObject("userId").toString(), resultSet.getObject("chatId").toString(),
						resultSet.getObject("name").toString()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(User obj) {
		updateSQL("INSERT INTO bot.users (userId, chatId, name) VALUES('" + obj.getUserId() + "', '" + obj.getChatId()
				+ "','" + obj.getName() + "')");
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
		updateSQL("update bot.users set userId='" + obj.getUserId() + "', chatId='" + obj.getChatId() + "', name='"
				+ obj.getName() + "' where userId='" + obj.getUserId() + "'");
		for (User user : users) {
			if (user.getUserId().equals(obj.getUserId())) {
				user.setChatId(obj.getChatId());
				user.setName(obj.getName());
			}
		}
	}

	@Override
	public void delete(User obj) {
		updateSQL("delete from bot.users where userId='" + obj.getUserId() + "'");
		users.remove(obj);
	}

	@Override
	public ArrayList<User> getAll() {
		return users;
	}
}
