package com.ying.test.junit;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

public class AssumeTest {

	@Before
	public void setUp() {
		String versionNumber = "7";
		Assume.assumeTrue(Integer.valueOf(versionNumber) == 5);
	}

	@Test
	public void test() {
		System.out.println("begin");
		String versionNumber = "7";
//		Assume.assumeTrue(Integer.valueOf(versionNumber) == 5);
		System.out.println("Test executed");
	}
}
