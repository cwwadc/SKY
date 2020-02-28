package com.sky.base.lock.zookeeper;

import static org.junit.Assert.assertNotNull;

import org.apache.curator.test.TestingServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sky.base.lock.zookeeper.CuratorZookeeperDistributedLock;
import com.sky.base.lock.zookeeper.ZookeeperProperties;
import com.sky.base.test.net.NetUtils;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-01-18
 */
public class CuratorZookeeperDistributedLockTest {

    private TestingServer zkServer;

    private CuratorZookeeperDistributedLock lock;

    @Before
    public void setUp() throws Exception {
        int port = NetUtils.getAvailablePort();
        zkServer = new TestingServer(port, true);
        ZookeeperProperties properties = new ZookeeperProperties();
        properties.setConnectString("127.0.0.1:" + port);
        properties.setTimeout(10 * 1000);
        lock = new CuratorZookeeperDistributedLock(properties);
    }

    @After
    public void tearDown() throws Exception {
        zkServer.stop();
    }

    @Test
    public void testConstructor() {
        assertNotNull(lock.getClient());
    }

}
