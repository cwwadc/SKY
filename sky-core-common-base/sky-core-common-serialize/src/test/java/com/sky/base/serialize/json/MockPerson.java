package com.sky.base.serialize.json;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-07
 */
public class MockPerson {
    private String name;
    private int age;
    private String sex;

    public MockPerson() {
        super();
    }

    public MockPerson(String name, int age, String sex) {
        super();
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MockPerson [name=");
        builder.append(name);
        builder.append(", age=");
        builder.append(age);
        builder.append(", sex=");
        builder.append(sex);
        builder.append("]");
        return builder.toString();
    }

}