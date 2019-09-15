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
		Meeting meeting4 = meeting1;
		Meeting meeting5 = new Meeting(null, null, null);
		Meeting meeting6 = new Meeting(null, null);
		Meeting meeting7 = null;
		Assert.assertTrue(meeting1.hashCode() == meeting4.hashCode());
		Assert.assertTrue(meeting1.hashCode() != meeting2.hashCode());
		Assert.assertTrue(meeting1.equals(meeting4));
		Assert.assertTrue(!meeting1.equals(meeting2));
		Assert.assertTrue(!meeting2.equals(meeting3));
		Assert.assertTrue(!meeting1.equals(meeting7));
		Assert.assertTrue(meeting1 != null);
		Assert.assertTrue(!meeting1.equals(new Object()));
		Assert.assertTrue(meeting1.getName().equals("Meeting1"));
		Assert.assertTrue(meeting1.getDate().equals(date));
		Assert.assertTrue(!meeting3.getDate().equals(new Date()));
		User user1 = new User("123123", "23423423", "TestUser");
		User user2 = new User("234234", "34534534", "TestUser2");
		meeting1.addUser(user1);
		meeting1.addUser(user2);
		meeting2.addUser(user1);
		meeting1.setPrimaryUser(user1);
		meeting2.setPrimaryUser(user1);
		Assert.assertTrue(meeting1.getPrimaryUser() == meeting2.getPrimaryUser());
		Assert.assertTrue(meeting1.getUsers() != meeting2.getUsers());
		Assert.assertTrue(meeting5.getMeetingId().equals(""));
		Assert.assertTrue(meeting5.getName().equals(""));
		Assert.assertTrue(meeting5.getDate() != null);
		Assert.assertTrue(meeting6.getDate() != null);
		Assert.assertTrue(meeting6.getName() != null);
	}

}
