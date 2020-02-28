package com.sky.base.serialize.xml;

/**
 * @Title xml常量
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-11
 */
public final class XmlConstants {

    /**
     * CDATA(Unparsed Character Data)标记前缀 <![CDATA[
     */
    public static final String CDATA_PREFIX = "<![CDATA[";
    /**
     * CDATA(Unparsed Character Data)标记后缀 ]]>
     */
    public static final String CDATA_SUFFIX = "]]>";

    private XmlConstants() {
        throw new IllegalStateException("Constant class");
    }

}
