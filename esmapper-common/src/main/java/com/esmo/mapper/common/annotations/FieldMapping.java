package com.esmo.mapper.common.annotations;

import com.esmo.mapper.common.annotations.enums.ApplyFieldStrategy;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({})
public @interface FieldMapping {
	String[] sPackages() default {};
	Class[] sTypes() default {};
	String[] s();

	String[] dPackages() default {};
	Class[] dTypes() default {};
	String[] d();

	boolean ignoreDirectionS2D() default false;
	boolean ignoreDirectionD2S() default false;

	String methodNameS2D() default "";
	String methodNameD2S() default "";

	// Features for not null
	ApplyFieldStrategy[] applyWhenS2D() default {};
	ApplyFieldStrategy[] applyWhenD2S() default {};
}
