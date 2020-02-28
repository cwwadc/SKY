package com.sky.service.api.exception;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = -6645703857369835018L;
	private String messageKey = null;
	private String errorNum;

	public ApiException() {
		super();
	}

	public ApiException(String msg) {
		super(msg);
	}

	public ApiException(int errorNum, String msg) {
		super(msg);
		this.errorNum = String.valueOf(errorNum);
	}

	public ApiException(String msg, Exception ex) {
		super(msg, ex);
	}

	public ApiException(String errorNum, String msg) {
		super(msg);
		this.errorNum = errorNum;
	}

	public String getErrorNum() {
		return errorNum;
	}

	public void setErrorNum(int errorNum) {
		this.errorNum = String.valueOf(errorNum);
	}

	public ApiException(Throwable rootCause) {
		super(rootCause);
	}

	public void setMessageKey(String key) {
		this.messageKey = key;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public void setRootCause(Throwable anException) {
		super.initCause(anException);
	}

	public Throwable getRootCause() {
		return super.getCause();
	}
}
