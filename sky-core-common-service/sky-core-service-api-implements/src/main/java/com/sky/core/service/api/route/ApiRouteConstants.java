package com.sky.core.service.api.route;

/**
  * 路由常量类
 */
public class ApiRouteConstants {
	public static final String ROUTE_GROUP_HTTP_REALTIME_TRADE = "GROUP_HTTP_REALTIME_TRADE";   //税局实时交易路由组
	public static final String ROUTE_GROUP_HTTP_ASYNC_TRADE = "GROUP_HTTP_ASYNC_TRADE";      //税局异步交易路由组
	public static final String ROUTE_GROUP_MQ_NOTIFY_TRADE = "GROUP_MQ_NOTIFY_TRADE";   //ETS通知交易路由组
	public static final String ROUTE_GROUP_MQ_REALTIME_TRADE = "GROUP_MQ_REALTIME_TRADE";  //ETS实时交易请求路由组
	public static final String ROUTE_GROUP_MQ_REALTIME_RT_TRADE = "GROUP_MQ_REALTIME_RT_TRADE";    //ETS实时交易应答路由组
	public static final String ROUTE_GROUP_MQ_REPLY_TRADE = "GROUP_MQ_REPLY_TRADE";  //税局实时交易应答路由组
	
	public static final String ROUTE_GROUP_FAILRETRY_AVAIABLE = "0"; //是否应用失败重试机制：0-是
	public static final String ROUTE_GROUP_FAILRETRY_UNAVAIABLE = "1"; //是否应用失败重试机制：1-否
	
	public static final String ROUTE_PROTOCAL_INTERN_RMI = "PROTOCAL_INTERN-RMI";    //路由协议定义：内部rmi调用协议
	public static final String ROUTE_PROTOCAL_OUTER_IBMMQ = "PROTOCAL_OUTER-IBMMQ";  //路由协议定义：IBM-MQ协议
	public static final String ROUTE_PROTOCAL_OUTER_WS = "PROTOCAL_OUTER-WS";    //路由协议定义：WS协议
	
	public static final String ROUTE_STATUS_AVAIABLE = "0";   //路由状态：0-可用
	public static final String ROUTE_STATUS_UNAVAIABLE = "1";  //路由状态：1-不可用
	
	public static final String ROUTE_GROUP_STATUS_AVAIABLE = "0";   //路由组状态：0-可用
	public static final String ROUTE_GROUP_STATUS_UNAVAIABLE = "1";  //路由组状态：1-不可用
	
	public static final String ROUTE_UNUSE_DATA_COMPRESS = "0";  //0-不启用数据压缩发送
	public static final String ROUTE_USE_DATA_COMPRESS = "1";  //1-启用数据压缩发送
	
}
