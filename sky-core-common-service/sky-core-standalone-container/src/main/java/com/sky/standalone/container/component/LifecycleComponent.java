package com.sky.standalone.container.component;

public interface LifecycleComponent<T> {

  Lifecycle.State lifecycleState();

  T start() throws Exception;

  T stop() throws Exception;
}
