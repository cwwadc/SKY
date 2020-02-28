package com.sky.base.context.spring;

/**
 * @author zzq
 * @version 1.0.0
 * @Title {@link}
 * @Description
 * @date 2019/2/15
 */
public class MockTest2 {

    private String a;
    private String b;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MockTest2{");
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
