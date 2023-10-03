package com.esmo.mapper.common.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
public @interface EsMapperGenerated {
    String value();
    String date() default "";
}
