package com.sky.core.serialize;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.sky.base.lang.ClassUtils;
import com.sky.base.serialize.xml.XmlUtils;
import com.sky.base.serialize.xml.strategy.DefaultXstreamStrategy;
import com.sky.core.utils.SkyApiMsgUtils;
import com.sky.service.api.exception.ApiException;
import com.sky.service.api.struct.domain.SkyMsg;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.XStream;

/**
 * 基于XML协议栈实现的Api序列化发生器
 *
 */
public class XmlBasedApiSerializer implements ApiSerializer<SkyMsg>, InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(XmlBasedApiSerializer.class);
	public static final String DOC_STATEMENT = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
	public static final String BASE_PACKAGE = "com.sky.service.api.struct.transaction";
	private Map<String, Class<?>> serializerTypeStore = new HashMap<String, Class<?>>();
	private Map<Class<?>, XStream> xstreamStore = new HashMap<Class<?>, XStream>();
	private XStream defaultXStream;

	@Override
	public String serialize(SkyMsg messageObject) {
		XStream xstream = null;
		if(messageObject.getClass().equals(SkyMsg.class) && SkyApiMsgUtils.isCommonReplyContext(messageObject)) {
			xstream = defaultXStream;
		} else {
			String msgType = messageObject.getHead().getMsgType();
			Class<?> serializeType = serializerTypeStore.get(msgType);
			xstream = xstreamStore.get(serializeType);
		}
		return new StringBuilder().append(DOC_STATEMENT).append(xstream.toXML(messageObject).replaceAll(">(\\s*|\\n|\\t*|\\r)<", "><")).toString();
	}

	@Override
	public SkyMsg deserialize(String messageText, Class<?> deserializeType) {
		SkyMsg msg = null;
		if(SkyApiMsgUtils.isCommonReplyContext(messageText)) {
			logger.debug("unpack common reply message ..");
			msg = (SkyMsg) defaultXStream.fromXML(messageText);
		} else if(deserializeType != null) {
			logger.debug("unpack message, deserialize-type -> {}", deserializeType.getName());
			msg = (SkyMsg) xstreamStore.get(deserializeType).fromXML(messageText);
		} else {
			throw new ApiException("cannot found deserialize type or unrecognized msg type, please check.");
		}
		return msg;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initialize();
	}

	@Override
	public boolean supportApi(String apiType) {
		return serializerTypeStore.containsKey(apiType);
	}

	@Override
	public Class<?> lookupDeserializeType(String apiType) {
		return serializerTypeStore.get(apiType);
	}
	
	public void initSerializerTypeStore() {
		List<Class<?>> classSet = ClassUtils.getPackageClasses(BASE_PACKAGE);
		for (Class<?> type : classSet) {
			ApiSerializeType apiSerializeType = type.getAnnotation(ApiSerializeType.class);
			if (apiSerializeType != null) {
				serializerTypeStore.put(apiSerializeType.value(), type);
			}
		}
	}
	
	public void initXStreamStore() {
		for (Map.Entry<String, Class<?>> apiEntry : serializerTypeStore.entrySet()) {
			Class<?> serializeType = apiEntry.getValue();
			if(xstreamStore.get(serializeType) == null) {
				XStream xStream = XmlUtils.getXStream(new DefaultXstreamStrategy());
		        xStream.processAnnotations(SkyMsg.class);
		        xStream.processAnnotations(serializeType);
		        xstreamStore.put(serializeType, xStream);
			}
		}
	}
	
	public void initDefaultXStream() {
		defaultXStream = XmlUtils.getXStream(new DefaultXstreamStrategy());
		defaultXStream.processAnnotations(SkyMsg.class);
	}
	
	public void initialize() {
		initSerializerTypeStore();
		initXStreamStore();
		initDefaultXStream();
	}

}
