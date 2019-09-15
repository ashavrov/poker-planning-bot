package test;

import org.junit.Assert;
import org.junit.Test;

import entities.User;

public class UserTest {

	@Test
	public void testAllMethods() {
		User user1 = new User("123123", "23423423", "TestUser");
		User user2 = new User("123123", "23423423", "TestUser");
		User user3 = new User("234234", "34534534", "TestUser2");
		User user4 = new User("123123", "234234231", "TestUser");
		User user5 = new User("123123", "23423423", "TestUser3");
		User user6 = new User(null, null, null);
		User user7 = null;
		Assert.assertTrue(user1.hashCode() == user2.hashCode());
		Assert.assertTrue(user1.hashCode() != user3.hashCode());
		Assert.assertTrue(user1.getUserId().equals("123123"));
		Assert.assertTrue(user1.getChatId().equals("23423423"));
		Assert.assertTrue(user1.getName().equals("TestUser"));
		Assert.assertTrue(user1.equals(user2));
		Assert.assertTrue(!user1.equals(user3));
		Assert.assertTrue(!user1.equals(user4));
		Assert.assertTrue(!user1.equals(user5));
		Assert.assertTrue(!user3.equals(user5));
		Assert.assertTrue(!user1.equals(new Object()));
		Assert.assertTrue(!user1.equals(user7));
		Assert.assertTrue(user1 != null);
		Assert.assertTrue(user6.getChatId() != null);
		Assert.assertTrue(user6.getName() != null);
		Assert.assertTrue(user6.getUserId() != null);
	}

}
