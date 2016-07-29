package com.ying.test.junit.rule;

import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class ExampleTest {
	
	@Rule
	public MethodNameExample m = new MethodNameExample();

	@Rule
	public Timeout timeout = new Timeout(1000); // 使用Timeout这个 Rule，

	@Test
	public void testMethod1() throws Exception {
//		Thread.sleep(2000);
		// your tests
	}

	@Test
	public void testMethod2() throws Exception {
		// your tests2
	}

	// other test methods
}
