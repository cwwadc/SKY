package com.sky.base.lock.zookeeper;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-01-18
 */
public class ZookeeperProperties {

    private String connectString;

    private int timeout;

    private String digest;

    String getConnectString() {
        return connectString;
    }

    void setConnectString(String connectString) {
        this.connectString = connectString;
    }

    int getTimeout() {
        return timeout;
    }

    void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    String getDigest() {
        return digest;
    }

    void setDigest(String digest) {
        this.digest = digest;
    }

}
