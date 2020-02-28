package com.sky.base.serialize.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.sky.base.serialize.json.fastjson.FastJsonHandler;

/**
 * @Title json工具类
 * @Description 基于com.alibaba.fastjson
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-07
 */
public final class JsonUtils {

    private static JsonHandler jsonHandler = new FastJsonHandler();

    /**
     * 解析json文本为指定类型对象
     * @param jsonText json文本
     * @param specificClass 指定类型
     * @return 解析后对象实例
     */
    public static <T> T parse(String jsonText, Class<T> specificClass) {
        return jsonHandler.parse(jsonText, specificClass);
    }

    /**
     * 指定类型对象转json文本
     * @param object 指定类型对象
     * @return json文本
     */
    public static <T> String toJsonString(T object) {
        return jsonHandler.toJsonString(object);
    }

    /**
     * 格式化/美化json文本
     * @param jsonText json文本
     * @return 格式化/美化后json文本
     */
    public static String format(String jsonText) {
        return jsonHandler.format(jsonText);
    }

    /**
     * 判断文本是否符合json规范
     * @param text
     * @return true表示符合json规范, false表示不符合json规范
     */
    public static boolean isJson(String text) {
        return jsonHandler.isJson(text);
    }

    /**
     * 获取指定key的String类型取值
     * @param jsonText json文本
     * @param key 指定key
     * @return String类型取值
     */
    public static String getString(String jsonText, String key) {
        return jsonHandler.getString(jsonText, key);
    }

    /**
     * 获取指定key的Integer类型取值
     * @param jsonText json文本
     * @param key 指定key
     * @return Integer类型取值
     */
    public static Integer getInteger(String jsonText, String key) {
        return jsonHandler.getInteger(jsonText, key);
    }

    /**
     * 获取指定key的Long类型取值
     * @param jsonText json文本
     * @param key 指定key
     * @return Long类型取值
     */
    public static Long getLong(String jsonText, String key) {
        return jsonHandler.getLong(jsonText, key);
    }

    /**
     * 获取指定key的Double类型取值
     * @param jsonText json文本
     * @param key 指定key
     * @return Double类型取值
     */
    public static Double getDouble(String jsonText, String key) {
        return jsonHandler.getDouble(jsonText, key);
    }

    /**
     * 获取指定key的BigInteger类型取值
     * @param jsonText json文本
     * @param key 指定key
     * @return BigInteger类型取值
     */
    public static BigInteger getBigInteger(String jsonText, String key) {
        return jsonHandler.getBigInteger(jsonText, key);
    }

    /**
     * 获取指定key的BigDecimal类型取值
     * @param jsonText json文本
     * @param key 指定key
     * @return BigDecimal类型取值
     */
    public static BigDecimal getBigDecimal(String jsonText, String key) {
        return jsonHandler.getBigDecimal(jsonText, key);
    }

    /**
     * 获取指定key的Boolean类型取值
     * @param jsonText json文本
     * @param key 指定key
     * @return Boolean类型取值
     */
    public static Boolean getBoolean(String jsonText, String key) {
        return jsonHandler.getBoolean(jsonText, key);
    }

    /**
     * 获取指定key的Byte类型取值
     * @param jsonText json文本
     * @param key 指定key
     * @return Byte类型取值
     */
    public static Byte getByte(String jsonText, String key) {
        return jsonHandler.getByte(jsonText, key);
    }

    /**
     * 获取指定key的特定类型取值
     * @param jsonText json文本
     * @param key 指定key
     * @param specificClass 指定类型
     * @return 指定类型取值
     */
    public static <T> T getObject(String jsonText, String key, Class<T> specificClass) {
        return jsonHandler.getObject(jsonText, key, specificClass);
    }

    /**
     * 获取指定key的ArrayList类型取值
     * @param jsonText json文本
     * @param key 指定key
     * @param specificClass 指定类型
     * @return ArrayList类型取值
     */
    public static <T> List<T> getList(String jsonText, String key, Class<T> specificClass) {
        return jsonHandler.getList(jsonText, key, specificClass);
    }

    /**
     * 添加属性
     * @param jsonText json文本
     * @param key 指定key
     * @param value 指定value
     * @return 添加属性后的json文本
     */
    public static <T> String addProperty(String jsonText, String key, T value) {
        return jsonHandler.addProperty(jsonText, key, value);
    }

    /**
     * 更新属性
     * @param jsonText json文本
     * @param key 指定key
     * @param value 新的value
     * @return 更新指定属性后的json文本
     */
    public static <T> String updateProperty(String jsonText, String key, T value) {
        return jsonHandler.updateProperty(jsonText, key, value);
    }

    /**
     * 移除属性
     * @param jsonText json文本
     * @param key 指定key
     * @return 移除指定属性后的json文本
     */
    public static String removeProperty(String jsonText, String key) {
        return jsonHandler.removeProperty(jsonText, key);
    }

    private JsonUtils() {
        throw new IllegalStateException("Utility class");
    }
}
