package com.ying.test.junit;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class AssertThatTest {

	@Test
	public void test() {
		assertThat("sun", is("sun"));
		assertThat("sun", not("sun1"));
		assertThat("sun", anything());
		assertThat(5, anyOf(is(3), is(5)));
		assertThat("4", allOf(notNullValue()));
		assertThat("4", anyOf(notNullValue(), nullValue()));
//		assertThat(5, greaterThan(1));
		
	}

}
