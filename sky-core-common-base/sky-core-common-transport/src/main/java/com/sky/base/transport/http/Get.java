package com.sky.base.transport.http;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

/**
 * 
 * @title
 * @description
 * @author lizp
 * @version 1.0.0
 * @date 2019-05-07
 */
public class Get extends Request {

	public Get(String url) {
		super(url);
	}

	public Get(String url, Map<String, String> params) {
		super(url, params);
	}

	public Get(String url, Map<String, String> params, String charsetName) {
		super(url, params, charsetName);
	}

	@Override
	protected void init() {
		appendUrlParamsIfNecessary();
	}

	private void appendUrlParamsIfNecessary() {
		if (!params.isEmpty()) {
			int paramSplitIndex = StringUtils.indexOf(url, "?");
			if (paramSplitIndex == -1) {
				url = url + "?";
			} else {
				url = url + "&";
			}
			StringBuilder urlBuilder = new StringBuilder(url.length() + 128);
			urlBuilder.append(url);
			for (Entry<String, String> each : params.entrySet()) {
				urlBuilder.append(each.getKey()).append("=").append(each.getValue()).append("&");
			}
			urlBuilder.deleteCharAt(urlBuilder.length() - 1);
			url = urlBuilder.toString();
		}
	}

	@Override
	protected HttpUriRequest createRequest() throws IOException {
		logger.info("Http get request url -> {}", url);
		return new HttpGet(url);
	}

}
