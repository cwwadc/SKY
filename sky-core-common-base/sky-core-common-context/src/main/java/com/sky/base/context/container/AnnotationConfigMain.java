package com.sky.base.context.container;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.sky.base.lang.string.StringUtils;

/**
 * @Title AnnotationConfigMain.
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-13
 */
public class AnnotationConfigMain extends Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationConfigMain.class);

    /** Spring配置环境变量类key : ursa.spring.config.class */
    public static final String SPRING_CONFIG_CLASS_KEY = "sky.spring.config.class";

    private static final String SPRING_CONFIG_CLASS_SEPARATOR = ",";

    public static void main(String[] args) {
        new AnnotationConfigMain().launch();
    }

    @Override
    protected AbstractApplicationContext customApplicationContext() throws Exception {
        String springConfigClass = System.getProperty(SPRING_CONFIG_CLASS_KEY);
        if (StringUtils.isBlank(springConfigClass)) {
            LOGGER.error("Please enter spring config parameter, -D{}=xxx.xxx.xxx", SPRING_CONFIG_CLASS_KEY);
            System.exit(1);
        }
        LOGGER.info("Use Spring config class --> {}", springConfigClass);

        List<Class<?>> classes = parseClass(springConfigClass);

        return new AnnotationConfigApplicationContext(classes.toArray(new Class<?>[classes.size()]));
    }

    private List<Class<?>> parseClass(String springConfigClass) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>(2);
        if (StringUtils.contains(springConfigClass, SPRING_CONFIG_CLASS_SEPARATOR)) {
            boolean containConfigClass = false;
            String[] classNames = StringUtils.splitByWholeSeparatorPreserveAllTokens(springConfigClass,
                    SPRING_CONFIG_CLASS_SEPARATOR);
            for (String className : classNames) {
                if (!StringUtils.isBlank(springConfigClass)) {
                    containConfigClass = true;
                    classes.add(Class.forName(className));
                }
            }
            if (!containConfigClass) {
                LOGGER.error("Please enter spring config parameter, -D{}=xxx.xxx,xxx.xxx", SPRING_CONFIG_CLASS_KEY);
                System.exit(1);
            }
        } else {
            classes.add(Class.forName(springConfigClass));
        }
        return classes;
    }
}
