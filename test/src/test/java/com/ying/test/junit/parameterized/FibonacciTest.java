package com.ying.test.junit.parameterized;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class FibonacciTest {

	private int fInput;
	private int fExpected;

	public FibonacciTest(int input, int expected) {
		System.out.println("initial.");
		fInput = input;
		fExpected = expected;
	}

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { 0, 0 }, { 1, 1 }, { 2, 1 }, { 3, 2 }, { 4, 3 }, { 5, 5 }, { 6, 8 } });
	}

	@Test
	public void test() {
		System.out.println("FibonacciTest test.");
		assertEquals(fExpected, Fibonacci.compute(fInput));
	}
}
