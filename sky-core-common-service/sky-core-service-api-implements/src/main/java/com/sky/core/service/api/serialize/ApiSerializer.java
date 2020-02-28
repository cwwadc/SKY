package com.sky.core.service.api.serialize;

/**
 * Api序列化发生器接口
 */
public interface ApiSerializer<T> {
	String serialize(T messageObject);
	
	T deserialize(String messageText, Class<?> deserializeType);
	
	boolean supportApi(String apiType);
	
	Class<?> lookupDeserializeType(String apiType);
}
