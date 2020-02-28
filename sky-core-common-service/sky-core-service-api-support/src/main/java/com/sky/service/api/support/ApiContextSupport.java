package com.sky.service.api.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.sky.base.context.spring.SpringContext;
import com.sky.service.api.exception.ApiException;
import com.sky.service.api.support.plugin.OrderedApiPlugin;

@Component
@Configuration
public class ApiContextSupport implements InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(ApiContextSupport.class);
    public Map<String, ApiHandler> handlerStore = new HashMap<String, ApiHandler>();
    private ApiHandler defaultApiHandler;
    private ApiHandler errorApiHandler;
    
    private List<ApiPlugin> standardPlugins;
    private List<ApiPlugin> errorPlugins;
    
    @Value("#{'${api.plugins.unavaiable}'.split(',')}")
    private List<String> unavaiablePlugins;
    
    public void initHandlerStore() {
        String[] names = SpringContext.getBeanDefinitionNames();
        Class<?> type;
        for (String name : names) {
            type = SpringContext.getApplicationContext().getType(name);
            ApiHandlerMapping apiHandlerMapping = type.getAnnotation(ApiHandlerMapping.class);
            if(apiHandlerMapping != null) {
            	ApiHandler apiHandler = (ApiHandler) SpringContext.getBean(name);
            	if(apiHandlerMapping.defaultHandler()) {
            		setDefaultHandler(apiHandler);
            	} else if(apiHandlerMapping.errorHandler()) {
            		setErrorHandler(apiHandler);
            	} else {
            		for (String mapping : apiHandlerMapping.value()) {
                		handlerStore.put(mapping, apiHandler);
    				}
            	}
            }
        }
    }
    
    private void setDefaultHandler(ApiHandler handler) {
    	if(defaultApiHandler != null) {
    		throw new ApiException("multiple default api-handler is not allowed, "
    				+ "check default handler mapping dependency by annatation ApiHandlerMapping.defaultHandler().");
    	}
    	defaultApiHandler = handler;
	}
    
    private void setErrorHandler(ApiHandler handler) {
    	if(defaultApiHandler != null) {
    		throw new ApiException("multiple error api-handler is not allowed, "
    				+ "check default handler mapping dependency by annatation ApiHandlerMapping.errorHandler().");
    	}
    	errorApiHandler = handler;
	}
    
	public void initPlugins() {
		standardPlugins = loadPlugins(ApiPluginTypeEnum.STANDARD);
		errorPlugins = loadPlugins(ApiPluginTypeEnum.ERROR);
	}
	
    
    public List<ApiPlugin> loadPlugins(ApiPluginTypeEnum pluginType) {
    	Map<Integer, ApiPlugin> pluginsMap = new TreeMap<Integer, ApiPlugin>();	
    	 String[] names = SpringContext.getBeanDefinitionNames();
         Class<?> type;
         for (String name : names) {
             type = SpringContext.getApplicationContext().getType(name);
             ApiPluginMapping apiPluginMapping = type.getAnnotation(ApiPluginMapping.class);
             if(apiPluginMapping != null && apiPluginMapping.type().equals(pluginType)) {
            	OrderedApiPlugin apiPlugin = (OrderedApiPlugin) SpringContext.getBean(name);
            	apiPlugin.setOrder(apiPluginMapping.order());
            	if(!unavaiablePlugins.contains(apiPlugin.getPluginName())) {
            		apiPlugin.setAvaiableState(true);
            		pluginsMap.put(apiPluginMapping.order(), apiPlugin);
            	}else {
            		apiPlugin.setAvaiableState(false);
            	}
             }
         }
		return new ArrayList<ApiPlugin>(pluginsMap.values());
	}
    
	public ApiHandler findHandler(String apiName) {
        return handlerStore.get(apiName);
    }

    public boolean containsHandlerMapping(String apiName) {
        return handlerStore.containsKey(apiName);
    }
    

	@Override
	public void afterPropertiesSet() throws Exception {
		initHandlerStore();
		initPlugins();
	}

	public ApiHandler defaultApiHandler() {
		return defaultApiHandler;
	}

	public ApiHandler errorApiHandler() {
		return errorApiHandler;
	}
	
	public List<ApiPlugin> getStandardPlugins() {
		return standardPlugins;
	}

	public void setStandardPlugins(List<ApiPlugin> standardPlugins) {
		this.standardPlugins = standardPlugins;
	}

	public List<ApiPlugin> getErrorPlugins() {
		return errorPlugins;
	}

	public void setErrorPlugins(List<ApiPlugin> errorPlugins) {
		this.errorPlugins = errorPlugins;
	}

}