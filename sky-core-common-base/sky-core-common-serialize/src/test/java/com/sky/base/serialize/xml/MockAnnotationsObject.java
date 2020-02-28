package com.sky.base.serialize.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-12
 */
@XStreamAlias("xml")
public class MockAnnotationsObject {
    @XStreamAlias("myname")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}