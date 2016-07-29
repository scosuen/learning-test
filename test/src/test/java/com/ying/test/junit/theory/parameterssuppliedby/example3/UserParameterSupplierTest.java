package com.ying.test.junit.theory.parameterssuppliedby.example3;

import org.junit.experimental.theories.ParametersSuppliedBy;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class UserParameterSupplierTest {

	@Theory
	public void test(@ParametersSuppliedBy(UserParameterSupplier.class) User user) {
		System.out.println(user);
	}

}
