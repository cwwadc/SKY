package com.sky.base.security.hex;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.sky.base.security.exception.DecodeException;

/**
 * @Title 十六进制工具类
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-02-25
 */
public class HexUtils {

	public static String toHexString(String data) {
		return new String(Hex.encodeHex(data.getBytes()));
	}

	public static String fromHexString(String hex) throws DecodeException {
		try {
			return new String(Hex.decodeHex(hex));
		} catch (DecoderException e) {
			throw new DecodeException(e);
		}
	}
}
