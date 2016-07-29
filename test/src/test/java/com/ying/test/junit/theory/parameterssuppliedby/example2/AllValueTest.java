package com.ying.test.junit.theory.parameterssuppliedby.example2;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class AllValueTest {

	@Theory
	public void test(@AllValue String str) {
		
		System.out.println(str);
	}

}
