package test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import dao.MeetingDAO;
import dao.UserDAO;
import entities.Meeting;

public class MeetingDAOTest {
	@Test
	public void testInsertMeeting() throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		MeetingDAO meetingDAO = new MeetingDAO();
		Assert.assertNotNull(meetingDAO);
		Meeting meeting = new Meeting(formatter.parse(formatter.format(new Date())), "Test meeting");
		meetingDAO.insert(meeting);
		meetingDAO.addUser(new UserDAO().getById("174913663"), meeting);

		Assert.assertEquals(meetingDAO.getAllUsers(meeting).get(0), new UserDAO().getById("174913663"));

		MeetingDAO meetingDAO2 = new MeetingDAO();
		Meeting meetingFromDB = meetingDAO2.getById(meeting.getMeetingId());
		Assert.assertNotNull(meetingFromDB);
		Assert.assertEquals(meeting, meetingFromDB);
		meetingDAO2.delete(meeting);
		Assert.assertNull(meetingDAO2.getById(meeting.getMeetingId()));
		Assert.assertNull(meetingDAO2.getAllUsers(meetingFromDB));
	}

	@Test
	public void testUpdateMeeting() throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		MeetingDAO meetingDAO = new MeetingDAO();
		Meeting meeting = new Meeting(formatter.parse(formatter.format(new Date())), "Test meeting 1");
		meetingDAO.insert(meeting);

		MeetingDAO meetingDAO2 = new MeetingDAO();
		Meeting meetingFromDB = meetingDAO2.getById(meeting.getMeetingId());
		Assert.assertNotNull(meetingFromDB);
		Assert.assertEquals(meeting, meetingFromDB);
		Date newDate = formatter.parse(formatter.format(new Date()));
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(newDate); 
		calendar.add(Calendar.DATE, 1);
		newDate = calendar.getTime();
		meeting.setDate(newDate);
		meetingDAO2.update(meeting);

		MeetingDAO meetingDAO3 = new MeetingDAO();
		meetingFromDB = meetingDAO3.getById(meeting.getMeetingId());
		Assert.assertEquals(newDate.toString(), meetingFromDB.getDate().toString());
		Assert.assertNull(meetingDAO3.getById("0987"));
		Assert.assertNull(meetingDAO3.getByName("NoMeeting"));
		meetingDAO3.delete(meeting);
		Assert.assertNull(meetingDAO3.getById(meeting.getMeetingId()));
		
		
	}

}
