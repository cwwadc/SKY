package com.sky.base.context.spring;

import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;
import org.springframework.util.SystemPropertyUtils;

/**
 * @Title Spring上下文
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-07
 */
@Component
public class SpringContext implements ApplicationContextAware, EmbeddedValueResolverAware {

    private static ApplicationContext applicationContext;

    private static StringValueResolver stringValueResolver;
    
    public static ApplicationContext getApplicationContext() {
    	return SpringContext.applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.applicationContext = applicationContext;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        SpringContext.stringValueResolver = resolver;
    }

    /**
     * 匹配名称获取实例
     * @param name 实例名称
     * @return 实例对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 匹配类型获取实例
     * @param requiredType 实例类型
     * @return 实例对象
     */
    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    /**
     * 匹配类型获取实例
     * @param requiredType 实例类型
     * @param args 创建实例时需要的参数
     * @return 实例对象
     */
    public static <T> T getBean(Class<T> requiredType, Object... args) {
        return applicationContext.getBean(requiredType, args);
    }

    /**
     * 匹配名称、类型获取实例
     * @param name 实例名称
     * @param requiredType 实例类型
     * @return 实例对象
     */
    public static <T> T getBean(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }

    /**
     * 匹配名称获取实例
     * @param name 实例名称
     * @param args 创建实例时需要的参数
     * @return 实例对象
     */
    public static Object getBean(String name, Object... args) {
        return applicationContext.getBean(name, args);
    }

    /**
     * 根据类型获取实例对象集合
     * @param type 实例类型
     * @return 实例对象集合
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return applicationContext.getBeansOfType(type);
    }

    /**
     * 根据注解获取实例对象集合
     * @param annotationType 注解类型
     * @return 实例对象集合
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        return applicationContext.getBeansWithAnnotation(annotationType);
    }

    /**
     * 获取国际化消息
     * @param code 消息代码
     * @param args 填充参数
     * @param locale 地区
     * @return 国际化消息
     */
    public static String getMessage(String code, Object[] args, Locale locale) {
        return applicationContext.getMessage(code, args, locale);
    }

    /**
     * 获取国际化消息
     * @param code 消息代码
     * @param args 填充参数
     * @param defaultMessage 默认消息
     * @param locale 地区
     * @return 国际化消息
     */
    public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return applicationContext.getMessage(code, args, defaultMessage, locale);
    }

    /**
     * 获取资源
     * @param location 资源位置
     * @return 资源对象
     */
    public static Resource getResource(String location) {
        return applicationContext.getResource(location);
    }

    /**
     * 获取Bean定义名称列表
     * @return
     */
    public static String[] getBeanDefinitionNames() {
        return applicationContext.getBeanDefinitionNames();
    }

    /**
     * 根据获取属性值
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        return stringValueResolver.resolveStringValue(
                SystemPropertyUtils.PLACEHOLDER_PREFIX + key + SystemPropertyUtils.PLACEHOLDER_SUFFIX);
    }

}
