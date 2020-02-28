package com.sky.base.context.util;

import java.util.Arrays;

import org.slf4j.Logger;

/**
 * 
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-04-23
 */
public class SimpleStopWatch {

	private final Logger logger;

	private String format;

	private Object[] arguments;

	private long startTime;

	public SimpleStopWatch(Logger logger) {
		super();
		this.logger = logger;
	}

	public void start(String format, Object... arguments) {
		this.startTime = System.currentTimeMillis();
		this.format = format;
		this.arguments = arguments;
		logger.info(format, arguments);
	}

	public void stop() {
		Object[] objects = Arrays.copyOf(arguments, arguments.length + 1);
		objects[objects.length - 1] = System.currentTimeMillis() - startTime;
		logger.info(format + " | Done {} ms", objects);
	}

}
