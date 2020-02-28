package com.sky.base.lang.task;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class DelayedTaskManagerTest {
	private static final Logger logger = LoggerFactory.getLogger(DelayedTaskManagerTest.class);
	private DelayedTaskManager taskManager;
	
	@Before
	public void initManager() {
		try {
			taskManager = new DelayedTaskManager("TestManager", Executors.newCachedThreadPool());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void registerTest() {
		try {
			taskManager.initialize();
			for (int i = 0; i < 10; i++) {
				DelayedTask<Runnable> task = new DelayedTask<Runnable>(String.valueOf(RandomUtils.nextLong()),
						new Runnable() {
							@Override
							public void run() {
								logger.info("execute task now...");
							}

						}, 10*(i+1), TimeUnit.SECONDS);
				taskManager.register(task);
			}
			Thread.sleep(1000 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
