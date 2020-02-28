package com.sky.base.cache.ehcache;

import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import com.sky.base.cache.CacheManager;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author lizp
 * @version 1.0.0
 * @Title
 * @Description
 * @date 2019/4/25
 */
public class SpringEhCacheCacheManager implements CacheManager {

	private EhCacheCacheManager ehCacheCacheManager;

	@Override
	public <T> T get(String cacheName, String key, Class<T> type) {
		Cache cache = getCache(cacheName);
		checkCache(cacheName, cache);
		return cache.get(key, type);
	}

	private void checkCache(String cacheName, Cache cache) {
		if (isNotExists(cache)) {
			throw new IllegalArgumentException("Illegal cache " + cacheName);
		}
	}

	private boolean isNotExists(Cache cache) {
		return !Optional.ofNullable(cache).isPresent();
	}

	private Cache getCache(String cacheName) {
		return ehCacheCacheManager.getCache(cacheName);
	}

	@Override
	public void put(String cacheName, String key, Object value) {
		Cache cache = getCache(cacheName);
		checkCache(cacheName, cache);
		cache.put(key, value);
	}

	@Override
	public void putAll(String cacheName, Map<String, Object> map) {
		Cache cache = getCache(cacheName);
		checkCache(cacheName, cache);
		map = Optional.ofNullable(map).orElse(Collections.emptyMap());
		for (Map.Entry<String, Object> each : map.entrySet()) {
			cache.put(each.getKey(), each.getValue());
		}
	}

	@Override
	public void evict(String cacheName, String key) {
		Cache cache = getCache(cacheName);
		if (isNotExists(cache)) {
			return;
		}
		cache.evict(key);
	}

	@Override
	public void clear(String cacheName) {
		Cache cache = getCache(cacheName);
		if (isNotExists(cache)) {
			return;
		}
		cache.clear();
	}

	public void setEhCacheCacheManager(EhCacheCacheManager ehCacheCacheManager) {
		this.ehCacheCacheManager = ehCacheCacheManager;
	}
}
