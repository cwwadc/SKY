package com.sky.base.serialize.xml.driver;

import com.thoughtworks.xstream.io.naming.NoNameCoder;

/**
 * @Title XppDriver
 * @Description 紧凑型, 可选节点使用CDATA标签
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-12
 */
public class CompactConditionUnparsedXppDriver extends ConfigurableXppDriver {

    public CompactConditionUnparsedXppDriver() {
        super(true, UnparsedCharacterDataTypeEnum.CONDITION_CDATA, new NoNameCoder());
    }

}
