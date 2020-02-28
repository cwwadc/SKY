package com.sky.core.service.api.route;

import com.sky.core.service.api.serialize.ApiCompressFactory;

/**
  * 路由发送适配器抽象
 *
 */
public abstract class ApiRouteAware<T, K> {
	protected String awareName;
	protected Boolean syncReturn = Boolean.TRUE;   //是否支持同步
	protected ApiCompressFactory compressFactory;  //数据压缩工厂
    public String getAwareName() {
		return awareName;
	}
	public void setAwareName(String awareName) {
		this.awareName = awareName;
	}
	public Boolean getSyncReturn() {
		return syncReturn;
	}
	public void setSyncReturn(Boolean syncReturn) {
		this.syncReturn = syncReturn;
	}
	public ApiCompressFactory getCompressFactory() {
		return compressFactory;
	}
	public void setCompressFactory(ApiCompressFactory compressFactory) {
		this.compressFactory = compressFactory;
	}
	public abstract K deliver(T deliverContent, String useDataCompress);    //转发方法，内容类型与方法返回类型由各个具体转发协议组件实现
	public abstract Boolean isRemoteEndReachable();   //远端服务是否可达，由各个具体转发协议组件实现，true-远端服务可达，false-远端服务不可达
	public void initialize() {};
	public void destory() {}
	
}
