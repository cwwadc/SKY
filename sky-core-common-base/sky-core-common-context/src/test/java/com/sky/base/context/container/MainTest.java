package com.sky.base.context.container;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;

import com.sky.base.context.container.Main;
import com.sky.base.context.spring.MockBean;
import com.sky.base.context.spring.SpringContext;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-07
 */
@Ignore
public class MainTest {

	public static void start(String springConfig) {
		System.setProperty(Main.ENV_KEY, "dev");
		System.setProperty(Main.SPRING_CONFIG_KEY, springConfig);
		Main.main(null);
	}

	@Test
	public void testClassPathXmlApplicationContextOfImport() {
		start("com/sky/base/context/container/MainTest.xml");
		MockBean mockBean = SpringContext.getBean("mockBean");
		assertNotNull(mockBean);
		assertEquals("test", mockBean.getName());
	}

	@Test
	public void testClassPathXmlApplicationContextOfScan() {
		start("com/sky/base/context/container/MainTestOfScan.xml");
		MockBean mockBean = SpringContext.getBean("mockBean");
		assertNotNull(mockBean);
		assertEquals("test", mockBean.getName());
	}

}
