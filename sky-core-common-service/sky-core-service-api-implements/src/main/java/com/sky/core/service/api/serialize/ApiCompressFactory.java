package com.sky.core.service.api.serialize;

public interface ApiCompressFactory {
	
	byte[] compress(String primStr);
	
	String uncompress(byte[] compressedStr);
}
