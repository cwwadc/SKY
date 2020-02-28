package com.sky.base.serialize.json.fastjson;

import com.alibaba.fastjson.JSONArray;

/**
 * @Title 空json数组对象, 包级共享
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-10
 */
class EmptyJsonArray extends JSONArray {

    /**
     * 
     */
    private static final long serialVersionUID = 4317086982474561636L;

    public EmptyJsonArray() {
        super(0);
    }

}