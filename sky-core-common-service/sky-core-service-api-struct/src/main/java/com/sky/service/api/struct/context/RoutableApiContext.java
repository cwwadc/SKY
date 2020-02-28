package com.sky.service.api.struct.context;

import java.io.Serializable;

import com.sky.service.api.support.ApiContext;
import com.sky.service.api.support.ApiReplyChannelAdapter;

public class RoutableApiContext<T> extends ApiContext implements Serializable {
	private static final long serialVersionUID = 1740718699509283262L;
	
	private String couplerId;  //回调键
	private String routeCode;   // 路由键
	private T routeMsg; //路由信息
	private String useDataCompress; //启动数据压缩发送标识
	private ApiReplyChannelAdapter replyAdapter; //响应适配器
	
	public String getCouplerId() {
		return couplerId;
	}
	public void setCouplerId(String couplerId) {
		this.couplerId = couplerId;
	}
	public String getRouteCode() {
		return routeCode;
	}
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	public T getRouteMsg() {
		return routeMsg;
	}
	public void setRouteMsg(T routeMsg) {
		this.routeMsg = routeMsg;
	}
	public String getUseDataCompress() {
		return useDataCompress;
	}
	public void setUseDataCompress(String useDataCompress) {
		this.useDataCompress = useDataCompress;
	}
	public ApiReplyChannelAdapter getReplyAdapter() {
		return replyAdapter;
	}
	public void setReplyAdapter(ApiReplyChannelAdapter replyAdapter) {
		this.replyAdapter = replyAdapter;
	}
}
