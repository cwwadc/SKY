package com.sky.core;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
@ComponentScan(basePackages = {"com.sky.core", "com.sky.base.context.spring"})
@PropertySource(value = "classpath:application.yml")
@EnableRabbit
public class SkyWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkyWebApplication.class, args);

//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SkyWebApplication.class);
//        System.out.println("rabbit service startup");
//        TimeUnit.SECONDS.sleep(200);
//        context.close();


    }

}
