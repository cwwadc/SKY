package com.sky.base.lang.exception;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @Title 异常处理类
 * @Description
 * @author dengny
 * @version 1.0.0
 * @date 2018-12-13
 *
 */
public class UrsaRuntimeException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9071214341422897151L;
	private String id;
	private String message;
	protected String realClassName;

	public UrsaRuntimeException() {
		this.initId();
	}

	public UrsaRuntimeException(String message, Object... args) {
		this.setMessageFormat(message, args);
		this.initId();
	}

	public UrsaRuntimeException(Throwable throwable) {
		this.setMessage(throwable.getMessage());
		this.setStackTrace(throwable.getStackTrace());
		this.realClassName = throwable.getClass().getName();
		this.initId();
	}

	public UrsaRuntimeException(String message, Throwable throwable, Object... args) {
		this.setMessageFormat(message, args);
		this.setStackTrace(throwable.getStackTrace());
		this.realClassName = throwable.getClass().getName();
		this.initId();
	}

	private void initId() {
		this.id = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
	}

	public String getId() {
		return this.id;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	private void setMessage(String message) {
		this.message = message;
	}

	private void setMessageFormat(String message, Object... args) {
		if (message != null && args != null) {
			this.setMessage(MessageFormat.format(message, args));
		} else {
			this.setMessage(message);
		}

	}

	public String getRealClassName() {
		return this.realClassName == null ? this.getClass().getName() : this.realClassName;
	}

	public void setRealClassName(String realClassName) {
		this.realClassName = realClassName;
	}

	public void mergeStackTrace(StackTraceElement[] stackTrace) {
		this.setStackTrace((StackTraceElement[]) ArrayUtils.addAll(this.getStackTrace(), stackTrace));
	}

	public StackTraceElement[] getCoreStackTrace() {
		ArrayList<StackTraceElement> list = new ArrayList<StackTraceElement>();
		StackTraceElement[] arg4;
		int arg3 = (arg4 = this.getStackTrace()).length;

		for (int arg2 = 0; arg2 < arg3; ++arg2) {
			StackTraceElement stackTrace = arg4[arg2];
			if (stackTrace.getClassName().startsWith("com.ursa")) {
				list.add(stackTrace);
			}
		}

		StackTraceElement[] arg5 = new StackTraceElement[list.size()];
		return (StackTraceElement[]) list.toArray(arg5);
	}

	public String getCoreStackTraceStr() {
		StringBuffer sb = new StringBuffer();
		StackTraceElement[] arg4;
		int arg3 = (arg4 = this.getCoreStackTrace()).length;

		for (int arg2 = 0; arg2 < arg3; ++arg2) {
			StackTraceElement traceEle = arg4[arg2];
			sb.append("\n" + traceEle.toString());
		}

		return sb.toString();
	}

}
