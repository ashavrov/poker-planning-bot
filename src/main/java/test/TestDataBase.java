package test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import utilities.DataBase;

public class TestDataBase {
	DataBase dataBase = new DataBase();

	@Before
	public void testConnect() {
		dataBase.init();
		Assert.assertNotNull(dataBase);
		Assert.assertEquals(1, dataBase.status);
	}

	@Test
	public void testGetUserNew() {
		Assert.assertNotNull(dataBase.getUser(123, (long) 123, "Тест"));
		dataBase.removeUser(123);
	}

	@Test
	public void testGetUserOld() {
		Assert.assertNotNull(dataBase.getUser(234, (long) 234, "Тест"));
		Assert.assertNotNull(dataBase.getUser(234, (long) 234, "Тест"));
		dataBase.removeUser(234);
	}

	@After
	public void testCloseConnect() {
		dataBase.close();
		Assert.assertEquals(0, dataBase.status);
	}
}
