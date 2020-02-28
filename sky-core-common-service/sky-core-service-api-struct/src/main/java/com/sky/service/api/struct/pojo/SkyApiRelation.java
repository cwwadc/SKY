package com.sky.service.api.struct.pojo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.sky.base.jdbc.spring.annotation.Table;

@Table(name = "SKY_API_RELATION", primary = {"ID"})
public class SkyApiRelation implements Serializable {

	private static final long serialVersionUID = 2222093236723628957L;
	
	private Long id;   //主键ID
	private String requestType;   //请求交易类型
	private String requestComment;  //请求交易名称
	private String responseType;   //响应交易类型
	private String responseComment;   //响应交易名称
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getRequestComment() {
		return requestComment;
	}
	public void setRequestComment(String requestComment) {
		this.requestComment = requestComment;
	}
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public String getResponseComment() {
		return responseComment;
	}
	public void setResponseComment(String responseComment) {
		this.responseComment = responseComment;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).append(requestType).append(requestComment).append(responseType).append(responseComment).toHashCode();
	}
	@Override
	public boolean equals(Object obj) {
		
		boolean equals = false;
		if (obj != null && SkyApiRelation.class.isAssignableFrom(obj.getClass())) {
			SkyApiRelation r = (SkyApiRelation) obj;
			equals = (new EqualsBuilder().append(id, r.id).append(requestType, r.requestType)
					.append(requestComment, r.requestComment).append(responseType, r.responseType)).append(responseComment, r.responseComment).isEquals();
		}
		return equals;
	}
}
