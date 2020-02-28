package com.sky.base.serialize.json;

/**
 * @Title JSON处理器
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-10
 */
public interface JsonHandler extends JsonPropertyHandler {

    /**
     * 解析json文本为指定类型对象
     * @param jsonText json文本
     * @param specificClass 指定类型
     * @return 解析后对象实例
     */
    <T> T parse(String jsonText, Class<T> specificClass);

    /**
     * 指定类型对象转json文本
     * @param object 指定类型对象
     * @return json文本
     */
    <T> String toJsonString(T object);

    /**
     * 格式化/美化json文本
     * @param jsonText json文本
     * @return 格式化/美化后json文本
     */
    String format(String jsonText);

    /**
     * 判断文本是否符合json规范
     * @param text
     * @return true表示符合json规范, false表示不符合json规范
     */
    boolean isJson(String text);
}
