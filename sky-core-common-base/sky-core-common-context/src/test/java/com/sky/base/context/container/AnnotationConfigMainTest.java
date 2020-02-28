package com.sky.base.context.container;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;

import com.sky.base.context.configuration.MockConfigurationOfBean;
import com.sky.base.context.configuration.MockConfigurationOfImport;
import com.sky.base.context.configuration.MockConfigurationOfScan;
import com.sky.base.context.configuration.SpringContextConfiguration;
import com.sky.base.context.container.AnnotationConfigMain;
import com.sky.base.context.container.Main;
import com.sky.base.context.spring.MockBean;
import com.sky.base.context.spring.SpringContext;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-13
 */
@Ignore
public class AnnotationConfigMainTest {

    public static void start(String springConfigClass) {
        System.setProperty(Main.ENV_KEY, "dev");
        System.setProperty(AnnotationConfigMain.SPRING_CONFIG_CLASS_KEY, springConfigClass);
        AnnotationConfigMain.main(null);
    }

    @Test
    public void testAnnotationConfigApplicationContextOfImport() {
        start(MockConfigurationOfImport.class.getName());
        MockBean mockBean = SpringContext.getBean("mockBean");
        assertNotNull(mockBean);
        assertEquals("test", mockBean.getName());
    }

    @Test
    public void testAnnotationConfigApplicationContextOfScan() {
        start(MockConfigurationOfScan.class.getName());
        MockBean mockBean = SpringContext.getBean("mockBean");
        assertNotNull(mockBean);
        assertEquals("test", mockBean.getName());
    }

    @Test
    public void testAnnotationConfigApplicationContextOfMultiClass() {
        start(MockConfigurationOfBean.class.getName() + "," + SpringContextConfiguration.class.getName());
        MockBean mockBean = SpringContext.getBean("mockBean");
        assertNotNull(mockBean);
        assertEquals("test", mockBean.getName());
    }

}
