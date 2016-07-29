package com.ying.test.junit.runner;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class LoggingRunner extends BlockJUnit4ClassRunner {

	public LoggingRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	@Override
	protected void runChild(FrameworkMethod method, RunNotifier notifier) {
		System.out.println(method.getName());
		super.runChild(method, notifier);
	}

}
