package com.sky.service.api.struct.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.sky.base.jdbc.spring.annotation.Table;

@Table(name = "SKY_API_ROUTE", primary = {"ID"})
public class SkyApiRoute implements Serializable {
	private static final long serialVersionUID = 5343017236944126860L;
	
	private Long id;
	private String routeCode;
	private Long routeGroup;
	private String routeProtocal;    //INTERN-RMI/OUTER-IBMMQ/OUTER-WS
   	private String routeUri;
   	private String available;  //0-可用，1-不可用
   	private Integer priority;   //优先级
   	private Timestamp createTime;
   	private Timestamp updateTime;
   	private String remark;  //备注
   	private String useDataCompress; //是否启用数据压缩发送：1-启用数据压缩发送 0-不启用数据压缩发送
   	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRouteCode() {
		return routeCode;
	}
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	public Long getRouteGroup() {
		return routeGroup;
	}
	public void setRouteGroup(Long routeGroup) {
		this.routeGroup = routeGroup;
	}
	public String getRouteProtocal() {
		return routeProtocal;
	}
	public void setRouteProtocal(String routeProtocal) {
		this.routeProtocal = routeProtocal;
	}
	public String getRouteUri() {
		return routeUri;
	}
	public void setRouteUri(String routeUri) {
		this.routeUri = routeUri;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
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
	public String getUseDataCompress() {
		return useDataCompress;
	}
	public void setUseDataCompress(String useDataCompress) {
		this.useDataCompress = useDataCompress;
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(available).append(routeCode).append(routeProtocal).append(routeUri).append(useDataCompress).toHashCode();
	}
	@Override
	public boolean equals(Object obj) {
		
		boolean equals = false;
		if (obj != null && SkyApiRoute.class.isAssignableFrom(obj.getClass())) {
			SkyApiRoute r = (SkyApiRoute) obj;
			equals = (new EqualsBuilder().append(available, r.available).append(routeCode, r.routeCode)
					.append(routeProtocal, r.routeProtocal).append(routeUri, r.routeUri)).append(useDataCompress, r.useDataCompress).isEquals();
		}
		return equals;
	}
	
	
}
