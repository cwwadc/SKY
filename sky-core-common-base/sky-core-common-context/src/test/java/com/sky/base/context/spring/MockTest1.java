package com.sky.base.context.spring;

/**
 * @author zzq
 * @version 1.0.0
 * @Title {@link}
 * @Description
 * @date 2019/2/15
 */
public class MockTest1 {
    private String a;
    private String b;

    public MockTest1(String a, String b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MockTest1{");
        sb.append("a='").append(a).append('\'');
        sb.append(", b='").append(b).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }
}
