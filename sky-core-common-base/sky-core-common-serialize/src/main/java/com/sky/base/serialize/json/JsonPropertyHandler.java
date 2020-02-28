package com.sky.base.serialize.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * @Title JSON属性处理器
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-10
 */
public interface JsonPropertyHandler {

    /**
     * 获取指定key的String类型取值
     * @param jsonText json文本
     * @param key 指定key
     * @return String类型取值
     */
    String getString(String jsonText, String key);

    /**
     * 获取指定key的Integer类型取值
     * @param jsonText json文本
     * @param key 指定key
     * @return Integer类型取值
     */
    Integer getInteger(String jsonText, String key);

    /**
     * 获取指定key的Long类型取值
     * @param jsonText json文本
     * @param key 指定key
     * @return Long类型取值
     */
    Long getLong(String jsonText, String key);

    /**
     * 获取指定key的Double类型取值
     * @param jsonText json文本
     * @param key 指定key
     * @return Double类型取值
     */
    Double getDouble(String jsonText, String key);

    /**
     * 获取指定key的BigInteger类型取值
     * @param jsonText json文本
     * @param key 指定key
     * @return BigInteger类型取值
     */
    BigInteger getBigInteger(String jsonText, String key);

    /**
     * 获取指定key的BigDecimal类型取值
     * @param jsonText json文本
     * @param key 指定key
     * @return BigDecimal类型取值
     */
    BigDecimal getBigDecimal(String jsonText, String key);

    /**
     * 获取指定key的Boolean类型取值
     * @param jsonText json文本
     * @param key 指定key
     * @return Boolean类型取值
     */
    Boolean getBoolean(String jsonText, String key);

    /**
     * 获取指定key的Byte类型取值
     * @param jsonText json文本
     * @param key 指定key
     * @return Byte类型取值
     */
    Byte getByte(String jsonText, String key);

    /**
     * 获取指定key的特定类型取值
     * @param jsonText json文本
     * @param key 指定key
     * @param specificClass 指定类型
     * @return 指定类型取值
     */
    <T> T getObject(String jsonText, String key, Class<T> specificClass);

    /**
     * 获取指定key的ArrayList类型取值
     * @param jsonText json文本
     * @param key 指定key
     * @param specificClass 指定类型
     * @return ArrayList类型取值
     */
    <T> List<T> getList(String jsonText, String key, Class<T> specificClass);

    /**
     * 添加属性
     * @param jsonText json文本
     * @param key 指定key
     * @param value 指定value
     * @return 添加属性后的json文本
     */
    <T> String addProperty(String jsonText, String key, T value);

    /**
     * 更新属性
     * @param jsonText json文本
     * @param key 指定key
     * @param value 新的value
     * @return 更新指定属性后的json文本
     */
    <T> String updateProperty(String jsonText, String key, T value);

    /**
     * 移除属性
     * @param jsonText json文本
     * @param key 指定key
     * @return 移除指定属性后的json文本
     */
    String removeProperty(String jsonText, String key);

}
