package com.ying.test.junit;

import org.junit.Ignore;
import org.junit.Test;

public class TestAnnontation {

	@Test(timeout = 1000) // 脚本运行时间小于1000毫秒
	public void testTimeout() {
		try {
			Thread.sleep(1100);
			// Thread.sleep(900);
		} catch (InterruptedException e) {
		}
	}

	@Test(expected = IllegalArgumentException.class) // 脚本抛出参数非法的异常
	public void testExpectedThrowable() {
		// throw new IllegalArgumentException();
		throw new NullPointerException();
	}

	@Test
//	@Ignore
	@Ignore("not ready yet!")
	public void test() {
		System.out.println("ignore test case");
	}
}
