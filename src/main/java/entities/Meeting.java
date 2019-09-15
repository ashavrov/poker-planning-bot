package entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Meeting {
	private Date date = new Date();
	private ArrayList<User> users = new ArrayList<User>();
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
		this.date = dateTime;
		this.name = name;
		this.meetingId = UUID.randomUUID().toString();
	}

	public Meeting(String name, String dateString, String meetingId) {
		try {
			this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.name = name;
		this.meetingId = meetingId;
	}

	public Date getDate() {
		return date;
	}

	public ArrayList<User> getUsers() {
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
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((meetingId == null) ? 0 : meetingId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (date.compareTo(other.date) != 0) {
			return false;
		}
		if (meetingId == null) {
			if (other.meetingId != null)
				return false;
		} else if (!meetingId.equals(other.meetingId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
