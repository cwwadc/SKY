package com.sky.base.cache.redis.client;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zzq
 * @version v1.0
 * @Title com.ursa.base.cache.redis.client.RedisClient
 * @Description redis操作客户端接口
 * @date 2018/12/17
 */
public interface RedisClient {

	/**
	 * 将key对应的value自加1
	 *
	 * @param key 键
	 * @return value
	 */
	String incr(String key);

	/**
	 * 将key对应的value自加n
	 *
	 * @param key 键
	 * @param n   自加数
	 * @return value
	 */
	String incrBy(String key, int n);

	/**
	 * 将key对应的value自减1
	 *
	 * @param key 键
	 * @return value
	 */
	String decr(String key);

	/**
	 * 将key对应的value自减n
	 *
	 * @param key 键
	 * @param n   自减数
	 * @return value
	 */
	String decrBy(String key, int n);

	/**
	 * 设置一个key值生存时间
	 *
	 * @param key     键
	 * @param seconds 秒
	 * @return true 设置成功 false 设置失败 null 未知错误
	 */
	Boolean expire(String key, int seconds);

	/**
	 * 去除一个key值生存时间
	 *
	 * @param key 键
	 * @return true 设置成功 false 设置失败 null 未知错误
	 */
	Boolean persist(String key);

	/**
	 * 判断一个key值是否存在
	 *
	 * @param key 键
	 * @return true 存在 false 不存在 null 未知错误
	 */
	Boolean exists(String key);

	/**
	 * 删除key的数据
	 *
	 * @param key 键
	 * @return 删除键数量
	 */
	Long del(String... key);

	/**
	 * 查看key所对应value数据类型
	 *
	 * @param key 键
	 * @return null 未知错误 none key值不存在 string list set zset hash
	 */
	String type(String key);

	/**
	 * 获取key值数据剩余生存时间
	 *
	 * @param key 键
	 * @return -1 无限时间 -2 key 不存在 其他整整数表示生存时间
	 */
	Long ttl(String key);

	/**
	 * 获取匹配规则key列表
	 *
	 * @param patten 匹配规则
	 * @return 命中规则key值列表
	 */
	Set<String> keys(String patten);

	/**
	 * 执行Lua 脚本
	 *
	 * @param script 脚本
	 * @return
	 */
	Object eval(String script);

	/**
	 * 执行Lua 脚本
	 *
	 * @param script 脚本
	 * @param keys
	 * @param args
	 * @return
	 */
	Object eval(String script, List<String> keys, List<String> args);

	/**
	 * 新增键值对 value 默认json序列化
	 *
	 * @param key   键
	 * @param value 值
	 * @return true 设置成功 false 设置失败 null 未知错误
	 */
	Boolean set(String key, Object value);

	/**
	 * 新增键值对 value 默认json序列化
	 *
	 * @param key   键
	 * @param value 值
	 * @return true 设置成功 false 设置失败 null 未知错误
	 */
	Boolean setnx(String key, Object value);

	/**
	 * 新增带时间键值对
	 *
	 * @param key     键
	 * @param value   值
	 * @param seconds 秒
	 * @return true 设置成功 false 设置失败 null 未知错误
	 */
	Boolean setex(String key, Object value, int seconds);

	/**
	 * 获取key对应的value并更新value
	 *
	 * @param key   键
	 * @param value 值
	 * @return old value
	 */
	String getSet(String key, String value);

	/**
	 * 获取key对应的value并更新value
	 *
	 * @param key           键
	 * @param value         值
	 * @param specificClass 指定类型
	 * @return old value
	 */
	<T> T getSetByObj(String key, Object value, Class<T> specificClass);

	/**
	 * 根据key值获取value
	 *
	 * @param key           键
	 * @param specificClass 指定类型
	 * @param               <T>
	 * @return
	 */
	<T> T get(String key, Class<T> specificClass);

	/**
	 * 在key对应value后面拓展字符串str
	 *
	 * @param key 键
	 * @param str 拓展字符串
	 * @return null 未知错误 Long 添加后字符长度
	 */
	Long append(String key, String str);

	/**
	 * 添加一个List
	 *
	 * @param key   键
	 * @param value 值
	 * @return 返回插入后List size
	 */
	Long lpush(String key, List<String> value);

