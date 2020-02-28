package com.sky.standalone.container.component;

public abstract class AbstractProcessor<T> implements Processor<T> {

    protected Handler<T> next;
    protected Handler<Void> exitHandler;
    protected Handler<ProcessorFailure> errorHandler;

    @Override
    public Processor<T> handler(Handler<T> handler) {
        this.next = handler;
        return this;
    }

    @Override
    public Processor<T> errorHandler(Handler<ProcessorFailure> errorHandler) {
        this.errorHandler = errorHandler;
        return this;
    }

    @Override
    public Processor<T> exitHandler(Handler<Void> exitHandler) {
        this.exitHandler = exitHandler;
        return this;
    }
}
