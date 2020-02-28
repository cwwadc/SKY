package com.sky.base.lang.logging;

/**
 * 日志格式化辅助工具
 *
 */
public class LoggingFormatter {
	
	public static String stdPrettyInfo(String record, int lineLimit) {
		int leftpadSize, rightpadSize;
		if((lineLimit - record.length() - 4) % 2 == 0) {
			leftpadSize = rightpadSize = (lineLimit - record.length() - 4) / 2;
		}else {
			leftpadSize = (lineLimit - record.length() - 4) / 2;
			rightpadSize = leftpadSize + 1;
		}
		
		StringBuilder padbuilder = new StringBuilder(leftpadSize);
		for (int i = 0; i < leftpadSize; i++) {
			padbuilder.append('-');
		}
		return String.format("+%s %s %s+", padbuilder.toString(), record, leftpadSize == rightpadSize ? padbuilder.toString() : padbuilder.append('-').toString());
	}
	
	public static String subPrettyInfo(String record, int lineLimit) {
		StringBuilder padbuilder = new StringBuilder();
		padbuilder.append('|').append(' ').append('*').append(' ').append(record);
		int rightpadSize = lineLimit - padbuilder.length() - 1;
		for (int i = 0; i < rightpadSize; i++) {
			padbuilder.append(' ');
		}
		return padbuilder.append('|').toString();
	}
	
//	public static void main(String[] args) {
//		String stdStr = stdPrettyInfo("Embbed Netty Container Environmentxx", 80);
//		String prtStr = subPrettyInfo("Gateway.server.binding.port -", 80);
//		System.out.println(stdStr);
//		System.out.println(prtStr);
//		System.out.println(stdStr.length());
//		System.out.println(prtStr.length());
//	}
}
