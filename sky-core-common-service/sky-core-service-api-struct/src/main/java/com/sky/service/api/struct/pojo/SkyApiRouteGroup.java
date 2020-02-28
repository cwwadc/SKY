package com.sky.service.api.struct.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.sky.base.jdbc.spring.annotation.Table;

@Table(name = "SKY_API_ROUTE_GROUP", primary = {"ID"})
public class SkyApiRouteGroup implements Serializable {
	private static final long serialVersionUID = 5343017236944126860L;
	
	private Long id;
	private String groupName;
	private String useLoadBalance;
	private String loadBalancePolicy;
	private String available;
	private Timestamp createTime;
   	private Timestamp updateTime;
   	private String remark;  //备注
   	private String failRetryAvaiable;  //是否应用失败重试机制：0-不适用 1-是 2-否
   	private Long failRetryTimeLimit;  //重试周期限制，单位：分钟，即首次路由失败后在多少分钟内执行失败重试
   	private Long failRetryInterval;  //重试间隔，单位：分钟，即首次路由失败后每隔多少分钟执行重试
   	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getUseLoadBalance() {
		return useLoadBalance;
	}
	public void setUseLoadBalance(String useLoadBalance) {
		this.useLoadBalance = useLoadBalance;
	}
	public String getLoadBalancePolicy() {
		return loadBalancePolicy;
	}
	public void setLoadBalancePolicy(String loadBalancePolicy) {
		this.loadBalancePolicy = loadBalancePolicy;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
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
	
	
}
