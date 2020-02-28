package com.sky.base.transport.http;

import java.io.IOException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

/**
 * 
 * @title
 * @description
 * @author lizp
 * @version 1.0.0
 * @date 2019-05-07
 */
public class StringPost extends Post {

	private String content;

	public StringPost(String url, String content) {
		super(url);
		this.content = content;
	}

	public StringPost(String url, String content, String charsetName) {
		super(url);
		this.content = content;
		this.charsetName = charsetName;
	}

	@Override
	protected void init() {

	}

	@Override
	protected HttpUriRequest createRequest() throws IOException {
		logger.info("Http post request url -> {}, content -> {}", url, content);
		HttpPost httpPost = new HttpPost(url);
		StringEntity entity = new StringEntity(content, getCharsetName());
		httpPost.setEntity(entity);
		return httpPost;
	}

}
