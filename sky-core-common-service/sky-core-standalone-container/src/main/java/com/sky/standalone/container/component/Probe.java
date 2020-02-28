package com.sky.standalone.container.component;

import java.util.concurrent.CompletableFuture;

public interface Probe {

    String id();

    CompletableFuture<Result> check();
}
