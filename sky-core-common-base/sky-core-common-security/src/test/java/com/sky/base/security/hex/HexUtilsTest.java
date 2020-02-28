package com.sky.base.security.hex;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sky.base.security.exception.DecodeException;
import com.sky.base.security.hex.HexUtils;

/**
 * 
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-02-25
 */
public class HexUtilsTest {

	@Test
	public void testToHexString() {
		assertEquals("41313233", HexUtils.toHexString("A123"));
	}

	@Test
	public void testFromHexString() throws DecodeException {
		assertEquals("A123", HexUtils.fromHexString("41313233"));
	}

}
