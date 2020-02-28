package com.sky.core.service.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.base.serialize.json.JsonUtils;
import com.sky.service.api.struct.domain.common.IBMMQBinding;
import com.sky.service.api.struct.domain.common.RMIBinding;
import com.sky.service.api.struct.domain.common.WSBinding;

public class SkyChnlUtils {
	private static final Logger logger = LoggerFactory.getLogger(SkyChnlUtils.class);
	public static final String BINDING_URI_SPLITTER = ":";
	public static final String BINDING_AUTH_SPLITTER = "@";
	
	public static IBMMQBinding parseIBMMQBinding(String bindingUri) {    //IBMMQBindingURI组成：username:password@host:port:qmgr:ccsid:channel:queue
		String[] bindingGroup = bindingUri.split(BINDING_AUTH_SPLITTER);
		String[] authGroup = bindingGroup[0].split(BINDING_URI_SPLITTER);
		String[] desinationGroup = bindingGroup[1].split(BINDING_URI_SPLITTER);
		
		return new IBMMQBinding(authGroup[0].trim(), authGroup[1].trim(), 
				desinationGroup[0].trim(), Integer.valueOf(desinationGroup[1].trim()),
				desinationGroup[2].trim(), Integer.valueOf(desinationGroup[3].trim()),
				desinationGroup[4].trim(), desinationGroup[5].trim());
	}
	
	public static WSBinding parseWSBinding(String bindingUri) {	//WSBindingURI组成：serviceurl:method:connect-timeout-milliseconds:receive-timeout-milliseconds:{access-param1}:{access-param2}:{access-param...}
		int serviceUrlIndex = bindingUri.indexOf(":", bindingUri.lastIndexOf("/"));
		String serviceUrl = bindingUri.substring(0, serviceUrlIndex);
		String[] bindingGroup = bindingUri.substring(serviceUrlIndex + 1).split(BINDING_URI_SPLITTER);
		String[] accessParamArray = null;
		if(bindingGroup.length > 3) {  //method:connect-timeout-milliseconds:receive-timeout-milliseconds
			accessParamArray = new String[bindingGroup.length - 3];
			System.arraycopy(bindingGroup, 3, accessParamArray, 0, bindingGroup.length - 3);
			return new WSBinding(serviceUrl.trim(), bindingGroup[0].trim(), Long.valueOf(bindingGroup[1].trim()), Long.valueOf(bindingGroup[2].trim()), accessParamArray);
		} else {
			return new WSBinding(serviceUrl.trim(), bindingGroup[0].trim(), Long.valueOf(bindingGroup[1].trim()), Long.valueOf(bindingGroup[2].trim()));
		}
	}
	
	public static RMIBinding parseRMIBinding(String bindingUri) { //RMIBindingURI组成：serviceurl:service-class:method
		try {
			int serviceUrlIndex = bindingUri.indexOf(":", bindingUri.lastIndexOf("/")); 
			String serviceUrl = bindingUri.substring(0, serviceUrlIndex);
			String[] bindingGroup = bindingUri.substring(serviceUrlIndex + 1).split(BINDING_URI_SPLITTER);
			return new RMIBinding(serviceUrl.trim(), Class.forName(bindingGroup[0].trim()), bindingGroup[1].trim());
		} catch (Exception e) {
			logger.error("parse rmi-binding error", e);
			return null;
		}
	}
	
	public static void main(String[] args) {
		String ibmmmqUri = "ibmmq-admin:111111@127.0.0.1:8008:QM_GDTAX_GDETS:1381:GDTAX.GDETS.BATCH:RQ.BATCH.TO.GDETS";
		String wsUri = "http://127.0.0.1:1839/xxx/batch/:notify:60000:30000";
		String rmiUri = "http://127.0.0.1:1099/reactor/callbackReactor:com.sky.core.service.api.callback.reactor:callback";
		
		IBMMQBinding ibmmqBinding = parseIBMMQBinding(ibmmmqUri);
		WSBinding wsBinding = parseWSBinding(wsUri);
		RMIBinding rmiBinding = parseRMIBinding(rmiUri);
		
		System.out.println("ibmmqBinding->" + JsonUtils.toJsonString(ibmmqBinding));
		System.out.println("wsBinding->" + JsonUtils.toJsonString(wsBinding));
		System.out.println("rmiUri->" + JsonUtils.toJsonString(rmiBinding));
		
	}
}
