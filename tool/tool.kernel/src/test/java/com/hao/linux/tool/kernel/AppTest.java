package com.hao.linux.tool.kernel;

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
		String content = FileUtils.readFile("/proc/net/dev");
		System.out.println(content);
		
		content = ShellUtils.ecuteResult("ifconfig");
		System.out.println(content);
	}
	
	public void testNumber() {
		System.out.println(1<<10);
		System.out.println(1<<20);
		System.out.println(1<<30);
	}

}
