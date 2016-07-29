package com.ying.test.junit.category;

import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TestTwo {
	@Test
	public void testTwo1() {
		System.out.println("TestTwo 1");
	}

	@Category(Category1.class)
	@Test
	public void testTwo2() {
		System.out.println("TestTwo 2");
	}
}
