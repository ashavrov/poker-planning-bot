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
	private ArrayList<Meeting> meetings = new ArrayList<Meeting>();

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

	public MeetingDAO() {
		ResultSet resultSet = selectSQL("SELECT name, date, meetingid FROM bot.meetings;");
		try {
			while (resultSet.next()) {
				meetings.add(new Meeting(resultSet.getObject("name").toString(), resultSet.getObject("date").toString(),
						resultSet.getObject("meetingid").toString()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(Meeting obj) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		updateSQL("INSERT INTO bot.meetings (name, date, meetingId) " + "VALUES ('" + obj.getName() + "', '"
				+ formatter.format(obj.getDate()) + "','" + obj.getMeetingId() + "')");
		meetings.add(obj);
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
	public Meeting getByName(String name) {
		for (Meeting meeting : meetings) {
			if (meeting.getName().equals(name)) {
				return meeting;
			}
		}
		return null;
	}

	@Override
	public void update(Meeting obj) {
		updateSQL("UPDATE bot.meetings " + "SET name='" + obj.getName() + "', date='" + obj.getDate() + "' "
				+ "WHERE meetingId='" + obj.getMeetingId() + "'");
		for (Meeting meeting : meetings) {
			if (meeting.getMeetingId().equals(obj.getMeetingId())) {
				meeting.setDate(obj.getDate());
				meeting.setName(obj.getName());
			}
		}
	}

	@Override
	public void delete(Meeting obj) {
		updateSQL("DELETE FROM bot.meetings WHERE meetingId='" + obj.getMeetingId() + "'");
		meetings.remove(obj);
	}

	@Override
	public ArrayList<Meeting> getAll() {
		return meetings;
	}
}
