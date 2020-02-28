package com.sky.base.context.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sky.base.lang.constant.BooleanConstants;
import com.sky.base.lang.string.StringUtils;

/**
 * @Title Main.
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-13
 */
public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	/** 版本环境变量key : ursa.env */
	public static final String ENV_KEY = "ursa.env";
	/** Spring配置环境变量key : ursa.spring.config */
	public static final String SPRING_CONFIG_KEY = "ursa.spring.config";
	/** ShutdownHook开关环境变量key : ursa.env */
	public static final String SHUTDOWN_HOOK_KEY = "ursa.shutdown.hook";

	/** 版本环境变量值 release : 正式版本 */
	public static final String ENV_RELEASE = "release";
	/** 版本环境变量值 dev : 开发版本 */
	public static final String ENV_DEV = "dev";

	public static void main(String[] args) {
		new Main().launch();
	}

	public void launch() {
		checkEnv();
		LOGGER.info("Main starting");
		AbstractApplicationContext applicationContext = null;
		try {
			applicationContext = customApplicationContext();
			registerShutdownHook(applicationContext);
			LOGGER.info("Main started");
		} catch (Exception e) {
			LOGGER.error("Main start fail", e);
			if (applicationContext != null) {
				applicationContext.close();
			}
		}
	}

	protected String checkEnv() {
		String env = System.getProperty(ENV_KEY);
		if (StringUtils.isBlank(env)) {
			LOGGER.error("Please enter environment parameter, -D{}={} or -D{}={}", ENV_KEY, ENV_DEV, ENV_KEY,
					ENV_RELEASE);
			System.exit(1);
		}
		if (!StringUtils.equalsIgnoreCase(env, ENV_DEV) && !StringUtils.equalsIgnoreCase(env, ENV_RELEASE)) {
			LOGGER.error("Invalid environment parameter");
			System.exit(1);
		}
		LOGGER.info("Use profile edition --> {}", env);
		return env;
	}

	protected AbstractApplicationContext customApplicationContext() throws Exception {
		String springConfig = System.getProperty(SPRING_CONFIG_KEY);
		if (StringUtils.isBlank(springConfig)) {
			LOGGER.error("Please enter spring config parameter, -D{}=xxx.xml", SPRING_CONFIG_KEY);
			System.exit(1);
		}
		LOGGER.info("Use Spring config --> {}", springConfig);

		return new ClassPathXmlApplicationContext(springConfig);
	}

	protected void registerShutdownHook(AbstractApplicationContext applicationContext) {
		if (BooleanConstants.TRUE
				.equals(StringUtils.defaultIfBlank(System.getProperty(SHUTDOWN_HOOK_KEY), BooleanConstants.TRUE))) {
			applicationContext.registerShutdownHook();
		}
	}

}
