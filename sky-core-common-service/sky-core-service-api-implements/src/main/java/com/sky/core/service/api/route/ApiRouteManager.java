package com.sky.core.service.api.route;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sky.base.jdbc.spring.CoreDao;
import com.sky.base.serialize.json.JsonUtils;
import com.sky.core.service.api.callback.endpoint.ApiCallbackEndpoint;
import com.sky.core.service.api.utils.SkyChnlUtils;
import com.sky.service.api.exception.ApiException;
import com.sky.service.api.struct.domain.common.IBMMQBinding;
import com.sky.service.api.struct.domain.common.RMIBinding;
import com.sky.service.api.struct.domain.common.WSBinding;
import com.sky.service.api.struct.pojo.SkyApiRelation;
import com.sky.service.api.struct.pojo.SkyApiRoute;
import com.sky.service.api.struct.pojo.SkyApiRouteGroup;

/**
  * 路由管理组件，内部维护一个线程安全的无界路由表，一个线程安全的无界路由发送适配器列表，应用启动时根据数据库路由表配置初始化路由表和发送适配器
 *
 */
@Component
public class ApiRouteManager implements InitializingBean, DisposableBean{
	private static final Logger logger = LoggerFactory.getLogger(ApiRouteManager.class);
	
	private Map<String, Set<SkyApiRoute>> apiRouteStore = new ConcurrentHashMap<String, Set<SkyApiRoute>>();
	private Map<String, ApiRouteAware<?, ?>> routeAwareStore = new ConcurrentHashMap<String, ApiRouteAware<?, ?>>();
	private Map<Long, SkyApiRouteGroup> apiRouteGroupStore = new ConcurrentHashMap<Long, SkyApiRouteGroup>();
	private Map<String, SkyApiRelation> apiRelationStore = new ConcurrentHashMap<String, SkyApiRelation>();

	@Value("${sky.api.sql.select.route}")
	private String apiRoutesSelectSql;
	@Value("${sky.api.sql.select.routegroup}")
	private String apiRouteGroupsSelectSql;
	@Value("${sky.api.sql.select.relation}")
	private String apiRelationSelectSql;
	
	@Autowired
	private CoreDao coreDao;
	
	/** 根据回调端点信息匹配回调路由适配器
	 * @param callbackEndPoint 回调端点信息，默认使用redis实现时通过公用redis缓存获取
	 * @return 回调路由适配器
	 */
	public ApiRouteAware<?, ?> matchCallbackRouteAware(ApiCallbackEndpoint callbackEndPoint) {
		String awareCode = callbackEndPoint.getServiceUrl();
		try {
			ApiRouteAware<?, ?> aware = routeAwareStore.get(awareCode);
			if(aware == null) {
				RMIBinding binding = new RMIBinding(callbackEndPoint.getServiceUrl(), 
						Thread.currentThread().getContextClassLoader().loadClass(callbackEndPoint.getServiceClassName()), callbackEndPoint.getServiceMethod());
				aware = new RMIRouteAware(binding);
				routeAwareStore.put(awareCode, aware);
			}
			logger.debug("match route-aware succcess, aware-code -> {}", awareCode);
			return aware;
		} catch (Exception e) {
			throw new ApiException("match route-aware succcess, aware-code -> " + awareCode, e);
		}
		
	}
	
	/** 根据路由配置匹配路由发送适配器
	 * @param route 路由配置
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public ApiRouteAware matchRouteAware(SkyApiRoute route) {
		String awareCode = route.getRouteUri();
		ApiRouteAware<?, ?> aware = routeAwareStore.get(awareCode);
		if(aware == null) {
			throw new ApiException("match route-aware fail, aware-code -> {}", awareCode);
		}
		logger.debug("match route-aware succcess, aware-code -> {}", awareCode);
		return aware;
	}
	
	/** 根据路由键匹配路由配置
	 * @param routeCode 路由键，本系统业务中使用接口类型（msgType）作为路由键
	 * @return
	 */
	public SkyApiRoute matchRoute(String routeCode) {
		Set<SkyApiRoute> apiRoutes  = apiRouteStore.get(routeCode);
		SkyApiRoute match = null;
		int matchPriority = 0;
		for (SkyApiRoute route : apiRoutes) {
			if(route.getPriority() > matchPriority) {
				match = route;
			}
		}
		if(match == null) {
			throw new ApiException("match route fail, route-code -> {}", routeCode);  
		}
		logger.debug("match route succcess, route-code -> {}, route -> {}", routeCode, JsonUtils.toJsonString(match));
		return match;
	}
	
	/** 根据路由配置匹配路由组
	 * @param route 路由配置
	 * @return
	 */
	public SkyApiRouteGroup matchRouteGroup(SkyApiRoute route) {
		SkyApiRouteGroup match = apiRouteGroupStore.get(route.getRouteGroup());
		if(match == null) {
			throw new ApiException("match route-group fail, route-group-id -> {}", String.valueOf(route.getRouteGroup()));
		}
		logger.debug("match route-group succcess, route-group-id -> {}, route-group -> {}", String.valueOf(route.getRouteGroup()), JsonUtils.toJsonString(match));
		return match;
	}
	
