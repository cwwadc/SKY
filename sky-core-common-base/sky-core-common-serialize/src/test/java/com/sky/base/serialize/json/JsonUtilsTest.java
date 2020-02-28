package com.sky.base.serialize.json;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSONException;
import com.sky.base.serialize.AbstractSerializeTest;
import com.sky.base.serialize.json.JsonUtils;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-10
 */
public class JsonUtilsTest extends AbstractSerializeTest {

    @Test
    public void testToJsonString() {
        MockPerson mockPerson = new MockPerson("alex", 18, "male");
        String text = JsonUtils.toJsonString(mockPerson);
        assertEquals("{\"age\":18,\"name\":\"alex\",\"sex\":\"male\"}", text);
    }

    @Test
    public void testParse() {
        String text = "{\"age\":13,\"name\":\"li\",\"sex\":\"male\"}";
        MockPerson person = JsonUtils.parse(text, MockPerson.class);
        assertEquals("li", person.getName());
        assertEquals(13, person.getAge());
        assertEquals("male", person.getSex());
    }

    @Test
    public void testFormat() {
        String expectedText = "{\n\t\"sex\":\"male\",\n\t\"name\":\"li\",\n\t\"age\":13\n}";
        String text = "{\"age\":13,\"name\":\"li\",\"sex\":\"male\"}";
        String newText = JsonUtils.format(text);
        assertEquals(expectedText, newText);
    }

    @Test
    public void testIsJson() {
        assertTrue(JsonUtils.isJson("{\"age\":13,\"name\":\"li\"}"));
        assertTrue(JsonUtils.isJson("[{\"age\":13,\"name\":\"li\"}]"));
        assertTrue(JsonUtils.isJson("{}"));
        assertFalse(JsonUtils.isJson("{\"age\":13,\"name\":\"li\"}test"));
        assertFalse(JsonUtils.isJson("123"));
        assertFalse(JsonUtils.isJson("[\"age\":13,\"name\":\"li\"]"));
    }

    @Test
    public void testGetString() {
        assertNull(JsonUtils.getString("", "age"));
        assertEquals("13", JsonUtils.getString("{\"age\":13,\"name\":\"li\"}", "age"));
        assertEquals("li", JsonUtils.getString("{\"age\":13,\"name\":\"li\"}", "name"));
        assertNull(JsonUtils.getString("{\"age\":13,\"name\":\"li\"}", "abc"));
    }

    @Test
    public void testGetInteger() {
        assertNull(JsonUtils.getInteger("", "age"));
        assertEquals(new Integer(13), JsonUtils.getInteger("{\"age\":13}", "age"));
        assertEquals(new Integer(13), JsonUtils.getInteger("{\"age\":\"13\"}", "age"));
        assertNull(JsonUtils.getInteger("{\"age\":13}", "abc"));

        try {
            assertEquals("li", JsonUtils.getInteger("{\"age\":13,\"name\":\"li\"}", "name"));
            fail("expected error");
        } catch (Exception e) {
            assertEquals(NumberFormatException.class, e.getClass());
        }
    }

    @Test
    public void testGetLong() {
        assertNull(JsonUtils.getLong("", "age"));
        assertEquals(new Long(13), JsonUtils.getLong("{\"age\":13}", "age"));
        assertEquals(new Long(13), JsonUtils.getLong("{\"age\":\"13\"}", "age"));
        assertNull(JsonUtils.getLong("{\"age\":13}", "abc"));

        try {
            assertEquals("li", JsonUtils.getLong("{\"name\":\"li\"}", "name"));
            fail("expected error");
        } catch (Exception e) {
            assertEquals(JSONException.class, e.getClass());
        }
    }

    @Test
    public void testGetDouble() {
        assertNull(JsonUtils.getDouble("", "age"));
        assertEquals(new Double(13.2D), JsonUtils.getDouble("{\"age\":13.2,\"name\":\"li\"}", "age"));
        assertEquals(new Double(13.2D), JsonUtils.getDouble("{\"age\":\"13.2\"}", "age"));
        assertNull(JsonUtils.getDouble("{\"age\":13.2}", "abc"));

        try {
            assertEquals("li", JsonUtils.getDouble("{\"age\":13.2,\"name\":\"li\"}", "name"));
            fail("expected error");
        } catch (Exception e) {
            assertEquals(NumberFormatException.class, e.getClass());
        }
    }

    @Test
    public void testGetBigInteger() {
        assertNull(JsonUtils.getBigInteger("", "age"));
        assertEquals(new BigInteger("100"), JsonUtils.getBigInteger("{\"age\":100,\"name\":\"li\"}", "age"));
        assertEquals(new BigInteger("100"), JsonUtils.getBigInteger("{\"age\":\"100\",\"name\":\"li\"}", "age"));
        assertNull(JsonUtils.getBigInteger("{\"age\":100,\"name\":\"li\"}", "abc"));

        try {
            assertEquals("li", JsonUtils.getBigInteger("{\"age\":100,\"name\":\"li\"}", "name"));
            fail("expected error");
        } catch (Exception e) {
            assertEquals(NumberFormatException.class, e.getClass());
        }
    }

    @Test
    public void testGetBigDecimal() {
        assertNull(JsonUtils.getBigDecimal("", "age"));
        assertEquals(new BigDecimal("100.2"), JsonUtils.getBigDecimal("{\"age\":100.2,\"name\":\"li\"}", "age"));
        assertEquals(new BigDecimal("100.2"), JsonUtils.getBigDecimal("{\"age\":\"100.2\",\"name\":\"li\"}", "age"));
        assertNull(JsonUtils.getBigDecimal("{\"age\":100.2,\"name\":\"li\"}", "abc"));

        try {
            assertEquals("li", JsonUtils.getBigDecimal("{\"age\":100.2,\"name\":\"li\"}", "name"));
            fail("expected error");
        } catch (Exception e) {
            assertEquals(NumberFormatException.class, e.getClass());
        }
    }

