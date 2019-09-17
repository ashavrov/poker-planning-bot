package test;

import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class Log4jTest {
	private static Logger log = LogManager.getLogger(Log4jTest.class);


	@Test
	public void testOne() throws Exception {
		log.info("INFO TEST");
		log.debug("DEBUG TEST");
		log.error("ERROR TEST");

		assertTrue(true);
	}

	@Test
	public void testTwo() throws Exception {
		log.info("INFO TEST");
		log.debug("DEBUG TEST");
		log.error("ERROR TEST");

		assertTrue(true);
	}
}
