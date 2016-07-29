package com.ying.test.junit.theory;

import java.util.ArrayList;
import java.util.List;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import com.ying.test.User;

@RunWith(Theories.class)
public class AddTest2 {

	@DataPoints
	public static List<User> getParameters() {
		return new ArrayList<User>() {
			{
				add(new User(1L, "Ying"));
				add(new User(2L, "Ke"));
				add(new User(3L, "Sun"));
			}
		};

	}

	@Theory
	public void test(User user) {
		System.out.println(user);
	}
}
