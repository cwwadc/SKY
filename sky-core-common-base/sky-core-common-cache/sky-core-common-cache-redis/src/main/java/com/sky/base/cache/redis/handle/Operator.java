package com.sky.base.cache.redis.handle;

import java.util.List;
import java.util.Set;

/**
 * @author zzq
 * @version v1.0
 * @Title com.ursa.base.cache.redis.handle.Operator
 * @Description 通用操作者接口
 * @date 2018/12/11
 */
public interface Operator {

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
    String incr(String key, int n);

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
    String decr(String key, int n);

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
     * @return null 未知错误 none key值不存在 string list  set zset hash
     */
    String type(String key);

    /**
     * 获取key值数据剩余生存时间
     *
     * @param key 键
     * @return -1 无限时间 -2 key 不存在 其他整整数表示生存时间
     */
    Long lifeTime(String key);

    /**
     * 获取匹配规则key列表
     *
     * @param patten 匹配规则
     * @return 命中规则key值列表
     */
    Set<String> keys(String patten);

    /**
     * 执行Lua 脚本
     * @param script 脚本
     * @return
     */
    Object eval(String script);

    /**
     * 执行Lua 脚本
     * @param script 脚本
     * @param keys
     * @param args
     * @return
     */
    Object eval(String script, List<String> keys, List<String> args);


}
