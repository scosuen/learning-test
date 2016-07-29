
package com.ying.test.junit.theory;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class AddTest {
	
	@BeforeClass
	public static void init () {
		System.out.println("before class");
	}

	@DataPoints
	public static int[] getParameters() {
		return new int[] { 2, 3, 5, 9, 10, Integer.MAX_VALUE, Integer.MIN_VALUE };
	}

	@Theory
	public void test(int a, int b) {
		System.out.println("test:" + a + ", " + b);
		assertThat(a + b, is(MyCalculator.add(a, b)));
	}

	@Theory
	public void test2(int a, int b) {
		System.out.println("test2:" + a + ", " + b);
		assertTrue(MyCalculator.add(a, b) > a);
		assertTrue(MyCalculator.add(a, b) > b);
	}

	@Theory
	public void addition_is_commutative(Integer a, Integer b) {
		assertTrue(MyCalculator.add(a, b) == MyCalculator.add(b, a));
	}
}
