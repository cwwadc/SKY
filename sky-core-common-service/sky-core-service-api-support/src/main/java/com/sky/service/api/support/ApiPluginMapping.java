package com.sky.service.api.support;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiPluginMapping {
	ApiPluginTypeEnum type();
	int order();
}
