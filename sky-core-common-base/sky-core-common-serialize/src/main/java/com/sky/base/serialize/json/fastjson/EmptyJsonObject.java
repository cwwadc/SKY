package com.sky.base.serialize.json.fastjson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @Title 空json对象, 包级共享
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-10
 */
class EmptyJsonObject extends JSONObject {

    /**
     * 
     */
    private static final long serialVersionUID = 5057892943651776177L;

    private static JSONArray emptyJsonArray = new EmptyJsonArray();

    public EmptyJsonObject() {
        super(0);
    }

    @Override
    public JSONArray getJSONArray(String key) {
        return emptyJsonArray;
    }

}
