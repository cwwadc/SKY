package com.sky.base.cache.redis.bean;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author zzq
 * @version v1.0
 * @Title
 * @Description
 * @date 2018/12/13
 */
public class MockObjectTest implements Serializable {

    private static final long serialVersionUID = 3502063305692175437L;
    private String name;
    private Integer age;

    public static MockObjectTest newInstance() {
        MockObjectTest test = new MockObjectTest();
        test.setName("test");
        test.setAge(24);
        return test;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MockObjectTest{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age='").append(age).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public MockObjectTest setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public MockObjectTest setAge(Integer age) {
        this.age = age;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MockObjectTest test = (MockObjectTest) o;
        return Objects.equals(name, test.name) &&
                Objects.equals(age, test.age);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, age);
    }
}
