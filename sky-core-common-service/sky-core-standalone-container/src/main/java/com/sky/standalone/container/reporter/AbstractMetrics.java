
package com.sky.standalone.container.reporter;

import java.time.Instant;

public abstract class AbstractMetrics implements Reportable {

    private final long timestamp;

    public AbstractMetrics(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public Instant timestamp() {
        return Instant.ofEpochMilli(timestamp);
    }
}
