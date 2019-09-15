package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import entities.Meeting;

public class MeetingDAO extends DAO<Meeting> {
	private static Logger log = LogManager.getLogger(MeetingDAO.class);
	private ArrayList<Meeting> meetings = new ArrayList<>();

	public void executeSQL(String sqlText) throws SQLException {
		try (Connection connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"))) {
			try (Statement statement = connection.createStatement()) {
				statement.execute(sqlText);
				try (ResultSet resultSet = statement.getResultSet()) {
					if (resultSet != null) {
						getMeetingSQL(resultSet);
					}
				}
			}
		}
	}

	private void getMeetingSQL(ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {
			meetings.add(new Meeting(resultSet.getObject("name").toString(), resultSet.getObject("date").toString(),
					resultSet.getObject("meetingid").toString()));
		}
	}

	public MeetingDAO() {
		try {
			executeSQL("SELECT name, date, meetingid FROM bot.meetings;");
		} catch (SQLException e) {
			log.catching(e);
		}
	}

	@Override
	public void insert(Meeting obj) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			executeSQL("INSERT INTO bot.meetings (name, date, meetingId) " + "VALUES ('" + obj.getName()
					+ "', TIMESTAMP '" + formatter.format(obj.getDate()) + "','" + obj.getMeetingId() + "')");
		} catch (SQLException e) {
			log.catching(e);
		}
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
		try {
			executeSQL("UPDATE bot.meetings " + "SET name='" + obj.getName() + "', date='" + obj.getDate() + "' "
					+ "WHERE meetingId='" + obj.getMeetingId() + "'");
		} catch (SQLException e) {
			log.catching(e);
		}
		for (Meeting meeting : meetings) {
			if (meeting.getMeetingId().equals(obj.getMeetingId())) {
				meeting.setDate(obj.getDate());
				meeting.setName(obj.getName());
			}
		}
	}

	@Override
	public void delete(Meeting obj) {
		try {
			executeSQL("DELETE FROM bot.meetings WHERE meetingId='" + obj.getMeetingId() + "'");
		} catch (SQLException e) {
			log.catching(e);
		}
		meetings.remove(obj);
	}

	@Override
	public ArrayList<Meeting> getAll() {
		return meetings;
	}
}
