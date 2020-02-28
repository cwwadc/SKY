package com.sky.standalone.container.component;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface Processor<T> extends Handler<T> {

    Processor<T> handler(Handler<T> handler);

    Processor<T> errorHandler(Handler<ProcessorFailure> handler);

    Processor<T> exitHandler(Handler<Void> handler);
}