	/**
	 * 添加一个List
	 *
	 * @param key   键
	 * @param value 值
	 * @return 返回插入后List size
	 */
	<T> Long lpushByObj(String key, List<T> value);

	/**
	 * 获取一个List
	 *
	 * @param key           键
	 * @param specificClass 指定类型
	 * @param               <T>
	 * @return
	 */
	<T> List<T> lrangeAll(String key, Class<T> specificClass);

	/**
	 * 左出栈List里面一个数据
	 *
	 * @param key           键
	 * @param specificClass 指定类型
	 * @return
	 */
	<T> T lpop(String key, Class<T> specificClass);

	/**
	 * 左出栈List里面一个数据
	 *
	 * @param key           键
	 * @param specificClass 指定类型
	 * @return
	 */
	<T> T rpop(String key, Class<T> specificClass);

	/**
	 * 左插入List里面一个数据
	 *
	 * @param key   键
	 * @param value value
	 * @return 返回插入后List size
	 */
	Long lpush(String key, Object value);

	/**
	 * 左插入List里面一个数据
	 *
	 * @param key   键
	 * @param value value
	 * @return 返回插入后List size
	 */
	Long rpush(String key, Object value);

	/**
	 * 获取List数量
	 *
	 * @param key 键
	 * @return
	 */
	Long llen(String key);

	/**
	 * 新建一个Hash值
	 *
	 * @param key 键
	 * @param map Map
	 * @return true 设置成功 false 设置失败 null 未知错误
	 */
	Boolean hmset(String key, Map<String, String> map);

	/**
	 * 新建一个Hash值
	 *
	 * @param key 键
	 * @param map Map
	 * @return true 设置成功 false 设置失败 null 未知错误
	 */
	<T> Boolean hmsetByObj(String key, Map<String, T> map);

	/**
	 * 为一个Hash值新增一个键值对
	 *
	 * @param key      键
	 * @param mapKey   新增Hash键
	 * @param mapValue 新增Hash值
	 * @return 1 新建成功 0 覆盖成功 null 未知错误
	 */
	<T> Long hset(String key, String mapKey, T mapValue);

	/**
	 * 获取一个Hash
	 *
	 * @param key           键
	 * @param specificClass 指定类型
	 * @return Map
	 */
	<T> Map<String, T> hgetAll(String key, Class<T> specificClass);

	/**
	 * 获取一个Hash中某个元素
	 *
	 * @param key           键
	 * @param mapKey        hash元素键值
	 * @param specificClass 指定类型
	 * @return
	 */
	<T> T hget(String key, String mapKey, Class<T> specificClass);

	/**
	 * 从Hash删除一个元素
	 *
	 * @param key    键
	 * @param mapKey hash元素键值
	 * @return
	 */
	Boolean hdel(String key, String... mapKey);

	/**
	 * 判断Hash是否存在mapKey对应属性
	 *
	 * @param key    键
	 * @param mapKey hash元素键值
	 * @return
	 */
	Boolean hexists(String key, String mapKey);

	/**
	 * 获取Hash所有元素的key
	 *
	 * @param key 键
	 * @return
	 */
	Set<String> hkeys(String key);

	/**
	 * 获取Hash所有元素的value
	 *
	 * @param key           键
	 * @param specificClass 指定类型
	 * @return
	 */
	<T> List<T> hvals(String key, Class<T> specificClass);

	/**
	 * 返回哈希表 key 中，一个或多个给定域的值，并转换为指定对象列表
	 * 
	 * @param key
	 * @param specificClass
	 * @param mapKeys
	 * @return
	 */
	<T> List<T> hmget(String key, Class<T> specificClass, String... mapKeys);

	/**
	 * 将字符串值 value 关联到 key
	 * 
	 * @param key
	 * @param value
	 * @param nxxx       NX|XX, NX -- Only set the key if it does not already exist.
	 *                   XX -- Only set the keyif it already exist
	 * @param expx       EX|PX, expire time units: EX = seconds; PX = milliseconds
	 * @param expireTime expire time in the units of expx
	 * @return
	 */
	Boolean set(String key, String value, String nxxx, String expx, long expireTime);
}
