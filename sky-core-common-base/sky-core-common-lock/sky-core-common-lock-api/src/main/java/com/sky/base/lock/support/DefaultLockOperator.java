package com.sky.base.lock.support;

import java.util.function.Supplier;

import com.sky.base.lock.api.DistributedLock;

/**
 * 
 * @title
 * @description
 * @author lizp
 * @version 1.0.0
 * @date 2019-05-29
 */
public class DefaultLockOperator<T> extends LockOperator<T> {

	private final Supplier<T> supplier;

	public DefaultLockOperator(DistributedLock lock, String lockKey, Supplier<T> supplier) {
		super(lock, lockKey);
		this.supplier = supplier;
	}

	public DefaultLockOperator(DistributedLock lock, String lockKey, long timeout, Supplier<T> supplier) {
		super(lock, lockKey, timeout);
		this.supplier = supplier;
	}

	@Override
	protected T doExecute() {
		return supplier.get();
	}

}
