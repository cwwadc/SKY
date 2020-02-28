package com.sky.base.serialize.json.fastjson;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sky.base.serialize.json.JsonHandler;
import com.sky.base.lang.string.StringUtils;

/**
 * @Title FastJson处理器
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-10
 */
public class FastJsonHandler implements JsonHandler {

    private static final String BRACKET_LEFT = "[";
    private static final String BRACE_LEFT = "{";
    private static final Logger LOGGER = LoggerFactory.getLogger(FastJsonHandler.class);

    @Override
    public String getString(String jsonText, String key) {
        JSONObject jsonObject = parseObjectNeverReturnNull(jsonText);
        return jsonObject.getString(key);
    }

    @Override
    public Integer getInteger(String jsonText, String key) {
        JSONObject jsonObject = parseObjectNeverReturnNull(jsonText);
        return jsonObject.getInteger(key);
    }

    @Override
    public Long getLong(String jsonText, String key) {
        JSONObject jsonObject = parseObjectNeverReturnNull(jsonText);
        return jsonObject.getLong(key);
    }

    @Override
    public Double getDouble(String jsonText, String key) {
        JSONObject jsonObject = parseObjectNeverReturnNull(jsonText);
        return jsonObject.getDouble(key);
    }

    @Override
    public BigInteger getBigInteger(String jsonText, String key) {
        JSONObject jsonObject = parseObjectNeverReturnNull(jsonText);
        return jsonObject.getBigInteger(key);
    }

    @Override
    public BigDecimal getBigDecimal(String jsonText, String key) {
        JSONObject jsonObject = parseObjectNeverReturnNull(jsonText);
        return jsonObject.getBigDecimal(key);
    }

    @Override
    public Boolean getBoolean(String jsonText, String key) {
        JSONObject jsonObject = parseObjectNeverReturnNull(jsonText);
        return jsonObject.getBoolean(key);
    }

    @Override
    public Byte getByte(String jsonText, String key) {
        JSONObject jsonObject = parseObjectNeverReturnNull(jsonText);
        return jsonObject.getByte(key);
    }

    @Override
    public <T> T getObject(String jsonText, String key, Class<T> specificClass) {
        JSONObject jsonObject = parseObjectNeverReturnNull(jsonText);
        return jsonObject.getObject(key, specificClass);
    }

    @Override
    public <T> List<T> getList(String jsonText, String key, Class<T> specificClass) {
        JSONObject jsonObject = parseObjectNeverReturnNull(jsonText);
        JSONArray jsonArray = jsonObject.getJSONArray(key);
        return jsonArray.toJavaList(specificClass);
    }

    @Override
    public <T> String addProperty(String jsonText, String key, T value) {
        JSONObject jsonObject = parseObjectNeverReturnNull(jsonText);
        addProperty(jsonObject, key, value);
        return jsonObject.toJSONString();
    }

    @Override
    public <T> String updateProperty(String jsonText, String key, T value) {
        return addProperty(jsonText, key, value);
    }

    @Override
    public String removeProperty(String jsonText, String key) {
        JSONObject jsonObject = parseObjectNeverReturnNull(jsonText);
        jsonObject.remove(key);
        return jsonObject.toJSONString();
    }

    @Override
    public <T> T parse(String jsonText, Class<T> specificClass) {
        return JSON.parseObject(jsonText, specificClass);
    }

    @Override
    public <T> String toJsonString(T object) {
        return JSON.toJSONString(object);
    }

    private <T> String toJsonString(T object, SerializerFeature... features) {
        return JSON.toJSONString(object, features);
    }

    @Override
    public String format(String jsonText) {
        JSONObject jsonObject = parseObject(jsonText);
        return toJsonString(jsonObject, SerializerFeature.PrettyFormat);
    }

    @Override
    public boolean isJson(String text) {
        try {
            if (!StringUtils.startsWith(text, BRACE_LEFT) && !StringUtils.startsWith(text, BRACKET_LEFT)) {
                return false;
            }
            JSON.parse(text);
            return true;
        } catch (Exception e) {
            LOGGER.error("Fastjson check json error, text : {}", text, e);
            return false;
        }
    }

    private JSONObject parseObject(String jsonText) {
        return JSONObject.parseObject(jsonText);
    }

    private JSONObject parseObjectNeverReturnNull(String jsonText) {
        JSONObject jsonObject = parseObject(jsonText);
        if (jsonObject == null) {
            LOGGER.trace("JsonObject is null, now return emptyJsonObject");
            jsonObject = new EmptyJsonObject();
        }
        return jsonObject;
    }

    private <T> void addProperty(JSONObject jsonObject, String key, T value) {
        LOGGER.trace("Value class type is {}", value.getClass());
        if (value instanceof String || value instanceof Number || value instanceof Boolean || value instanceof Byte[]) {
            jsonObject.put(key, value);
        } else {
            jsonObject.put(key, toJsonString(value));
        }
    }
}