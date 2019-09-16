package test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import dao.MeetingDAO;
import entities.Meeting;

public class MeetingDAOTest {
	@Test
	public void testInsertMeeting() throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		MeetingDAO meetingDAO = new MeetingDAO();
		Assert.assertNotNull(meetingDAO);
		Meeting meeting = new Meeting(formatter.parse(formatter.format(new Date())), "Test meeting");
		meetingDAO.insert(meeting);
		
		meetingDAO = null;
		meetingDAO = new MeetingDAO();
		Meeting meetingFromDB = meetingDAO.getById(meeting.getMeetingId());
		Assert.assertNotNull(meetingFromDB);
		Assert.assertTrue(meeting.equals(meetingFromDB));
		meetingDAO.delete(meeting);
		Assert.assertNull(meetingDAO.getById(meeting.getMeetingId()));
	}

	@Test
	public void testUpdateMeeting() throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		MeetingDAO meetingDAO = new MeetingDAO();
		Meeting meeting = new Meeting(formatter.parse(formatter.format(new Date())), "Test meeting 1");
		meetingDAO.insert(meeting);
		meetingDAO = null;
		
		meetingDAO = new MeetingDAO();
		Meeting meetingFromDB = meetingDAO.getById(meeting.getMeetingId());
		Assert.assertNotNull(meetingFromDB);
		Assert.assertTrue(meeting.equals(meetingFromDB));
		Date newDate = formatter.parse(formatter.format(new Date()));
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(newDate); 
		calendar.add(Calendar.DATE, 1);
		newDate = calendar.getTime();
		meeting.setDate(newDate);
		meetingDAO.update(meeting);
		meetingDAO = null;
		
		meetingDAO = new MeetingDAO();
		Assert.assertNotNull(meetingDAO.getAll().isEmpty());
		meetingFromDB = meetingDAO.getById(meeting.getMeetingId());
		Assert.assertTrue(newDate.toString().equals(meetingFromDB.getDate().toString()));
		Assert.assertNull(meetingDAO.getById("0987"));
		Assert.assertNull(meetingDAO.getByName("NoMeeting"));
		meetingDAO.delete(meeting);
		Assert.assertNull(meetingDAO.getById(meeting.getMeetingId()));
		
		
	}

}
