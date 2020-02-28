package com.sky.base.lock.exception;

/**
 * 
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-02-26
 */
public class LockException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LockException() {
		super();
	}

	public LockException(String message, Throwable cause) {
		super(message, cause);
	}

	public LockException(String message) {
		super(message);
	}

	public LockException(Throwable cause) {
		super(cause);
	}

}
