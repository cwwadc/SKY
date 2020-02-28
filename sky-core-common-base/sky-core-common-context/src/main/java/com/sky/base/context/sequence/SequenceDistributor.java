package com.sky.base.context.sequence;

/**
 * 
 * @Title 序列号派发器
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-03-09
 */
public interface SequenceDistributor {

	/**
	 * 获取当前值
	 * 
	 * @param 序列名称
	 * @return 当前序列值, 返回null时表示获取失败
	 */
	Long current(String name);

	/**
	 * 获取下一个值
	 * 
	 * @param 序列名称
	 * @return 下一个序列值, 返回null时表示获取失败
	 */
	Long next(String name);

	/**
	 * 获取下一个值
	 * 
	 * @param name     序列名称
	 * @param stepSize 步长
	 * @return 下一个序列值, 返回null时表示获取失败
	 */
	Long next(String name, int stepWidth);

}
