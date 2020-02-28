package com.sky.base.context.counter;

/**
 * @author zzq
 * @version 1.0.0
 * @Title {@link}
 * @Description 计数器
 * @date 2019/5/13
 */
public interface Counter {

	/**
	 * 是否间隔允许范围内
	 *
	 * @param key
	 * @return
	 */
	Boolean isAllowInterval(String key);

	/**
	 * 是否次数允许范围内
	 *
	 * @param key
	 * @return
	 */
	Boolean isAllowTimes(String key);

	/**
	 * 是否允许
	 *
	 * @param key
	 * @return
	 */
	Boolean isAvailable(String key);

	/**
	 * 累加
	 *
	 * @param key
	 * @return
	 */
	Boolean incr(String key);
}
