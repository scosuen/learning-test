package com.ying.test.junit.theory.parameterssuppliedby.example1;

import static org.junit.Assert.assertTrue;

import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class BetweenTest {

	@Theory
	public final void test(@Between(last = 0) int i, 
			@Between(first = 3, last = 10) int j) {
		// i 取值为 0（first默认=0，last=0），j 取值为 3-10
		System.out.println("i: " + i + ", j: " + j);
		assertTrue(i + j >= 0);
	}

}
