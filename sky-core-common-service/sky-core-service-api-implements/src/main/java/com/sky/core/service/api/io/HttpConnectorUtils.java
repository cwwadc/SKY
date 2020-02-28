package com.sky.core.service.api.io;

import io.netty.util.AsciiString;

public final class HttpConnectorUtils {
	
	public static final class HeaderValues {
		public static final AsciiString CONNECTION_CLOSE = AsciiString.cached("close");
		public static final AsciiString CONNECTION_KEEPALIVE = AsciiString.cached("keep-alive");
		public static final AsciiString CONTENETTYPE_PLAINTEXT = AsciiString.cached("text/plain");
	}

}
