package com.ying.test.junit.theory;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;

import org.junit.Assume;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;

@RunWith(value = Theories.class)
public class DivideTest {

	private static final double DELTA = 0.001;

	/*
	 * @DataPoint public static int zero = 0;
	 * 
	 * @DataPoint public static int Two = 2;
	 * 
	 * @DataPoint public static int three = 3;
	 * 
	 * @DataPoint public static int eight = 8;
	 */

	@DataPoints
	public static int[] getParameters() {
		return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
	}

	@Theory
	public void test(int dividend, int divisor) {
		// 跳过除数为0的case
		Assume.assumeThat(divisor, not(0));
		assertEquals(dividend / divisor, MyCalculator.divide(dividend, divisor), DELTA);
		System.out.println("test Passed with: dividend=" + dividend + ", divisor=" + divisor);
	}
	
	@Theory
	public void Test3 (int para) {
		System.out.println("test3 : " + para);
	}

	@Theory
	public void test2(@TestedOn(ints = { 0, 5 }) int dividend, 
			          @TestedOn(ints = { 5, 6 }) int divisor) {
		// 跳过除数为0的case
		assertEquals(dividend / divisor, MyCalculator.divide(dividend, divisor), DELTA);
		System.out.println("test 2 Passed with: dividend=" + dividend + ", divisor=" + divisor);
	}
}
