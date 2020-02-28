package com.sky.base.serialize.json.fastjson;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.sky.base.serialize.AbstractSerializeTest;
import com.sky.base.serialize.json.fastjson.EmptyJsonArray;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-10
 */
public class EmptyJsonArrayTest extends AbstractSerializeTest {

    private static JSONArray nullJsonArray = new EmptyJsonArray();

    @Test
    public void testToJavaList() {
        List<Object> list = nullJsonArray.toJavaList(Object.class);
        assertNotNull(list);
        assertThat(list.size(), is(0));
    }

}
