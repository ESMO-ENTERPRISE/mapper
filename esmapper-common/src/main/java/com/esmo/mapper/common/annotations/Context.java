package com.esmo.mapper.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.PARAMETER})
public @interface Context {
	String value() default "ctx";

	String esMapperConfig = "#ESMAPPER#conf";
	String esMapperContext = "#ESMAPPER#ctx";
}
