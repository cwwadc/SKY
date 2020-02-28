package com.sky.base.serialize.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSONException;
import com.sky.base.serialize.AbstractSerializeTest;
import com.sky.base.serialize.json.JsonHandler;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-10
 */
public abstract class BaseJsonHandlerTest extends AbstractSerializeTest {

    protected JsonHandler jsonHandler;

    @Test
    public void testToJsonString() {
        MockPerson mockPerson = new MockPerson("alex", 18, "male");
        String text = jsonHandler.toJsonString(mockPerson);
        assertEquals("{\"age\":18,\"name\":\"alex\",\"sex\":\"male\"}", text);
    }

    @Test
    public void testParse() {
        String text = "{\"age\":13,\"name\":\"li\",\"sex\":\"male\"}";
        MockPerson person = jsonHandler.parse(text, MockPerson.class);
        assertEquals("li", person.getName());
        assertEquals(13, person.getAge());
        assertEquals("male", person.getSex());
    }

    @Test
    public void testFormat() {
        String expectedText = "{\n\t\"sex\":\"male\",\n\t\"name\":\"li\",\n\t\"age\":13\n}";
        String text = "{\"age\":13,\"name\":\"li\",\"sex\":\"male\"}";
        String newText = jsonHandler.format(text);
        assertEquals(expectedText, newText);
    }

    @Test
    public void testIsJson() {
        assertTrue(jsonHandler.isJson("{\"age\":13,\"name\":\"li\"}"));
        assertTrue(jsonHandler.isJson("[{\"age\":13,\"name\":\"li\"}]"));
        assertTrue(jsonHandler.isJson("{}"));
        assertFalse(jsonHandler.isJson("{\"age\":13,\"name\":\"li\"}test"));
        assertFalse(jsonHandler.isJson("123"));
        assertFalse(jsonHandler.isJson("[\"age\":13,\"name\":\"li\"]"));
    }

    @Test
    public void testGetString() {
        assertNull(jsonHandler.getString("", "age"));
        assertEquals("13", jsonHandler.getString("{\"age\":13,\"name\":\"li\"}", "age"));
        assertEquals("li", jsonHandler.getString("{\"age\":13,\"name\":\"li\"}", "name"));
        assertNull(jsonHandler.getString("{\"age\":13,\"name\":\"li\"}", "abc"));
    }

    @Test
    public void testGetInteger() {
        assertNull(jsonHandler.getInteger("", "age"));
        assertEquals(new Integer(13), jsonHandler.getInteger("{\"age\":13}", "age"));
        assertEquals(new Integer(13), jsonHandler.getInteger("{\"age\":\"13\"}", "age"));
        assertNull(jsonHandler.getInteger("{\"age\":13}", "abc"));

        try {
            assertEquals("li", jsonHandler.getInteger("{\"age\":13,\"name\":\"li\"}", "name"));
            fail("expected error");
        } catch (Exception e) {
            assertEquals(NumberFormatException.class, e.getClass());
        }
    }

    @Test
    public void testGetLong() {
        assertNull(jsonHandler.getLong("", "age"));
        assertEquals(new Long(13), jsonHandler.getLong("{\"age\":13}", "age"));
        assertEquals(new Long(13), jsonHandler.getLong("{\"age\":\"13\"}", "age"));
        assertNull(jsonHandler.getLong("{\"age\":13}", "abc"));

        try {
            assertEquals("li", jsonHandler.getLong("{\"name\":\"li\"}", "name"));
            fail("expected error");
        } catch (Exception e) {
            assertEquals(JSONException.class, e.getClass());
        }
    }

    @Test
    public void testGetDouble() {
        assertNull(jsonHandler.getDouble("", "age"));
        assertEquals(new Double(13.2D), jsonHandler.getDouble("{\"age\":13.2,\"name\":\"li\"}", "age"));
        assertEquals(new Double(13.2D), jsonHandler.getDouble("{\"age\":\"13.2\"}", "age"));
        assertNull(jsonHandler.getDouble("{\"age\":13.2}", "abc"));

        try {
            assertEquals("li", jsonHandler.getDouble("{\"age\":13.2,\"name\":\"li\"}", "name"));
            fail("expected error");
        } catch (Exception e) {
            assertEquals(NumberFormatException.class, e.getClass());
        }
    }

    @Test
    public void testGetBigInteger() {
        assertNull(jsonHandler.getBigInteger("", "age"));
        assertEquals(new BigInteger("100"), jsonHandler.getBigInteger("{\"age\":100,\"name\":\"li\"}", "age"));
        assertEquals(new BigInteger("100"), jsonHandler.getBigInteger("{\"age\":\"100\",\"name\":\"li\"}", "age"));
        assertNull(jsonHandler.getBigInteger("{\"age\":100,\"name\":\"li\"}", "abc"));

        try {
            assertEquals("li", jsonHandler.getBigInteger("{\"age\":100,\"name\":\"li\"}", "name"));
            fail("expected error");
        } catch (Exception e) {
            assertEquals(NumberFormatException.class, e.getClass());
        }
    }

    @Test
    public void testGetBigDecimal() {
        assertNull(jsonHandler.getBigDecimal("", "age"));
        assertEquals(new BigDecimal("100.2"), jsonHandler.getBigDecimal("{\"age\":100.2,\"name\":\"li\"}", "age"));
        assertEquals(new BigDecimal("100.2"), jsonHandler.getBigDecimal("{\"age\":\"100.2\",\"name\":\"li\"}", "age"));
        assertNull(jsonHandler.getBigDecimal("{\"age\":100.2,\"name\":\"li\"}", "abc"));

        try {
            assertEquals("li", jsonHandler.getBigDecimal("{\"age\":100.2,\"name\":\"li\"}", "name"));
            fail("expected error");
        } catch (Exception e) {
            assertEquals(NumberFormatException.class, e.getClass());
        }
    }

