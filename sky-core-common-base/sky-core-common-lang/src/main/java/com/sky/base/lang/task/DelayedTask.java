package com.sky.base.lang.task;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedTask<T extends Runnable> implements Delayed {
	
	private String id;
	private long deadline;
	private T task;

	public DelayedTask() {
	}

	public DelayedTask(String id, T runnable, long delayTime, TimeUnit delayTimeUnit) {
		this.deadline = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(delayTime, delayTimeUnit);
		this.task = runnable;
		this.id = id;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int compareTo(Delayed o) {
		long diff;
		if (o instanceof DelayedTask) {
			diff = this.deadline - ((DelayedTask) o).getDeadline();
		} else {
			diff = this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
		}
		return (diff == 0) ? 0 : ((diff < 0) ? -1 : 1);
	}

	@Override
	public long getDelay(TimeUnit unit) {
		long delay = unit.convert(deadline - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		return delay;
	}

	public long getDeadline() {
		return deadline;
	}

	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}

	public T getTask() {
		return task;
	}

	public void setTask(T task) {
		this.task = task;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
    public int hashCode() {
        return task.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof DelayedTask) {
            return object.hashCode() == hashCode() ? true : false;
        }
        return false;
    }
}
