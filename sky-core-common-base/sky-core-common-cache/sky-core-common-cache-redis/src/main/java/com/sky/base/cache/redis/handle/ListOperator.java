package com.sky.base.cache.redis.handle;

import java.util.List;

import com.sky.base.cache.redis.handle.impl.BaseListOperator;

/**
 * @author zzq
 * @version v1.0
 * @Title com.ursa.base.cache.redis.handle.ListOperator
 * @Description List操作者接口
 * @date 2018/12/11
 */
public interface ListOperator {

    /**
     * 添加一个List
     *
     * @param key   键
     * @param value 值
     * @return
     */
    Long set(String key, List<String> value);

    /**
     * 获取一个List
     *
     * @param key 键
     * @return
     */
    List<String> get(String key);

    /**
     * 添加一个List
     *
     * @param key   键
     * @param value 值
     * @param <T>
     * @return
     */
    <T> Long setT(String key, List<T> value);

    /**
     * 获取一个List
     *
     * @param key           键
     * @param specificClass 指定类型
     * @param <T>
     * @return
     */
    <T> List<T> get(String key, Class<T> specificClass);

    /**
     * 获取key对应list为index的元素
     *
     * @param key   键
     * @param index 下标
     * @return
     */
    String get(String key, int index);

    /**
     * 获取key对应list为index的元素
     *
     * @param key           键
     * @param specificClass 指定类型
     * @param index         下标
     * @param <T>
     * @return
     */
    <T> T get(String key, Class<T> specificClass, int index);

    /**
     * 获取List数量
     *
     * @param key 键
     * @return
     */
    Long size(String key);

    /**
     * 向List里面插入一个数据
     *
     * @param key       键
     * @param value     值
     * @param direction 方向
     * @return
     */
    Long set(String key, String value, BaseListOperator.Direction direction);

    /**
     * 向List里面插入一个数据
     *
     * @param key       键
     * @param value     值
     * @param direction 方向
     * @return
     */
    <T> Long set(String key, T value, BaseListOperator.Direction direction);

    /**
     * 删除List里面一个数据
     *
     * @param key       键
     * @param direction 方向
     * @return
     */
    String del(String key, BaseListOperator.Direction direction);

    /**
     * 删除List里面一个数据
     *
     * @param key       键
     * @param specificClass 指定类型
     * @param direction 方向
     * @return
     */
    <T> T del(String key, Class<T> specificClass, BaseListOperator.Direction direction);
}
