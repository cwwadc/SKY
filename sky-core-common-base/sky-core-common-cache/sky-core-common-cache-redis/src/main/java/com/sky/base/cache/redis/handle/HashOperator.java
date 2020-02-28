package com.sky.base.cache.redis.handle;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zzq
 * @version v1.0
 * @Title com.ursa.base.cache.redis.handle.HashOperator
 * @Description 哈希操作者接口
 * @date 2018/12/11
 */
public interface HashOperator {

    /**
     * 新建一个Hash值
     *
     * @param key 键
     * @param map Map
     * @return true 设置成功 false 设置失败 null 未知错误
     */
    Boolean set(String key, Map<String, String> map);

    /**
     * 新建一个Hash值
     *
     * @param key  键
     * @param map  Map
     * @return true 设置成功 false 设置失败 null 未知错误
     */
    <T> Boolean setT(String key, Map<String, T> map);

    /**
     * 为一个Hash值新增一个键值对
     *
     * @param key      键
     * @param mapKey   新增Hash键
     * @param mapValue 新增Hash值
     * @return 1 新建成功 0 覆盖成功 null 未知错误
     */
    Long set(String key, String mapKey, String mapValue);

    /**
     * 为一个Hash值新增一个键值对
     *
     * @param key      键
     * @param mapKey   新增Hash键
     * @param mapValue 新增Hash值
     * @return 1 新建成功 0 覆盖成功 null 未知错误
     */
    <T> Long set(String key, String mapKey, T mapValue);

    /**
     * 获取一个Hash
     *
     * @param key 键
     * @return Map
     */
    Map<String, String> get(String key);

    /**
     * 获取一个Hash
     *
     * @param key           键
     * @param specificClass 指定类型
     * @return Map
     */
    <T> Map<String, T> get(String key, Class<T> specificClass);

    /**
     * 获取一个Hash中某个元素
     *
     * @param key    键
     * @param mapKey hash元素键值
     * @return
     */
    String get(String key, String mapKey);

    /**
     * 获取一个Hash中某个元素
     *
     * @param key           键
     * @param mapKey        hash元素键值
     * @param specificClass 指定类型
     * @return
     */
    <T> T get(String key, String mapKey, Class<T> specificClass);

    /**
     * 获取Hash所有元素的key
     *
     * @param key 键
     * @return
     */
    Set<String> getMapKeys(String key);

    /**
     * 获取Hash所有元素的value
     *
     * @param key 键
     * @return
     */
    List<String> getMapValues(String key);

    /**
     * 获取Hash所有元素的value
     *
     * @param key           键
     * @param specificClass 指定类型
     * @return
     */
    <T> List<T> getMapValues(String key, Class<T> specificClass);

    /**
     * 从Hash删除一个元素
     *
     * @param key    键
     * @param mapKey hash元素键值
     * @return
     */
    Boolean del(String key, String... mapKey);

    /**
     * 判断Hash是否存在mapKey对应属性
     *
     * @param key    键
     * @param mapKey hash元素键值
     * @return
     */
    Boolean exists(String key, String mapKey);
    
    /**
     * 返回哈希表 key 中，一个或多个给定域的值，并转换为指定对象列表
     * @param key
     * @param specificClass
     * @param mapKeys
     * @return
     */
	<T> List<T> mget(String key, Class<T> specificClass, String... mapKeys);

}
