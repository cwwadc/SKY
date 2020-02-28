package com.sky.core.service.api.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GZipBasedApiCompressFactory implements ApiCompressFactory {
	private static final Logger logger = LoggerFactory.getLogger(GZipBasedApiCompressFactory.class);
	private static final GZipBasedApiCompressFactory instance = new GZipBasedApiCompressFactory();
	
	public static GZipBasedApiCompressFactory instance() {
		return instance;
	}
	
	@Override
	public byte[] compress(String primStr) {
		if (primStr == null || primStr.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(primStr.getBytes("GBK"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (gzip != null) {
                try {
                    gzip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        byte[] compressedBytes = out.toByteArray();
        if(logger.isDebugEnabled()) {
        	logger.debug("gzip compress start:\n input-buffer -> [{}],\n output-buffer -> [{}]", primStr, Hex.encodeHexString(compressedBytes));
        }
        return compressedBytes;
	}

	@Override
	public String uncompress(byte[] compressedStr) {
		if (compressedStr == null) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;
        GZIPInputStream ginzip = null;
        byte[] compressed = null;
        String decompressed = null;
        try {
            compressed = compressedStr;
            in = new ByteArrayInputStream(compressed);
            ginzip = new GZIPInputStream(in);
 
            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = ginzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString("GBK");
            if(logger.isDebugEnabled()) {
            	logger.debug("gzip uncompress start:\n input-buffer -> [{}],\n output-buffer -> [{}]", Hex.encodeHexString(compressedStr), decompressed);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ginzip != null) {
                try {
                    ginzip.close();
                } catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            try {
                out.close();
            } catch (IOException e) {
            }
        }
        return decompressed;
	}

}