    @Test
    public void testGetBoolean() {
        assertNull(JsonUtils.getBoolean("", "age"));
        assertFalse(JsonUtils.getBoolean("{\"flag\":0,\"name\":\"li\"}", "flag"));
        assertTrue(JsonUtils.getBoolean("{\"flag\":1,\"name\":\"li\"}", "flag"));
        assertFalse(JsonUtils.getBoolean("{\"flag\":\"false\",\"name\":\"li\"}", "flag"));
        assertTrue(JsonUtils.getBoolean("{\"flag\":\"true\",\"name\":\"li\"}", "flag"));
        assertNull(JsonUtils.getBoolean("{\"age\":100.2,\"name\":\"li\"}", "abc"));

        try {
            assertEquals("li", JsonUtils.getBoolean("{\"age\":100.2,\"name\":\"li\"}", "name"));
            fail("expected error");
        } catch (Exception e) {
            assertEquals(JSONException.class, e.getClass());
        }
    }

    @Test
    public void testGetByte() {
        assertNull(JsonUtils.getByte("", "age"));
        assertEquals(new Byte("100"), JsonUtils.getByte("{\"age\":100,\"name\":\"li\"}", "age"));
        assertEquals(new Byte("99"), JsonUtils.getByte("{\"age\":\"99\",\"name\":\"li\"}", "age"));
        assertNull(JsonUtils.getByte("{\"age\":100.2,\"name\":\"li\"}", "abc"));

        try {
            assertEquals("li", JsonUtils.getByte("{\"age\":100,\"name\":\"li\"}", "name"));
            fail("expected error");
        } catch (Exception e) {
            assertEquals(NumberFormatException.class, e.getClass());
        }
    }

    @Test
    public void testGetObject() {
        assertNull(JsonUtils.getObject("", "age", String.class));
        String text = "{\"abc\":\"123\",\"bcd\":{\"age\":18,\"name\":\"alex\",\"sex\":\"male\"}}";
        MockPerson person = JsonUtils.getObject(text, "bcd", MockPerson.class);
        assertEquals("alex", person.getName());
        assertEquals(18, person.getAge());
        assertEquals("male", person.getSex());

        text = "{\"abc\":\"123\",\"bcd\":\"{\\\"age\\\":18,\\\"name\\\":\\\"alex\\\",\\\"sex\\\":\\\"male\\\"}\"}";
        assertEquals("{\"age\":18,\"name\":\"alex\",\"sex\":\"male\"}", JsonUtils.getObject(text, "bcd", String.class));
    }

    @Test
    public void testGetList() {
        List<String> list = JsonUtils.getList("", "age", String.class);
        assertNotNull(list);
        assertThat(list.size(), is(0));

        String text = "{\"abc\":\"123\",\"bcd\":[{\"age\":18,\"name\":\"alex\",\"sex\":\"male\"}]}";
        List<MockPerson> persons = JsonUtils.getList(text, "bcd", MockPerson.class);
        assertEquals("alex", persons.get(0).getName());
        assertEquals(18, persons.get(0).getAge());
        assertEquals("male", persons.get(0).getSex());
    }

    @Test
    public void testAddProperty() {
        String text = "{\"a\":\"1\",\"b\":2,\"c\":\"3\"}";
        assertEquals("{\"a\":\"1\",\"b\":2,\"c\":\"3\",\"d\":\"4\"}", JsonUtils.addProperty(text, "d", "4"));
        assertEquals(
                "{\"a\":\"1\",\"b\":2,\"c\":\"3\",\"d\":\"{\\\"age\\\":18,\\\"name\\\":\\\"alex\\\",\\\"sex\\\":\\\"male\\\"}\"}",
                JsonUtils.addProperty(text, "d", new MockPerson("alex", 18, "male")));

        assertEquals("{\"d\":\"4\"}", JsonUtils.addProperty("", "d", "4"));
    }

    @Test
    public void testUpdateProperty() {
        String text = "{\"a\":\"1\",\"d\":\"{\\\"age\\\":18,\\\"name\\\":\\\"alex\\\",\\\"sex\\\":\\\"male\\\"}\"}";
        assertEquals("{\"a\":\"1\",\"d\":\"{\\\"age\\\":17,\\\"name\\\":\\\"alex\\\",\\\"sex\\\":\\\"male\\\"}\"}",
                JsonUtils.updateProperty(text, "d", new MockPerson("alex", 17, "male")));

        assertEquals(
                "{\"a\":\"1\",\"d\":\"{\\\"age\\\":18,\\\"name\\\":\\\"alex\\\",\\\"sex\\\":\\\"male\\\"}\",\"age\":10}",
                JsonUtils.updateProperty(text, "age", 10));
    }

    @Test
    public void testRemoveProperty() {
        String text = "{\"a\":\"1\",\"d\":\"{\\\"age\\\":18,\\\"name\\\":\\\"alex\\\",\\\"sex\\\":\\\"male\\\"}\"}";
        assertEquals("{\"a\":\"1\"}", JsonUtils.removeProperty(text, "d"));

        assertEquals(text, JsonUtils.removeProperty(text, "c"));

        assertEquals("{}", JsonUtils.removeProperty("", "c"));
    }

    @Test(expected = InvocationTargetException.class)
    public void testNewInstance() throws Exception {
        assertInvokeCanNotInstanceClassConstructor(JsonUtils.class, "Utility class");
    }

}
