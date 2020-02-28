package com.sky.core.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.fasterxml.uuid.UUIDGenerator;
import com.sky.base.context.spring.SpringContext;
import com.sky.base.lang.sequence.UUID;
import com.sky.base.lang.time.DateTimeUtils;
import com.sky.base.serialize.xml.DomUtils;
import com.sky.base.serialize.xml.XmlUtils;
import com.sky.core.serialize.XmlBasedApiSerializer;
import com.sky.service.api.exception.ApiException;
import com.sky.service.api.struct.constants.SkyMsgConstants;
import com.sky.service.api.struct.domain.SkyMsg;
import com.sky.service.api.struct.domain.SkyMsgHead;
import com.sky.service.api.struct.pojo.SkyApiRelation;

/**
 * 接口消息处理工具类
 *
 */
public final class SkyApiMsgUtils {
	private static final Logger logger = LoggerFactory.getLogger(SkyApiMsgUtils.class);
	
	/** 消息类型解析工具方法
	 * @param xmlPlainText 消息文本
	 * @return 消息类型
	 * @throws ApiException
	 */
	public static String unpackMsgType(String xmlPlainText) throws ApiException {
		return parseNodeFromXml(xmlPlainText, SkyMsgConstants.NODE_MSGTYPE);
	}
	
	/** 消息反序列化工具方法
	 * @param xmlPlainText 消息文本
	 * @param deserializeType 反序列化类型
	 * @return 消息对象
	 */
	public static SkyMsg unpackMsg(String xmlPlainText, Class<?> deserializeType) {
		if(deserializeType == null && !isCommonReplyContext(xmlPlainText)) {
			//反序列化类型为空时需判断是否是通用应答类报文（固定仅有报文头）
			throw new ApiException("cannot found deserialize type or unrecognized msg type, please check.");
		}
		if(deserializeType != null) {
			logger.debug("unpack message, deserialize-type -> {}", deserializeType.getName());
		}
		XmlBasedApiSerializer apiSerializer = SpringContext.getBean(XmlBasedApiSerializer.class);
		SkyMsg msg = apiSerializer.deserialize(xmlPlainText, deserializeType);
		return msg;
	}
	
	/** 消息序列化工具方法
	 * @param message 消息对象
	 * @return 消息文本
	 */
	public static String toPlainXml(SkyMsg message) {
		XmlBasedApiSerializer apiSerializer = SpringContext.getBean(XmlBasedApiSerializer.class);
		return apiSerializer.serialize(message);
	}
	

	/** 是否通用应答类消息
	 * @param message 消息对象
	 * @return true-应答类消息, false-非应答类消息
	 */
	public static boolean isCommonReplyContext(SkyMsg message) {
		return message.getBody() == null || message.getBody().isEmpty();
	}
	
	/** 是否通用应答类消息
	 * @param message 消息对象
	 * @return true-应答类消息, false-非应答类消息
	 */
	public static boolean isCommonReplyContext(String message) {
		return message.indexOf("<Body>") < 0;
	}
	
	public static String parseNodeFromXml(String xml, String nodename){
		String nodebegin = "<" + nodename + ">", nodeend = "</" + nodename + ">";
		int begin = xml.indexOf(nodebegin), end = xml.indexOf(nodeend);
		if (begin == -1 || end == -1 || begin >= end)
			return null;
		begin += nodebegin.length();
		return xml.substring(begin, end);
	}

}
