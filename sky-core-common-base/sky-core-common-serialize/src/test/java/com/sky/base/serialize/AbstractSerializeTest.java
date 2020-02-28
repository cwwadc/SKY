package com.sky.base.serialize;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;

import org.hamcrest.Matcher;

import com.sky.base.test.util.MatcherUtils;



/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-12
 */
public abstract class AbstractSerializeTest {

    protected <T> Matcher<T> is(T value) {
        return MatcherUtils.is(value);
    }

    protected void assertInvokeCanNotInstanceClassConstructor(Class<?> c, String errorMessage) throws Exception {
        try {
            Constructor<?> constructor = c.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        } catch (Exception e) {
            assertTrue(IllegalStateException.class.isAssignableFrom(e.getCause().getClass()));
            assertEquals(errorMessage, e.getCause().getMessage());
            throw e;
        }
    }

}
