package com.sky.base.serialize.xml;

import com.sky.base.serialize.AbstractSerializeTest;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-12
 */
public abstract class AbstractXmlTest extends AbstractSerializeTest {

    protected static final String INDENTS = new String(new byte[] { ' ', ' ' });

    protected static final String NEWLINE = "\n";

    protected static final String NEWLINE_INDENTS = NEWLINE + INDENTS;

    protected String simplePrettyFormat(String format) {
        return String.format(format, NEWLINE_INDENTS, NEWLINE);
    }

}
