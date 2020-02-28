package com.sky.base.lock.exception;

/**
 * 
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-02-26
 */
public class TryLockException extends LockException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TryLockException() {
		super();
	}

	public TryLockException(String message, Throwable cause) {
		super(message, cause);
	}

	public TryLockException(String message) {
		super(message);
	}

	public TryLockException(Throwable cause) {
		super(cause);
	}

}
