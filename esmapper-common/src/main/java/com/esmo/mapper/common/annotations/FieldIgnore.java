package com.esmo.mapper.common.annotations;

import com.esmo.mapper.common.annotations.enums.IgnoreType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({})
public @interface FieldIgnore {
	Class[] types() default {};
	String[] 	packages() default {};
	String[] value();
	IgnoreType ignored() default IgnoreType.IGNORE_ALL;
}
