package com.sky.base.lang.exception;

/**
 * 
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-03-09
 */
public class UrsaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String code;

	public UrsaException() {
		super();
	}

	public UrsaException(String message, Throwable cause) {
		super(message, cause);
	}

	public UrsaException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public UrsaException(String message) {
		super(message);
	}

	public UrsaException(String code, String message) {
		super(message);
		this.code = code;
	}

	public UrsaException(Throwable cause) {
		super(cause);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
