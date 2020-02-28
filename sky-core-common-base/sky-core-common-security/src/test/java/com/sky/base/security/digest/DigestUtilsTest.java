package com.sky.base.security.digest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sky.base.security.digest.DigestUtils;

/**
 * 
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-02-25
 */
public class DigestUtilsTest {

	@Test
	public void testMd5Hex() {
		assertEquals("77f7f6a7612479294f44d19cc82af381",
				DigestUtils.md5Hex("errorCode=107&errorMsg=房屋的预售监管帐号与POS机绑定的账号不一致。&key=123456&signType=1"));
	}

}
