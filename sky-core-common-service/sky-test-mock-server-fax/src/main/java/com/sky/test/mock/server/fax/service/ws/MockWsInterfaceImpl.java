package com.sky.test.mock.server.fax.service.ws;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(endpointInterface="com.sky.test.mock.server.fax.service.ws.MockWsInterface")
public class MockWsInterfaceImpl implements MockWsInterface {
	private static final Logger logger = LoggerFactory.getLogger(MockWsInterfaceImpl.class);
	private static final String MOCK_WS_SERVER_CHARSET = "GBK";
	
	@Override
	public String accept(@WebParam(name="arg0")String method, @WebParam(name="arg1")String key, @WebParam(name="arg2")String message) {
		try {
			logger.info("ws request incoming -------->");
			logger.info("method:[{}], key:[{}], message:[{}]", method, key, URLDecoder.decode(message, MOCK_WS_SERVER_CHARSET));
			String acceptResponse = "web-service 中文响应";
			logger.info("accept-response:[{}]", acceptResponse);
			return URLEncoder.encode(acceptResponse, MOCK_WS_SERVER_CHARSET);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

}
