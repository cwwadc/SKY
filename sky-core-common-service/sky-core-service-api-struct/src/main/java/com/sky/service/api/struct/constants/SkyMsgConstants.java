package com.sky.service.api.struct.constants;

/**
 * 系统报文消息常量类
 *
 */
public class SkyMsgConstants {
	
	/**
	 * 常量：报文类型节点名称
	 */
	public static final String NODE_MSGTYPE = "MsgType";
	
	/**
	 * 常量：报文类型XPATH路径
	 */
	public static final String XPATH_MSGTYPE = "/Message/Head/MsgType";
	
	/**
	 * 常量：渠道返回超时应答报文
	 */
	public static final String MSG_TYPE_FEEDBACK_TIMEOUT_REPLAY = "FeedbackTimeoutReplayMsg";
	
    /**
     * 常量：系统异常应答报文
     */
    public static final String MSG_TYPE_SYSTEM_EXCEPTION_REPLAY = "SystemExceptionReplayMsg";
    
    /**
     * 常量：请求接收完成应答报文（多用于异步类、批量类交易）
     */
    public static final String MSG_TYPE_REQUEST_ACCEPTED_REPLAY = "RequestAcceptCompletedReplayMsg";
    
}
