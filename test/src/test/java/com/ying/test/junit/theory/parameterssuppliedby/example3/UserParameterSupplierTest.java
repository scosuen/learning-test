package com.ying.test.junit.theory.parameterssuppliedby.example3;

import java.util.ArrayList;
import java.util.List;

import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.ParametersSuppliedBy;
import org.junit.experimental.theories.PotentialAssignment;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import com.ying.test.User;

@RunWith(Theories.class)
public class UserParameterSupplierTest {

	private class UserParameterSupplier extends ParameterSupplier {
		@Override
		public List<PotentialAssignment> getValueSources(ParameterSignature arg0) throws Throwable {
			List<PotentialAssignment> list = new ArrayList<PotentialAssignment>();
			list.add(PotentialAssignment.forValue("name", new User(1L, "Ying")));
			list.add(PotentialAssignment.forValue("name", new User(2L, "Scott")));
			list.add(PotentialAssignment.forValue("name", new User(3L, "Sun")));
			return list;
		}

	}

	@Theory
	public void test(@ParametersSuppliedBy(UserParameterSupplier.class) User user) {
		System.out.println(user);
	}

}
