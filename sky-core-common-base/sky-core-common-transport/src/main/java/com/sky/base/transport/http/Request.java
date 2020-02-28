package com.sky.base.transport.http;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @title
 * @description
 * @author lizp
 * @version 1.0.0
 * @date 2019-05-07
 */
public abstract class Request {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected String url;

	protected final Map<String, String> params = new HashMap<String, String>(16);

	protected String charsetName;

	public Request(String url) {
		super();
		this.url = url;
	}

	public Request(String url, Map<String, String> params) {
		this(url);
		this.params.putAll(params);
	}

	public Request(String url, Map<String, String> params, String charsetName) {
		this(url, params);
		this.charsetName = charsetName;
	}

	public String execute() throws IOException {
		init();
		return createRequestExecute();
	}

	protected abstract void init();

	private String createRequestExecute() throws IOException {
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpUriRequest httpUriRequest = createRequest();
			return doExecute(httpClient, httpUriRequest);
		}
	}

	protected abstract HttpUriRequest createRequest() throws IOException;

	private String doExecute(CloseableHttpClient httpClient, HttpUriRequest httpUriRequest)
	        throws IOException, ClientProtocolException {
		try (CloseableHttpResponse response = httpClient.execute(httpUriRequest)) {
			String responseString = EntityUtils.toString(response.getEntity());
			logger.info("Http status -> {}, response -> {}", response.getStatusLine(), responseString);
			return responseString;
		}
	}

	protected String decode(String name) {
		try {
			name = URLDecoder.decode(name, getCharsetName());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			// ignore decode failure
		}
		return name;
	}

	protected String getCharsetName() {
		return StringUtils.defaultString(charsetName, HttpUtils.DEFAULT_CHARSET);
	}

}
