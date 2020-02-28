package com.sky.base.lock.support;

import java.util.concurrent.TimeUnit;

import com.sky.base.lock.api.DistributedLock;
import com.sky.base.lock.exception.LockException;
import com.sky.base.lock.exception.TryLockException;

/**
 * 
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-02-26
 */
public abstract class LockOperator<T> {

	private String lockKey;

	private DistributedLock lock;

	private long timeout = TimeUnit.SECONDS.toMillis(3);

	protected abstract T doExecute();

	public T tryLockExecute() throws TryLockException {
		if (lock.tryLock(lockKey)) {
			try {
				return doExecute();
			} finally {
				lock.unLock(lockKey);
			}
		} else {
			throw new TryLockException("Try lock " + lockKey + " failed");
		}
	}

	public T tryLockWithTimeoutExecute() throws TryLockException {
		if (lock.tryLock(lockKey, timeout)) {
			try {
				return doExecute();
			} finally {
				lock.unLock(lockKey);
			}
		} else {
			throw new TryLockException("Try lock " + lockKey + " failed");
		}

	}

	public T lockExecute() throws LockException {
		if (lock.lock(lockKey)) {
			try {
				return doExecute();
			} finally {
				lock.unLock(lockKey);
			}
		} else {
			throw new LockException("Lock " + lockKey + " failed");
		}
	}

	public LockOperator(DistributedLock lock, String lockKey) {
		super();
		this.lock = lock;
		this.lockKey = lockKey;
	}

	public LockOperator(DistributedLock lock, String lockKey, long timeout) {
		this(lock, lockKey);
		this.timeout = timeout;
	}

}
