package com.sky.base.lang;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import org.apache.commons.lang3.StringUtils;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-05-05
 */
public class RuntimeUtils {

	/**
	 * 获取进程id，runtimeMXBean.getName()取得的值包括两个部分：PID和hostname，两者用@连接
	 * 
	 * @return
	 */
	public static String getProcessId() {
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		return StringUtils.split(runtimeMXBean.getName(), "@")[0];
	}

}
