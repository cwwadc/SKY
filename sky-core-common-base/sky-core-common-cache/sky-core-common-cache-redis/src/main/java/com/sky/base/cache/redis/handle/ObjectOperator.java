package com.sky.base.cache.redis.handle;


/**
 * @author zzq
 * @version v1.0
 * @Title com.ursa.base.cache.redis.handle.ObjectOperator
 * @Description object操作者接口
 * @date 2018/12/11
 */
public interface ObjectOperator {

    /**
     * 新增键值对 value 默认json序列化
     *
     * @param key   键
     * @param value 值
     * @return true 设置成功 false 设置失败 null 未知错误
     */
    Boolean set(String key, Object value);

    /**
     * 新增键值对
     *
     * @param key              键
     * @param value            值
     * @param isDuplicateJudge 是否判断重复 true 重复不插入 false 直接插入
     * @return true 设置成功 false 设置失败 null 未知错误
     */
    Boolean set(String key, Object value, boolean isDuplicateJudge);

    /**
     * 新增带时间键值对
     *
     * @param key     键
     * @param value   值
     * @param seconds 秒
     * @return true 设置成功 false 设置失败 null 未知错误
     */
    Boolean set(String key, Object value, int seconds);

    /**
     * 根据key值获取value
     *
     * @param key           键
     * @param specificClass 指定类型
     * @param <T>
     * @return
     */
    <T> T get(String key, Class<T> specificClass);

    /**
     * 获取key对应的value并更新value
     *
     * @param key           键
     * @param value         值
     * @param specificClass 指定类型
     * @return old value
     */
    <T> T getSet(String key, Object value, Class<T> specificClass);

}
