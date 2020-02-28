package com.sky.base.serialize.xml;

import com.sky.base.serialize.xml.factory.DefaultXstreamFactoryImpl;
import com.sky.base.serialize.xml.factory.XstreamFactory;
import com.sky.base.serialize.xml.strategy.DefaultXstreamStrategy;
import com.sky.base.serialize.xml.strategy.XstreamStrategy;
import com.thoughtworks.xstream.XStream;

/**
 * @Title xml工具类
 * @Description 基于com.thoughtworks.xstream
 * @author lizp
 * @version 1.0.0
 * @date 2015-12-11
 */
public final class XmlUtils {

    private static XstreamFactory xStreamFactory = new DefaultXstreamFactoryImpl();
    private static XstreamStrategy strategy = new DefaultXstreamStrategy();

    /**
         * 对象转XML字符串
     * @param object 序列化对象
     * @return XML字符串
     */
    public static <T> String toXml(T object) {
        return toXml(object, null);
    }
    
    /**
     * XML字符串转对象
     * @param xml XML字符串
     * @param specificClass 指定类型，反序列化返回类型
     * @param strategy 指定策略
     * @param addtionalTypes 追加解析注解处理对象类型
     * @return 序列化对象
     */
    public static <T> T fromXml(String xml, Class<T> specificClass, Class<?>... addtionalTypes) {
    	return fromXml(xml, specificClass, strategy, addtionalTypes);
    }

    /**
         * 对象转XML字符串
     * @param object 序列化对象
     * @param strategy 指定策略
     * @return XML字符串
     */
    public static <T> String toXml(T object, XstreamStrategy strategy) {
        XStream xStream = getXStream(strategy);
        return xStream.toXML(object);
    }

    /**
     * XML字符串转对象
     * @param xml XML字符串
     * @param specificClass 指定类型
     * @param strategy 指定策略
     * @return 序列化对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromXml(String xml, Class<T> specificClass, XstreamStrategy strategy) {
        XStream xStream = getXStream(strategy);
        xStream.processAnnotations(specificClass);
        return (T) xStream.fromXML(xml);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T fromXml(String xml, Class<T> specificClass, XstreamStrategy strategy, Class<?>... additionalTypes) {
        XStream xStream = getXStream(strategy);
        xStream.processAnnotations(specificClass);
        xStream.processAnnotations(additionalTypes);
        return (T) xStream.fromXML(xml);
    }

    /**
              * 添加CDATA标签
     * @param text 文本
     * @return 添加CDATA标签后的文本
     */
    public static String unparsedCharacterData(String text) {
        return XmlConstants.CDATA_PREFIX + text + XmlConstants.CDATA_SUFFIX;
    }

    public static XStream getXStream(XstreamStrategy strategy) {
        return xStreamFactory.getInstance(strategy);
    }

    private XmlUtils() {
        throw new IllegalStateException("Utility class");
    }

}
