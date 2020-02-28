package com.sky.base.context.spring;

import org.springframework.cglib.beans.BeanMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zzq
 * @version 1.0.0
 * @Title {@link com.sky.base.context.spring.BeanUtils}
 * @Description 扩展org.springframework.beans.BeanUtils
 * @date 2019/2/15
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    /**
     * 将给定源List的属性值复制到目标List中
     *
     * @param sources 源List
     * @param targets 目标List
     * @param clazz   目标Class
     * @param <T>
     */
    public static <T> void copyProperties(List<?> sources, List<T> targets, Class<T> clazz) {
        copyProperties(sources, targets, clazz, (String[]) null);
    }

    /**
     * 将给定源List的属性值复制到目标List中
     *
     * @param sources          源List
     * @param targets          目标List
     * @param clazz            目标Class
     * @param ignoreProperties 忽略属性值
     * @param <T>
     */
    public static <T> void copyProperties(List<?> sources, List<T> targets, Class<T> clazz,
                                          String... ignoreProperties) {
        sources.forEach((source) -> {
            T target = null;
            try {
                target = clazz.newInstance();
                copyProperties(source, target, ignoreProperties);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            targets.add(target);
        });
    }

    /**
     * bean转map
     *
     * @param bean 待转换的bean
     * @param map  转换后的map
     * @param <T>
     */
    public static <T> void beanToMap(T bean, Map<String, Object> map) {
        BeanMap beanMap = BeanMap.create(bean);
        for (Object key : beanMap.keySet()) {
            Object value = beanMap.get(key);
            if (null == value) {
                continue;
            }
            map.put(key.toString(), value);
        }
    }

    /**
     * beanList转mapList
     *
     * @param beans 待转换的beanList
     * @param maps  转换后的mapList
     * @param <T>
     */
    public static <T> void beanToMapByList(List<T> beans, List<Map<String, Object>> maps) {
        for (T bean : beans) {
            Map<String, Object> map = new HashMap<>(16);
            beanToMap(bean, map);
            maps.add(map);
        }
    }
}
