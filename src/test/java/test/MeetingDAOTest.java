package test;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import dao.MeetingDAO;
import entities.Meeting;

public class MeetingDAOTest {
	@Test
	public void testInsertMeeting() {
		MeetingDAO meetingDAO = new MeetingDAO();
		Assert.assertNotNull(meetingDAO);
		Meeting meeting = new Meeting(new Date(), "Test meeting");
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
	public void testUpdateMeeting() {
		MeetingDAO meetingDAO = new MeetingDAO();
		Assert.assertNotNull(meetingDAO);
		Meeting meeting = new Meeting(new Date(), "Test meeting");
		meetingDAO.insert(meeting);
		meetingDAO = null;
		meetingDAO = new MeetingDAO();
		Meeting meetingFromDB = meetingDAO.getById(meeting.getMeetingId());
		Assert.assertNotNull(meetingFromDB);
		Assert.assertTrue(meeting.equals(meetingFromDB));
		Date newDate = new Date();
		meetingFromDB.setDate(newDate);
		meetingDAO.update(meetingFromDB);
		meetingDAO = null;
		meetingDAO = new MeetingDAO();
		Assert.assertNotNull(meetingDAO.getAll().isEmpty());
		meetingFromDB = meetingDAO.getById(meeting.getMeetingId());
		Assert.assertTrue(newDate.toString().equals(meetingFromDB.getDate().toString()));
		meetingDAO.delete(meeting);
		Assert.assertNull(meetingDAO.getById(meeting.getMeetingId()));
	}

}
