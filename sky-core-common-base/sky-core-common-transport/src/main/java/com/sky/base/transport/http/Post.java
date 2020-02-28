package com.sky.base.transport.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;

/**
 * 
 * @title
 * @description
 * @author lizp
 * @version 1.0.0
 * @date 2019-05-07
 */
public class Post extends Request {

	public Post(String url) {
		super(url);
	}

	public Post(String url, Map<String, String> params) {
		super(url, params);
	}

	public Post(String url, Map<String, String> params, String charsetName) {
		super(url, params, charsetName);
	}

	@Override
	protected void init() {
		int paramSplitIndex = StringUtils.indexOf(url, "?");
		if (paramSplitIndex != -1) {
			String paramsString = StringUtils.substring(url, paramSplitIndex + 1);
			populateParams(paramsString);
			url = StringUtils.substring(url, 0, paramSplitIndex);
		}
	}

	public void populateParams(String paramsString) {
		StringTokenizer st = new StringTokenizer(paramsString, "&");
		int i;
		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			i = StringUtils.indexOf(s, "=");
			if (i > 0 && s.length() >= i + 1) {
				String name = StringUtils.substring(s, 0, i);
				String value = StringUtils.substring(s, i + 1);
				params.put(decode(name), decode(value));
			} else if (i == -1) {
				String name = s;
				String value = "";
				params.put(decode(name), value);
			}
		}
	}

	@Override
	protected HttpUriRequest createRequest() throws IOException {
		logger.info("Http post request url -> {}, params -> {}", url, params);
		List<NameValuePair> nvps = buildNameValuePairs(params);
		HttpPost httpPost = new HttpPost(url);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps, getCharsetName());
		httpPost.setEntity(entity);
		return httpPost;
	}

	private List<NameValuePair> buildNameValuePairs(Map<String, String> params) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (params == null || params.isEmpty()) {
			return nvps;
		}
		for (Entry<String, String> each : params.entrySet()) {
			nvps.add(new BasicNameValuePair(each.getKey(), each.getValue()));
		}
		return nvps;
	}

}
