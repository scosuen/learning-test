package com.ying.test.junit.parameterized;

public class PrimeNumberChecker {

	public Boolean validate(Integer primeNumber) {
		for (int i = 2; i < (primeNumber / 2); i++) {
			if (primeNumber % i == 0) {
				return false;
			}
		}
		return true;
	}

}
