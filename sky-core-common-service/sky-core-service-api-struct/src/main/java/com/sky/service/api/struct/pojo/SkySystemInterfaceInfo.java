package com.sky.service.api.struct.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.sky.base.jdbc.spring.CoreDao;
import com.sky.base.jdbc.spring.annotation.Table;


/**
 * 系统接口调用信息实体
 *
 */
@Table(name = "SKY_SYSTEM_INTERFACE_INFO", primary = {"ID"})
public class SkySystemInterfaceInfo implements Serializable {
	private static final long serialVersionUID = 3395613514615567621L;
	
	private Long id;
	private String interfaceName;
	private String timeConsuming;
	private String isSusuccess;
	private String reserved;
	private Timestamp createTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getTimeConsuming() {
		return timeConsuming;
	}
	public void setTimeConsuming(String timeConsuming) {
		this.timeConsuming = timeConsuming;
	}
	public String getIsSusuccess() {
		return isSusuccess;
	}
	public void setIsSusuccess(String isSusuccess) {
		this.isSusuccess = isSusuccess;
	}
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	
}
