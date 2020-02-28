package com.sky.base.context.spring;

import static com.sky.base.test.util.MatcherUtils.is;
import static org.junit.Assert.*;

import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.Resource;

import com.sky.base.context.container.MainTest;
import com.sky.base.context.spring.SpringContext;
import com.sky.base.test.matcher.StringArrayContainMatcher;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-07
 */
@Ignore
public class SpringContextTest {

    @Test
    public void testGetBeanString() {
        MainTest.start("com/sky/base/context/spring/SpringContextTest.xml");
        MockBean mockBean = SpringContext.getBean("mockBean");
        assertNotNull(mockBean);
        assertEquals("test", mockBean.getName());
    }

    @Test
    public void testGetBeanStringOfScan() {
        MainTest.start("com/sky/base/context/spring/SpringContextTestOfScan.xml");
        MockBean mockBean = SpringContext.getBean("mockBean");
        assertNotNull(mockBean);
        assertEquals("test", mockBean.getName());
    }

    @Test
    public void testGetBeanClassOfT() {
        MainTest.start("com/sky/base/context/spring/SpringContextTest.xml");
        MockBean mockBean = SpringContext.getBean(MockBean.class);
        assertNotNull(mockBean);
        assertEquals("test", mockBean.getName());
    }

    @Test
    public void testGetBeanClassOfTObjectArray() {
        MainTest.start("com/sky/base/context/spring/SpringContextTest.xml");
        MockBeanOfConstruct mockBean = SpringContext.getBean(MockBeanOfConstruct.class, "test3", 20);
        assertNotNull(mockBean);
        assertEquals("test3", mockBean.getName());
        assertEquals(20, mockBean.getAge());
    }

    @Test
    public void testGetBeanStringClassOfT() {
        MainTest.start("com/sky/base/context/spring/SpringContextTest.xml");
        MockAgeService mockAgeService = SpringContext.getBean("mockBean2", MockAgeService.class);
        assertNotNull(mockAgeService);
        assertEquals(12, mockAgeService.age());

        BaseMockBean baseMockBean = SpringContext.getBean("mockBean2", BaseMockBean.class);
        assertNotNull(baseMockBean);
        assertEquals("test2", baseMockBean.myName());
    }

    @Test
    public void testGetBeanStringObjectArray() {
        MainTest.start("com/sky/base/context/spring/SpringContextTest.xml");
        MockBeanOfConstruct mockBean = (MockBeanOfConstruct) SpringContext.getBean("mockBeanOfConstruct", "test4", 18);
        assertNotNull(mockBean);
        assertEquals("test4", mockBean.getName());
        assertEquals(18, mockBean.getAge());
    }

    @Test
    public void testGetBeansOfType() {
        MainTest.start("com/sky/base/context/spring/SpringContextTest.xml");
        Map<String, MockBean3> map = SpringContext.getBeansOfType(MockBean3.class);
        assertThat(map.size(), is(2));
        MockBean3 mockBean33 = map.get("mockBean33");
        assertNotNull(mockBean33);
        assertEquals("test33", mockBean33.getName());
        MockBean3 mockBean333 = map.get("mockBean333");
        assertNotNull(mockBean333);
        assertEquals("test333", mockBean333.getName());
    }

    @Test
    public void testGetBeansWithAnnotation() {
        MainTest.start("com/sky/base/context/spring/SpringContextTestOfScan.xml");
        Map<String, Object> map = SpringContext.getBeansWithAnnotation(MockCustomAnnotation.class);
        assertThat(map.size(), is(2));
        MockBean5 mockBean5 = (MockBean5) map.get("mockBean5");
        assertNotNull(mockBean5);
        MockBean6 mockBean6 = (MockBean6) map.get("mockBean6");
        assertNotNull(mockBean6);
    }

    @Test
    public void testGetMessageStringObjectArrayLocale() {
        MainTest.start("com/sky/base/context/spring/SpringContextTestOfMessages.xml");
        String companyName = SpringContext.getMessage("company.name", null, Locale.SIMPLIFIED_CHINESE);
        assertEquals("通联支付", companyName);

        companyName = SpringContext.getMessage("company.name", null, Locale.US);
        assertEquals("allinpay", companyName);

        String argumentRequired = SpringContext.getMessage("argument.required", ArrayUtils.toArray("id"),
                Locale.SIMPLIFIED_CHINESE);
        assertEquals("参数 id 不能为空。", argumentRequired);

        argumentRequired = SpringContext.getMessage("argument.required", ArrayUtils.toArray("name"), Locale.US);
        assertEquals("The name argument is required.", argumentRequired);
    }

    @Test
    public void testGetMessageStringObjectArrayStringLocale() {
        MainTest.start("com/sky/base/context/spring/SpringContextTestOfMessages.xml");
        String companyName = SpringContext.getMessage("mycompany.name", null, "通联", Locale.SIMPLIFIED_CHINESE);
        assertEquals("通联", companyName);

        companyName = SpringContext.getMessage("mycompany.name", null, "TL", Locale.US);
        assertEquals("TL", companyName);

        String argumentRequired = SpringContext.getMessage("argument.required2", null, "参数不能为空",
                Locale.SIMPLIFIED_CHINESE);
        assertEquals("参数不能为空", argumentRequired);

        argumentRequired = SpringContext.getMessage("argument.required3", ArrayUtils.toArray("name"),
                "Argument {0} is required.", Locale.US);
        assertEquals("Argument name is required.", argumentRequired);
    }

    @Test
    public void testGetResource() {
        MainTest.start("com/sky/base/context/spring/SpringContextTestOfScan.xml");
        Resource resource = SpringContext.getResource("com/sky/base/context/spring/spring-context-test.properties");
        assertNotNull(resource);
    }

    @Test
    public void testGetBeanDefinitionNames() {
        MainTest.start("com/sky/base/context/spring/SpringContextTest.xml");
        String[] names = SpringContext.getBeanDefinitionNames();
        assertThat(names, new StringArrayContainMatcher(new String[] { "mockBean" }));
    }

    @Test
    public void testGetProperty() {
        MainTest.start("com/sky/base/context/spring/SpringContextTestOfProperties.xml");
        String name = SpringContext.getProperty("spring.test.name");
        assertEquals("li", name);
        String age = SpringContext.getProperty("spring.test.age");
        assertEquals("19", age);
    }
}
