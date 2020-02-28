package com.sky.base.serialize.xml.factory;

import com.sky.base.serialize.xml.strategy.XstreamStrategy;
import com.thoughtworks.xstream.XStream;

/**
 * @Title XStream实例工厂
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-11
 */
public interface XstreamFactory {

    /** 
     * 实例化XStream
     * @param strategy 实例化策略
     * @return
     */
    XStream getInstance(XstreamStrategy strategy);
}
