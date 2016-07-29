package com.ying.test.junit.theory.parameterssuppliedby.example2;

import java.util.ArrayList;
import java.util.List;

import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class AllValueSupplier extends ParameterSupplier {

	@Override
	public List<PotentialAssignment> getValueSources(ParameterSignature arg0) throws Throwable {
		
		List<PotentialAssignment> result = new ArrayList<PotentialAssignment>();
		
		for (int i = 0 ; i < 100 ; i ++) {
			result.add(PotentialAssignment.forValue("name", "Number:" + i));
		}
		return result;
	}

}
