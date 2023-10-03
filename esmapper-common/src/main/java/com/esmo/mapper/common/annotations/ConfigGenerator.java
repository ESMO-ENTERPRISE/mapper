package com.esmo.mapper.common.annotations;

import com.esmo.mapper.common.annotations.enums.ConfigErrorReporting;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({})
public @interface ConfigGenerator {
	Class[] fieldPackages() default {};
	Class[] fieldTypes() default {};
	String[] field() default "";
	ConfigErrorReporting missingAsSource() default ConfigErrorReporting.WARNINGS_ONLY;
	ConfigErrorReporting missingAsDestination() default ConfigErrorReporting.WARNINGS_ONLY;
}
