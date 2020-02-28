package com.sky.base.context.spring;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-20
 */
public class MockBean2 extends BaseMockBean implements MockAgeService {

    private String name;

    private int age;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MockBean2 [name=");
        builder.append(name);
        builder.append(", age=");
        builder.append(age);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int age() {
        return age;
    }

    @Override
    public String myName() {
        return name;
    }

}
