package com.sky.service.api.support.utils;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.base.lang.string.StringUtils;
import com.sky.base.security.hex.HexUtils;

public class ApiMsgUtils {
	private static final Logger logger = LoggerFactory.getLogger(ApiMsgUtils.class);
	
	public static String newTraceId() {
		return newTraceId(null);
	}
	
	public static String newTraceId(String appName) {
		if (!StringUtils.isEmpty(appName)) {
			String appNameHex = HexUtils.toHexString(appName);
			return appNameHex + "-" + UUID.randomUUID().toString();
		} else {
			return UUID.randomUUID().toString();
		}
		
	}

}
