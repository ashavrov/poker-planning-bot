package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import entities.Meeting;
import entities.User;

public class MeetingDAO extends DAO<Meeting> {
	private static Logger log = LogManager.getLogger(MeetingDAO.class);
	private ArrayList<Meeting> meetings = new ArrayList<>();
	private HashMap<String,ArrayList<User>> users = new HashMap<>();

	public void executeSQL(String sqlText, String tableName) throws SQLException {
		try (Connection connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"))) {
			try (Statement statement = connection.createStatement()) {
				statement.execute(sqlText);
				try (ResultSet resultSet = statement.getResultSet()) {
					if (resultSet != null) {
						if ("meetings".equals(tableName)) {
							getMeetingSQL(resultSet);
						} else if ("meeting_user".equals(tableName)) {
							getUserSQL(resultSet);
						}
					}
				}
			}
		}
	}

	private void getUserSQL(ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {
			users.get(resultSet.getObject("meetingId").toString()).add(new User(resultSet.getObject("userId").toString(), resultSet.getObject("chatId").toString(),
					resultSet.getObject("name").toString()));
		}
	}

	private void getMeetingSQL(ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {
			meetings.add(new Meeting(resultSet.getObject("name").toString(), resultSet.getObject("date").toString(),
					resultSet.getObject("meetingid").toString()));
			users.put(resultSet.getObject("meetingid").toString(), new ArrayList<>());
		}
	}

	public MeetingDAO() {
		try {
			executeSQL("SELECT name, date, meetingid FROM bot.meetings", "meetings");
			executeSQL("SELECT bot.users.userId, chatId, name, meetingid "
					+ "FROM bot.meeting_user JOIN bot.users ON bot.meeting_user.userId = bot.users.userId", "meeting_user");
		} catch (SQLException e) {
			log.catching(e);
		}
	}

	@Override
	public void insert(Meeting obj) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			executeSQL("INSERT INTO bot.meetings (name, date, meetingId) " + "VALUES ('" + obj.getName()
					+ "', TIMESTAMP '" + formatter.format(obj.getDate()) + "','" + obj.getMeetingId() + "')", "meetings");
		} catch (SQLException e) {
			log.catching(e);
		}
		meetings.add(obj);
		users.put(obj.getMeetingId(), new ArrayList<>());
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
					+ "WHERE meetingId='" + obj.getMeetingId() + "'", "meetings");
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
			executeSQL("DELETE FROM bot.meetings WHERE meetingId='" + obj.getMeetingId() + "'", "meetings");
			executeSQL("DELETE FROM bot.meeting_user WHERE meetingId='" + obj.getMeetingId() + "'", "meeting_user");
		} catch (SQLException e) {
			log.catching(e);
		}
		meetings.remove(obj);
		users.remove(obj.getMeetingId());
	}

	@Override
	public ArrayList<Meeting> getAll() {
		return meetings;
	}

	public void addUser(User user, Meeting meeting) {
		try {
			executeSQL("INSERT INTO bot.meeting_user " + "(userid, meetingid, primaryflag)" + "VALUES('"
					+ user.getUserId() + "', '" + meeting.getMeetingId() + "', 'N')", "meeting_user");
		} catch (SQLException e) {
			log.catching(e);
		}
		users.get(meeting.getMeetingId()).add(user);
	}

	public ArrayList<User> getAllUsers(Meeting meeting) {
		return users.get(meeting.getMeetingId());
	}
}
