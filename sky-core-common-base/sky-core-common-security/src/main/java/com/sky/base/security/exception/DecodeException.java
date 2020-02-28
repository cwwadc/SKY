package com.sky.base.security.exception;

/**
 * @Title 自定义异常：解码异常
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-02-25
 */
public class DecodeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5234011720785227794L;

	public DecodeException() {
		super();
	}

	public DecodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public DecodeException(String message) {
		super(message);
	}

	public DecodeException(Throwable cause) {
		super(cause);
	}

}
