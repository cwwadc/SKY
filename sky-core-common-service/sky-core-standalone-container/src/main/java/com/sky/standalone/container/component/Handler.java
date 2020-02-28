package com.sky.standalone.container.component;

@FunctionalInterface
public interface Handler<T> {

    void handle(T result);
}
