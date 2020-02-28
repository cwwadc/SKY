package com.sky.core.service.api.route;

import org.junit.Test;

import com.sky.base.serialize.json.JsonUtils;
import com.sky.core.service.api.utils.SkyChnlUtils;
import com.sky.service.api.struct.domain.common.WSBinding;

public class WSRouteAwareTest {
	
	
	public static void main(String[] args) {
		String bindingUrl = "http://127.0.0.1:7011/ws/wbserver?wsdl:accept:60000:60000:sky:KEYSTRING";
		WSBinding binding = SkyChnlUtils.parseWSBinding(bindingUrl);
		System.out.println("binding -> " + JsonUtils.toJsonString(binding));
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		WSRouteAware aware = new WSRouteAware(binding, 2);
	}
	
	
//	@Test
//	public void deliverTest() {
//		try {
//			String bindingUri = "http://127.0.0.1:7011/ws/wbserver?wsdl:accept:60000:60000:{access-param1}:{access-param2}";
//			WSBinding binding = SkyChnlUtils.parseWSBinding(bindingUri);
//			WSRouteAware aware = new WSRouteAware(binding, null);
//			String deliverContent = "web-service route deliver content : web-service路由内容";
//			int runnableCount = 100;
//			for (int i = 0; i < runnableCount; i++) {
//				new Thread(new Runnable() {
//					@Override
//					public void run() {
//						aware.deliver(deliverContent, ApiRouteConstants.ROUTE_UNUSE_DATA_COMPRESS);
//					}
//					
//				}).start();
//				
//			}
//			System.in.read();
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//		
//	}
	
//	public static void main(String[] args) {
//		String version = System.getProperty("java.version");
//		System.out.println(version);
		
//		boolean isJava9Compatible;
//	    boolean isJava8Before161;

//	        String version = System.getProperty("java.version");
//	        String version = "1.8.0";
//	        try {
//	            isJava8Before161 = version != null && version.startsWith("1.8.0")
//	                && Integer.parseInt(version.substring(6)) < 161;
//	        } catch (NumberFormatException ex) {
//	            isJava8Before161 = false;
//	        }
//
//	        if (version.indexOf('.') > 0) {
//	            version = version.substring(0, version.indexOf('.'));
//	        }
//	        System.out.println(version);
	        //setJava9Compatible(Integer.valueOf(version) >= 9);
//	}
	
}
