package com.ying.test.junit.category;

import org.junit.Test;
import org.junit.experimental.categories.Category;


public class TestOne {

	@Test
	public void testOne1() {
		System.out.println("TestOne 1");
	}

	@Category({ Category1.class })
	@Test
	public void testOne2() {
		System.out.println("TestOne 2");
	}
}
