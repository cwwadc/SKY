package com.sky.core.service.api.mock.server.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface MockWsInterface {
	
	@WebMethod
	public String accept(@WebParam(name="arg0")String method, 
			@WebParam(name="arg1")String key, 
			@WebParam(name="arg2")String message);
}
