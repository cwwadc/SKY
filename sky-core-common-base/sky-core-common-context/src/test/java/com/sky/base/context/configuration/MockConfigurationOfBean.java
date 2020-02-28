package com.sky.base.context.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sky.base.context.spring.MockBean;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-13
 */
@Configuration
public class MockConfigurationOfBean {

    @Bean
    public MockBean mockBean() {
        MockBean mockBean = new MockBean();
        mockBean.setName("test");
        return mockBean;
    }

}