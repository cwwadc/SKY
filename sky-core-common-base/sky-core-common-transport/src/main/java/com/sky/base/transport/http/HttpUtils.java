package com.sky.base.transport.http;

import java.io.IOException;
import java.util.Map;

/**
 * @Title http工具类
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-02-20
 */
public class HttpUtils {

	static final String DEFAULT_CHARSET = "UTF-8";

	public static String post(String url) throws IOException {
		return new Post(url).execute();
	}

	public static String post(String url, Map<String, String> params) throws IOException {
		return new Post(url, params).execute();
	}

	public static String post(String url, Map<String, String> params, String charsetName) throws IOException {
		return new Post(url, params, charsetName).execute();
	}

	public static String post(String url, String content) throws IOException {
		return new StringPost(url, content).execute();
	}

	public static String post(String url, String content, String charsetName) throws IOException {
		return new StringPost(url, content, charsetName).execute();
	}

	public static String get(String url) throws IOException {
		return new Get(url).execute();
	}

	public static String get(String url, Map<String, String> params) throws IOException {
		return new Get(url, params).execute();
	}

	public static String get(String url, Map<String, String> params, String charsetName) throws IOException {
		return new Get(url, params, charsetName).execute();
	}

}
