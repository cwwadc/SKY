package com.sky.base.cache.redis.counter;

/**
 * 
 * @title 计数器统计单元
 * @description
 * @author zzq
 * @author lizp
 * @version 1.0.0
 * @date 2019-05-29
 */
class CountUnit {
	/**
	 * 记数间隔时间到期时间戳
	 */
	private Long intervalExpiresTime;
	/**
	 * 周期内频率时间到期时间戳
	 */
	private Long cycleFrequencyExpiresTime;
	/**
	 * 次数
	 */
	private Integer num;

	public CountUnit() {
		this.intervalExpiresTime = -1L;
		this.cycleFrequencyExpiresTime = -1L;
		this.num = 0;
	}

	public Long getIntervalExpiresTime() {
		return intervalExpiresTime;
	}

	public void setIntervalExpiresTime(Long intervalExpiresTime) {
		this.intervalExpiresTime = intervalExpiresTime;
	}

	public Long getCycleFrequencyExpiresTime() {
		return cycleFrequencyExpiresTime;
	}

	public void setCycleFrequencyExpiresTime(Long cycleFrequencyExpiresTime) {
		this.cycleFrequencyExpiresTime = cycleFrequencyExpiresTime;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	void addNum(Integer num) {
		this.num = this.num + num;
	}

	@Override
	public String toString() {
		return "CountUnit{" + "intervalExpiresTime=" + intervalExpiresTime + ", cycleFrequencyExpiresTime="
		        + cycleFrequencyExpiresTime + ", num=" + num + '}';
	}
}
