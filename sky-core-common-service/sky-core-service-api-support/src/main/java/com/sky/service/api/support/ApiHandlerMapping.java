package com.sky.service.api.support;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiHandlerMapping {
	String[] value() default "";
	boolean defaultHandler() default false;
	boolean errorHandler() default false;
	
}
