package com.sky.standalone.jms.connector.webspheremq;

import java.util.concurrent.atomic.AtomicLong;

public class MQApiRoundup {
	private AtomicLong running = new AtomicLong(0L);
	
	public Long addRunning() {
		return this.running.incrementAndGet();
	}
	
	public Long decRunning() {
		return this.running.decrementAndGet();
	}
	
	public Long getRunning() {
		return this.running.longValue();
	}
	
	public void setRunning(Long running) {
		this.running.set(running);
	}
	
}
