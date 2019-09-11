package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import entities.Meeting;

public class MeetingDAO extends DAO<Meeting> {

	private Connection connection = null;
	private ArrayList<Meeting> meetings = new ArrayList<Meeting>();

	public MeetingDAO() {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT name, date, meetingid FROM bot.meetings;");
			while (resultSet.next()) {
				meetings.add(new Meeting(resultSet.getObject("name").toString(), resultSet.getObject("date").toString(),
						resultSet.getObject("meetingid").toString()));
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void insert(Meeting obj) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
			Statement statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO bot.meetings (name, date, meetingId) VALUES('" + obj.getName() + "', '"
					+ formatter.format(obj.getDate()) + "','" + obj.getMeetingId() + "')");
			meetings.add(obj);
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Meeting getById(String id) {
		for (Meeting meeting : meetings) {
			if (meeting.getMeetingId().equals(id)) {
				return meeting;
			}
		}
		return null;
	}

	@Override
	public void update(Meeting obj) {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
			Statement statement = connection.createStatement();
			statement.executeUpdate("update bot.meetings set name='" + obj.getName() + "', date='" + obj.getDate()
					+ "' where meetingId='" + obj.getMeetingId() + "'");
			for (Meeting meeting : meetings) {
				if (meeting.getMeetingId().equals(obj.getMeetingId())) {
					meeting.setDate(obj.getDate());
					meeting.setName(obj.getName());
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
	public void delete(Meeting obj) {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"));
			Statement statement = connection.createStatement();
			statement.executeUpdate("delete from bot.meetings where meetingId='" + obj.getMeetingId() + "'");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		meetings.remove(obj);
	}

	@Override
	public ArrayList<Meeting> getAll() {
		return meetings;
	}
}
