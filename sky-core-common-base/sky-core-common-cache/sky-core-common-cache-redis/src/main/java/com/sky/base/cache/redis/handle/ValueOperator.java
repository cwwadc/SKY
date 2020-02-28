package com.sky.base.cache.redis.handle;

/**
 * @author zzq
 * @version v1.0
 * @Title com.ursa.base.cache.redis.handle.ValueOperator
 * @Description 字符串操作者接口
 * @date 2018/12/11
 */
public interface ValueOperator {

    /**
     * 新增键值对
     *
     * @param key   键
     * @param value 值
     * @return true 设置成功 false 设置失败 null 未知错误
     */
    Boolean set(String key, String value);

    /**
     * 新增键值对
     *
     * @param key              键
     * @param value            值
     * @param isDuplicateJudge 是否判断重复 true 重复不插入 false 直接插入
     * @return true 设置成功 false 设置失败 null 未知错误
     */
    Boolean set(String key, String value, boolean isDuplicateJudge);

    /**
     * 新增带时间键值对
     *
     * @param key     键
     * @param value   值
     * @param seconds 秒
     * @return true 设置成功 false 设置失败 null 未知错误
     */
    Boolean set(String key, String value, int seconds);

    /**
     * 获取key对应的value并更新value
     *
     * @param key   键
     * @param value 值
     * @return old value
     */
    String getSet(String key, String value);

    /**
     * 根据key值获取value
     *
     * @param key 键
     * @return value
     */
    String get(String key);

    /**
     * 在key对应value后面拓展字符串str
     *
     * @param key 键
     * @param str 拓展字符串
     * @return null 未知错误 Long 添加后字符长度
     */
    Long append(String key, String str);

    /**
     * 将字符串值 value 关联到 key
     * @param key
     * @param value
     * @param nxxx NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the keyif it already exist
     * @param expx EX|PX, expire time units: EX = seconds; PX = milliseconds
     * @param expireTime expire time in the units of expx
     * @return
     */
	String set(String key, String value, String nxxx, String expx, long expireTime);

}
