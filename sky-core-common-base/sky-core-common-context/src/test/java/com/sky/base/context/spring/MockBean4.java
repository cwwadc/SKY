package com.sky.base.context.spring;

import org.springframework.stereotype.Component;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-07
 */
@Component("mockBean4")
public class MockBean4 {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MockBean [name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }

}
