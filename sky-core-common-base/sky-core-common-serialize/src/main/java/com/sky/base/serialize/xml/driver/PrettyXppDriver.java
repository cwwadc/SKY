package com.sky.base.serialize.xml.driver;

import com.thoughtworks.xstream.io.naming.NoNameCoder;

/**
 * @Title XppDriver
 * @Description 美化型, 不使用CDATA标签
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-12
 */
public class PrettyXppDriver extends ConfigurableXppDriver {

    public PrettyXppDriver() {
        super(false, UnparsedCharacterDataTypeEnum.NO_CDATA, new NoNameCoder());
    }

}
