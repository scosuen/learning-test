package com.ying.test.junit.parameterized;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MatchUtilsTest {

	private int numberA;
	private int numberB;
	private int expected;

	public MatchUtilsTest(int numberA, int numberB, int expected) {
		this.numberA = numberA;
		this.numberB = numberB;
		this.expected = expected;
	}

	@Parameters(name = "{index}: testAdd({0}+{1}) = {2}")
	public static Collection parameters() {
		return Arrays.asList(new Object[][] { { 1, 1, 2 }, { 2, 2, 4 }, { 8, 2, 10 }, { 4, 5, 9 }, { 5, 5, 10 } });
	}

	@Test
	public void testAdd() {
		assertEquals(expected, MatchUtils.add(numberA, numberB));
		assertThat(MatchUtils.add(numberA, numberB), is(expected));
	}

}