    @Test
    public void testGetBoolean() {
        assertNull(jsonHandler.getBoolean("", "age"));
        assertFalse(jsonHandler.getBoolean("{\"flag\":0,\"name\":\"li\"}", "flag"));
        assertTrue(jsonHandler.getBoolean("{\"flag\":1,\"name\":\"li\"}", "flag"));
        assertFalse(jsonHandler.getBoolean("{\"flag\":\"false\",\"name\":\"li\"}", "flag"));
        assertTrue(jsonHandler.getBoolean("{\"flag\":\"true\",\"name\":\"li\"}", "flag"));
        assertNull(jsonHandler.getBoolean("{\"age\":100.2,\"name\":\"li\"}", "abc"));

        try {
            assertEquals("li", jsonHandler.getBoolean("{\"age\":100.2,\"name\":\"li\"}", "name"));
            fail("expected error");
        } catch (Exception e) {
            assertEquals(JSONException.class, e.getClass());
        }
    }

    @Test
    public void testGetByte() {
        assertNull(jsonHandler.getByte("", "age"));
        assertEquals(new Byte("100"), jsonHandler.getByte("{\"age\":100,\"name\":\"li\"}", "age"));
        assertEquals(new Byte("99"), jsonHandler.getByte("{\"age\":\"99\",\"name\":\"li\"}", "age"));
        assertNull(jsonHandler.getByte("{\"age\":100.2,\"name\":\"li\"}", "abc"));

        try {
            assertEquals("li", jsonHandler.getByte("{\"age\":100,\"name\":\"li\"}", "name"));
            fail("expected error");
        } catch (Exception e) {
            assertEquals(NumberFormatException.class, e.getClass());
        }
    }

    @Test
    public void testGetObject() {
        assertNull(jsonHandler.getObject("", "age", String.class));
        String text = "{\"abc\":\"123\",\"bcd\":{\"age\":18,\"name\":\"alex\",\"sex\":\"male\"}}";
        MockPerson person = jsonHandler.getObject(text, "bcd", MockPerson.class);
        assertEquals("alex", person.getName());
        assertEquals(18, person.getAge());
        assertEquals("male", person.getSex());

        text = "{\"abc\":\"123\",\"bcd\":\"{\\\"age\\\":18,\\\"name\\\":\\\"alex\\\",\\\"sex\\\":\\\"male\\\"}\"}";
        assertEquals("{\"age\":18,\"name\":\"alex\",\"sex\":\"male\"}",
                jsonHandler.getObject(text, "bcd", String.class));
    }

    @Test
    public void testGetList() {
        List<String> list = jsonHandler.getList("", "age", String.class);
        assertNotNull(list);
        assertThat(list.size(), is(0));

        String text = "{\"abc\":\"123\",\"bcd\":[{\"age\":18,\"name\":\"alex\",\"sex\":\"male\"}]}";
        List<MockPerson> persons = jsonHandler.getList(text, "bcd", MockPerson.class);
        assertEquals("alex", persons.get(0).getName());
        assertEquals(18, persons.get(0).getAge());
        assertEquals("male", persons.get(0).getSex());
    }

    @Test
    public void testAddProperty() {
        String text = "{\"a\":\"1\",\"b\":2,\"c\":\"3\"}";
        assertEquals("{\"a\":\"1\",\"b\":2,\"c\":\"3\",\"d\":\"4\"}", jsonHandler.addProperty(text, "d", "4"));
        assertEquals(
                "{\"a\":\"1\",\"b\":2,\"c\":\"3\",\"d\":\"{\\\"age\\\":18,\\\"name\\\":\\\"alex\\\",\\\"sex\\\":\\\"male\\\"}\"}",
                jsonHandler.addProperty(text, "d", new MockPerson("alex", 18, "male")));

        assertEquals("{\"d\":\"4\"}", jsonHandler.addProperty("", "d", "4"));
    }

    @Test
    public void testUpdateProperty() {
        String text = "{\"a\":\"1\",\"d\":\"{\\\"age\\\":18,\\\"name\\\":\\\"alex\\\",\\\"sex\\\":\\\"male\\\"}\"}";
        assertEquals("{\"a\":\"1\",\"d\":\"{\\\"age\\\":17,\\\"name\\\":\\\"alex\\\",\\\"sex\\\":\\\"male\\\"}\"}",
                jsonHandler.updateProperty(text, "d", new MockPerson("alex", 17, "male")));

        assertEquals(
                "{\"a\":\"1\",\"d\":\"{\\\"age\\\":18,\\\"name\\\":\\\"alex\\\",\\\"sex\\\":\\\"male\\\"}\",\"age\":10}",
                jsonHandler.updateProperty(text, "age", 10));
    }

    @Test
    public void testRemoveProperty() {
        String text = "{\"a\":\"1\",\"d\":\"{\\\"age\\\":18,\\\"name\\\":\\\"alex\\\",\\\"sex\\\":\\\"male\\\"}\"}";
        assertEquals("{\"a\":\"1\"}", jsonHandler.removeProperty(text, "d"));

        assertEquals(text, jsonHandler.removeProperty(text, "c"));

        assertEquals("{}", jsonHandler.removeProperty("", "c"));
    }

}
