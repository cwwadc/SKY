package com.sky.base.context.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-13
 */
@ComponentScan("com.sky.base.context.configuration")
@ImportResource("com/sky/base/context/configuration/MainScanConfigurationTestOfScan.xml")
@Configuration
public class MockConfigurationOfScan {

}