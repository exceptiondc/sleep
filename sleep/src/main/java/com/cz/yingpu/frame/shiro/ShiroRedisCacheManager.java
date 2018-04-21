package com.cz.yingpu.frame.shiro;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import com.cz.yingpu.frame.cached.ICached;
/**
 * redis的缓存管理器
 * @author caomei
 *
 */
public class ShiroRedisCacheManager extends AbstractCacheManager {
	private ICached cached;
	@SuppressWarnings("rawtypes")
	@Override
	protected Cache createCache(String cacheName) throws CacheException {
		return new ShiroRedisCache<String, Object>(cacheName,cached);
	}
	public ICached getCached() {
		return cached;
	}
	public void setCached(ICached cached) {
		this.cached = cached;
	}

}
