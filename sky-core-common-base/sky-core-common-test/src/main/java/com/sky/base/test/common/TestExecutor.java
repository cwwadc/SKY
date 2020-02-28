package com.sky.base.test.common;

/**
 * @Title 测试执行接口
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-03-20
 */
@FunctionalInterface
public interface TestExecutor {

	/**
	 * 执行
	 */
	void execute() throws Exception;

}
