package com.sky.standalone.container.component;

import java.util.Map;

public interface ProcessorFailure {

    int statusCode();

    String message();

    String key();

    Map<String, Object> parameters();

    String contentType();
}
