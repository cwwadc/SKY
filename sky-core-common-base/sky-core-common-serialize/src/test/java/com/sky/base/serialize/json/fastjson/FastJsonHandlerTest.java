package com.sky.base.serialize.json.fastjson;

import org.junit.Before;

import com.sky.base.serialize.json.BaseJsonHandlerTest;
import com.sky.base.serialize.json.fastjson.FastJsonHandler;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-10
 */
public class FastJsonHandlerTest extends BaseJsonHandlerTest {

    @Before
    public void setUp() {
        jsonHandler = new FastJsonHandler();
    }

}
