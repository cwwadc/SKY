package com.sky.service.api.struct.context;

import java.io.Serializable;

public class FailRetryableApiContext<T> extends RoutableApiContext<T> implements Serializable {
	private static final long serialVersionUID = 590255089124803789L;
	
	private String failRetryAvaiable;   //是否应用失败重试机制：0-是 1-否
	private Long failRetryTimeLimit;    //重试周期限制，单位：分钟，即首次路由失败后在多少分钟内执行失败重试
	private Long failRetryInterval;     //重试间隔，单位：分钟，即首次路由失败后每隔多少分钟执行重试
	private Integer totalRetryCount;    //当前累计重试次数
	private Long retryActivateTime;     //进入重试队列时间
	private Long nextTime;              //下次计划重试时间
	private Long completeTime;          //重试完成（成功）时间
	private Long retryExpireTime;       //重试退出时间
	
	public String getFailRetryAvaiable() {
		return failRetryAvaiable;
	}
	public void setFailRetryAvaiable(String failRetryAvaiable) {
		this.failRetryAvaiable = failRetryAvaiable;
	}
	public Long getFailRetryTimeLimit() {
		return failRetryTimeLimit;
	}
	public void setFailRetryTimeLimit(Long failRetryTimeLimit) {
		this.failRetryTimeLimit = failRetryTimeLimit;
	}
	public Long getFailRetryInterval() {
		return failRetryInterval;
	}
	public void setFailRetryInterval(Long failRetryInterval) {
		this.failRetryInterval = failRetryInterval;
	}
	public Integer getTotalRetryCount() {
		return totalRetryCount;
	}
	public void setTotalRetryCount(Integer totalRetryCount) {
		this.totalRetryCount = totalRetryCount;
	}
	public Long getNextTime() {
		return nextTime;
	}
	public void setNextTime(Long nextTime) {
		this.nextTime = nextTime;
	}
	public Long getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Long completeTime) {
		this.completeTime = completeTime;
	}
	public Long getRetryActivateTime() {
		return retryActivateTime;
	}
	public void setRetryActivateTime(Long retryActivateTime) {
		this.retryActivateTime = retryActivateTime;
	}
	public Long getRetryExpireTime() {
		return retryExpireTime;
	}
	public void setRetryExpireTime(Long retryExpireTime) {
		this.retryExpireTime = retryExpireTime;
	}
}
