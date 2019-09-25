package test;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import entities.Meeting;
import entities.User;

public class MeetingTest {

	@Test
	public void test() {
		Date date = new Date();
		Meeting meeting1 = new Meeting(date, "Meeting1");
		Meeting meeting2 = new Meeting(date, "Meeting2");
		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String stringDate = formatter.format(new Date());
		Meeting meeting3 = new Meeting("Meeting1", stringDate, meeting1.getMeetingId());
		Meeting meeting5 = new Meeting(null, null, null);
		Meeting meeting6 = new Meeting(null, null);
		Assert.assertEquals(meeting1.hashCode(), meeting1.hashCode());
		Assert.assertTrue(meeting1.hashCode() != meeting2.hashCode());
		Assert.assertEquals(meeting1, meeting1);
		Assert.assertNotEquals(meeting1, meeting2);
		Assert.assertNotEquals(meeting2, meeting3);
		Assert.assertNotEquals(meeting1, null);
		Assert.assertNotNull(meeting1);
		Assert.assertNotEquals(meeting1, new Object());
		Assert.assertEquals("Meeting1", meeting1.getName());
		Assert.assertEquals(meeting1.getDate(), date);
		Assert.assertNotEquals(meeting3.getDate(), new Date());
		User user1 = new User("123123", "23423423", "TestUser");
		User user2 = new User("234234", "34534534", "TestUser2");
		meeting1.addUser(user1);
		meeting1.addUser(user2);
		meeting2.addUser(user1);
		meeting1.setPrimaryUser(user1);
		meeting2.setPrimaryUser(user1);
		Assert.assertSame(meeting1.getPrimaryUser(), meeting2.getPrimaryUser());
		Assert.assertNotSame(meeting1.getUsers(), meeting2.getUsers());
		Assert.assertEquals("", meeting5.getMeetingId());
		Assert.assertEquals("", meeting5.getName());
		Assert.assertNotNull(meeting5.getDate());
		Assert.assertNotNull(meeting6.getDate());
		Assert.assertNotNull(meeting6.getName());
	}

}
