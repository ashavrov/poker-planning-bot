package entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Meeting {
	private static Logger log = LogManager.getLogger(Meeting.class);
	private Date date = new Date();
	private ArrayList<User> users = new ArrayList<>();
	private User primaryUser = null;

	private String name = "";
	private String meetingId = "";

	public User getPrimaryUser() {
		return primaryUser;
	}

	public void setPrimaryUser(User primaryUser) {
		this.primaryUser = primaryUser;
	}

	public Meeting(Date dateTime, String name) {
		this.date = dateTime == null ? new Date() : dateTime;
		this.name = name == null ? "" : name;
		this.meetingId = UUID.randomUUID().toString();
	}

	public Meeting(String name, String dateString, String meetingId) {
		try {
			this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateString);
		} catch (Exception e) {
			log.catching(e);
		}
		this.name = name == null ? "" : name;
		this.meetingId = meetingId == null ? "" : meetingId;
	}

	public Date getDate() {
		return date;
	}

	public List<User> getUsers() {
		return users;
	}

	public String getName() {
		return name;
	}

	public void addUser(User user) {
		users.add(user);
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + date.hashCode();
		result = prime * result + meetingId.hashCode();
		result = prime * result + name.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Meeting other = (Meeting) obj;
		return date.compareTo(other.date)==0 && meetingId.equals(other.meetingId) && name.equals(other.name);
	}
}
