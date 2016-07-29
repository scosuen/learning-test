package com.ying.test.junit.theory.parameterssuppliedby.example2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.experimental.theories.ParametersSuppliedBy;

/**
 * 空接口，不需要接受任何参数变量
 * @author Ying
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@ParametersSuppliedBy(AllValueSupplier.class)
public @interface AllValue {

}
