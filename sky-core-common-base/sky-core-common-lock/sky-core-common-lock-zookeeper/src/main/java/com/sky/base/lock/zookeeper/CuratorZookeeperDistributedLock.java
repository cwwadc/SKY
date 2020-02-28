package com.sky.base.lock.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

import com.google.common.base.Strings;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-01-18
 */
public class CuratorZookeeperDistributedLock extends AbstractZookeeperDistributedLock {

    private final CuratorFramework client;

    public CuratorZookeeperDistributedLock(ZookeeperProperties properties) {
        try {
            CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                    .connectString(properties.getConnectString()).retryPolicy(new RetryNTimes(1, 1000))
                    .connectionTimeoutMs(properties.getTimeout());
            String digest = properties.getDigest();
            if (!Strings.isNullOrEmpty(digest)) {
                builder = builder.authorization("digest", digest.getBytes());
            }
            client = builder.build();
            client.start();
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public boolean tryLock(String key) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean tryLock(String key, long timeout) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean lock(String key) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean unLock(String key) {
        // TODO Auto-generated method stub
        return false;
    }

    CuratorFramework getClient() {
        return client;
    }

}
