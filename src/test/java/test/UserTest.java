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
        Assert.assertEquals(user1.hashCode(), user2.hashCode());
		Assert.assertTrue(user1.hashCode() != user3.hashCode());
        Assert.assertEquals("123123", user1.getUserId());
        Assert.assertEquals("23423423", user1.getChatId());
        Assert.assertEquals("TestUser", user1.getName());
        Assert.assertEquals(user1, user2);
        Assert.assertNotEquals(user1, user3);
        Assert.assertNotEquals(user1, user4);
        Assert.assertNotEquals(user1, user5);
        Assert.assertNotEquals(user3, user5);
        Assert.assertNotEquals(user1, new Object());
        Assert.assertNotEquals(null, user1);
        Assert.assertNotNull(user6.getChatId());
        Assert.assertNotNull(user6.getName());
        Assert.assertNotNull(user6.getUserId());
	}

}
