package com.sky.base.serialize.json.fastjson;

import static org.junit.Assert.*;

import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sky.base.serialize.AbstractSerializeTest;
import com.sky.base.serialize.json.fastjson.EmptyJsonObject;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-10
 */
public class EmptyJsonObjectTest extends AbstractSerializeTest {

    private static JSONObject emptyJsonObject = new EmptyJsonObject();

    @Test
    public void testGetJSONArray() {
        JSONArray jsonArray = emptyJsonObject.getJSONArray("mock");
        assertNotNull(jsonArray);
        assertThat(jsonArray.size(), is(0));
    }

}
