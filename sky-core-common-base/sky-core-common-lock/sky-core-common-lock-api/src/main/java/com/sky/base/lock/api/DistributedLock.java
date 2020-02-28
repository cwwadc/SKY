package com.sky.base.lock.api;

/**
 * @Title 分布式锁接口
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-01-16
 */
public interface DistributedLock {
    /**
     * 尝试上锁
     * 
     * @param key
     * @return
     */
    boolean tryLock(String key);

    /**
     * 在timeOut的时间内尝试上锁
     * 
     * @param key
     * @param timeout
     * @return
     */
    boolean tryLock(String key, long timeout);

    /**
     * 上锁
     * 
     * @param key
     * @return
     */
    boolean lock(String key);

    /**
     * 释放锁
     * 
     * @param key
     * @return
     */
    boolean unLock(String key);
}