	/** 根据请求交易类型获取交易关系
	 * @param requestType 请求交易类型
	 * @return
	 */
	public SkyApiRelation matchApiRelation(String requestType) {
		SkyApiRelation match = apiRelationStore.get(requestType);
		if(match == null) {
			throw new ApiException("match api-relation fail, request-type -> {}", requestType);
		}
		logger.debug("match api-relation success, request-type -> {}, api-relation -> {}", requestType, JsonUtils.toJsonString(match));
		return match;
	}
	
	/**
	  * 初始化路由表  
	 */
	protected void initRouteCache() {
		List<SkyApiRoute> apiRoutes = coreDao.search(apiRoutesSelectSql, SkyApiRoute.class, ApiRouteConstants.ROUTE_STATUS_AVAIABLE);
		for (SkyApiRoute route : apiRoutes) {
			Set<SkyApiRoute> routes = apiRouteStore.get(route.getRouteCode());
			if(routes == null) {
				routes = new HashSet<SkyApiRoute>(apiRoutes.size());
				apiRouteStore.put(route.getRouteCode(), routes);
			}
			routes.add(route);
		}
	}
	
	/**
	  * 初始化路由组表
	 */
	protected void initRouteGroupCache() {
		List<SkyApiRouteGroup> apiRouteGroups = coreDao.search(apiRouteGroupsSelectSql, SkyApiRouteGroup.class, ApiRouteConstants.ROUTE_GROUP_STATUS_AVAIABLE);
		for (SkyApiRouteGroup group : apiRouteGroups) {
			SkyApiRouteGroup existsGroup = apiRouteGroupStore.get(group.getId());
			if(existsGroup == null) {
				apiRouteGroupStore.put(group.getId(), group);
			}
		}
	}
	
	/**
	  * 初始化路由适配器列表
	 */
	protected void initRouteWareCache() {
		Collection<Set<SkyApiRoute>> apiRouteCollection = apiRouteStore.values();
		for (Set<SkyApiRoute> routes : apiRouteCollection) {
			for (SkyApiRoute route : routes) {
				if(route.getRouteProtocal().equals(ApiRouteConstants.ROUTE_PROTOCAL_OUTER_IBMMQ)) {
					initMQRouteAware(route);
				} else if(route.getRouteProtocal().equals(ApiRouteConstants.ROUTE_PROTOCAL_OUTER_WS)) {
					intWSRouteAware(route);
				}
			}
		}
	}
	
	/** RMI协议路由适配器初始化
	 * @param route 路由配置
	 */
	protected void intRmiRouteAware(SkyApiRoute route) {
		String awareCode = route.getRouteUri();
		if(routeAwareStore.get(awareCode) == null) {
			RMIBinding binding = SkyChnlUtils.parseRMIBinding(route.getRouteUri());
            RMIRouteAware aware = new RMIRouteAware(binding);
			aware.setAwareName(awareCode);
			routeAwareStore.put(awareCode, aware);
		}
	}

	/** WS协议路由适配器初始化
	 * @param route 路由配置
	 */
	protected void intWSRouteAware(SkyApiRoute route) {
		String awareCode = route.getRouteUri();
		if(routeAwareStore.get(awareCode) == null) {
			WSBinding binding = SkyChnlUtils.parseWSBinding(route.getRouteUri());
            WSRouteAware aware = new WSRouteAware(binding, null);
			aware.setAwareName(awareCode);
			routeAwareStore.put(awareCode, aware);
		}
	}

	/** IBM-MQ协议路由适配器初始化
	 * @param route 路由配置
	 */
	private void initMQRouteAware(SkyApiRoute route) {
		String awareCode = route.getRouteUri();
		if(routeAwareStore.get(awareCode) == null) {
			IBMMQBinding binding = SkyChnlUtils.parseIBMMQBinding(route.getRouteUri());
			IBMMQRouteAware aware = new IBMMQRouteAware(binding, 60000L);
			aware.setAwareName(awareCode);
			routeAwareStore.put(awareCode, aware);
		}
	}
	
	/**
	  *  初始化交易关系表
	 */
	private void initApiRelationCache() {
		List<SkyApiRelation> apiRelations = coreDao.search(apiRelationSelectSql, SkyApiRelation.class);
		for (SkyApiRelation relation : apiRelations) {
			SkyApiRelation existsRelation = apiRelationStore.get(relation.getRequestType());
			if(existsRelation == null) {
				apiRelationStore.put(relation.getRequestType(), relation);
			}
		}
	}
	
	/**
	  * 管理组件初始化方法 
	 */
	public void initialize() {
		initRouteCache();
		initRouteWareCache();
		initRouteGroupCache();
		initApiRelationCache();
	}
	
	/* 
	 * Spring-bean初始化方法重写
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		initialize();
	}

	/* 
	 * Spring-bean销毁方法重写
	 */
	@Override
	public void destroy() throws Exception {
		for (ApiRouteAware<?, ?> routeAware : routeAwareStore.values()) {
			routeAware.destory();
		}
	}
}
