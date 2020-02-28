package com.sky.base.transport.http;

import java.io.IOException;

import com.sky.base.transport.http.HttpUtils;

/**
 * 
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-02-20
 */
public class HttpUtilsTest {

	public static void main(String[] args) throws IOException {
		String url = "http://10.47.0.167:36680/merchant/gateway.do?appId=M0100001&bizContent={%22merchantId%22:%22200604000005448%22,%20%22productType%22:%22T00001%22}&charset=utf-8&service=support.bank.list&signType=MD5&timestamp=20190118173511&version=1.0&sign=5be19a039b702988b4f692a9449e05bf";
		HttpUtils.post(url);
	}
}
