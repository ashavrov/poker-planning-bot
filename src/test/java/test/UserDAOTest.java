package test;

import org.junit.Test;
import org.junit.Assert;

import dao.UserDAO;
import entities.User;

public class UserDAOTest {

	@Test
	public void testUserDAO() {
		UserDAO userDAO = new UserDAO();
		Assert.assertNotNull(userDAO);
	}

	@Test
	public void testInsertUser() {
		UserDAO userDAO = new UserDAO();
		User user = new User("123", "123", "Test");
		userDAO.insert(user);
		Assert.assertTrue(user.equals(userDAO.getById("123")));
		userDAO.delete(user);
		Assert.assertNull(userDAO.getById("123"));
	}

	@Test
	public void testUpdateUser() {
		UserDAO userDAO = new UserDAO();
		User user = new User("234", "234", "Test2");
		userDAO.insert(user);
		Assert.assertTrue(user.equals(userDAO.getById("234")));
		user.setName("Test3");
		userDAO.update(user);
		Assert.assertTrue(user.equals(userDAO.getById("234")));
		Assert.assertTrue(userDAO.getById("234").equals(userDAO.getByName("Test3")));
		Assert.assertNull(userDAO.getById("0987"));
		Assert.assertNull(userDAO.getByName("noUser"));
		userDAO.delete(user);
		Assert.assertNull(userDAO.getById("123"));
	}

	@Test
	public void testGetAll() {
		UserDAO userDAO = new UserDAO();
		Assert.assertNotNull(userDAO.getAll().isEmpty());
	}

}
