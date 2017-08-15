package com.UniSuper;

import org.apache.log4j.Logger;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	
	final static Logger logger = Logger.getLogger(AppTest.class);
	
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		logger.debug("This is log debug message");
		logger.warn("This is log warning message");
		logger.fatal("This is log fatel message");
		logger.info("This is log info message");
		
		assertTrue(true);
	}
}
