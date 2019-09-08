package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entities.User;

public class UserDAO extends DAO<User> {

	private Connection connection = null;
	private ArrayList<User> users = new ArrayList<User>();

	public UserDAO() {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select userId, chatId, name from bot.users");
			while (resultSet.next()) {
				users.add(new User(resultSet.getObject("userId").toString(), resultSet.getObject("chatId").toString(),
						resultSet.getObject("name").toString()));
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void insert(User obj) {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
			Statement statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO bot.users (userId, chatId, name) VALUES('" + obj.getUserId() + "', '"
					+ obj.getChatId() + "','" + obj.getName() + "')");
			users.add(obj);
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
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
	public void update(User obj) {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
			Statement statement = connection.createStatement();
			statement.executeUpdate("update bot.users set userId='" + obj.getUserId() + "', chatId='" + obj.getChatId()
					+ "', name='" + obj.getName() + "' where userId='" + obj.getUserId() + "'");
			for (User user : users) {
				if (user.getUserId().equals(obj.getUserId())) {
					user.setChatId(obj.getChatId());
					user.setName(obj.getName());
				}
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(User obj) {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
			Statement statement = connection.createStatement();
			statement.executeUpdate("delete from bot.users where userId='" + obj.getUserId() + "'");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		users.remove(obj);
	}

	@Override
	public ArrayList<User> getAll() {
		return users;
	}

}
